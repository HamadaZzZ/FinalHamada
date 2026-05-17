package com.example.finalhamada;

import android.Manifest; // يحتوي على أسماء الأذونات مثل إذن قراءة الصور.
import android.content.Intent; // يستخدم للانتقال بين الشاشات.
import android.content.pm.PackageManager; // يستخدم لفحص هل الإذن ممنوح أم لا.
import android.graphics.Bitmap; // يمثل الصورة داخل التطبيق.
import android.graphics.BitmapFactory; // يحول الصورة من Stream أو byte[] إلى Bitmap.
import android.net.Uri; // يمثل رابط الصورة المختارة من الجهاز.
import android.os.Build; // لمعرفة إصدار Android الحالي.
import android.os.Bundle; // لحفظ حالة الشاشة.
import android.util.Base64; // لتحويل الصورة إلى نص والعكس.
import android.util.Log; // لطباعة الأخطاء في Logcat.
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher; // الطريقة الحديثة لتشغيل طلب ينتظر نتيجة.
import androidx.activity.result.contract.ActivityResultContracts; // أنواع الطلبات مثل طلب إذن أو اختيار ملف.
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat; // لفحص الأذونات.

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;

/**
 * Profile:
 * ---------------------------------------------------------
 * شاشة الملف الشخصي للمستخدم.
 *
 * تعرض بيانات المستخدم المحفوظة في Firebase مثل:
 * الاسم، الجنس، الطول، الوزن، العمر وصورة البروفايل.
 *
 * كما تسمح للمستخدم:
 * - بتغيير صورة البروفايل.
 * - فتح شاشة AboutYourself لتعديل البيانات.
 * - تسجيل الخروج من الحساب.
 */
public class Profile extends AppCompatActivity {

    // اسم ثابت يساعدنا نعرف مصدر رسائل Logcat.
    private static final String TAG = "Profile";

    // عناصر عرض بيانات المستخدم.
    private TextView tvName, tvSubtitle, tvHeightValue, tvWeightValue, tvAgeValue;

    // صورة البروفايل.
    private ImageView imgProfile;

    // أزرار تعديل البيانات وتسجيل الخروج.
    private Button btnEditProfile, btnLogout;

    // FirebaseAuth للحصول على المستخدم الحالي وتنفيذ تسجيل الخروج.
    private FirebaseAuth auth;

    // مرجع Firebase Database لقراءة وحفظ بيانات المستخدم.
    private DatabaseReference dbRef;

    // رابط الصورة التي يختارها المستخدم من المعرض.
    private Uri selectedImageUri;

    // الصورة بعد تحويلها إلى نص Base64 حتى نستطيع حفظها في Firebase.
    private String selectedImageString;

    // Launcher لطلب إذن قراءة الصور في Android 13 وما فوق.
    private ActivityResultLauncher<String> requestReadMediaImagesPermission;

    // Launcher لطلب إذن قراءة التخزين في Android 12 وما أقل.
    private ActivityResultLauncher<String> requestReadExternalStoragePermission;

    // Launcher لفتح المعرض واختيار صورة.
    private ActivityResultLauncher<String> pickImageLauncher;

    /**
     * onCreate:
     * ---------------------------------------------------------
     * أول دالة تعمل عند فتح شاشة Profile.
     *
     * تقوم بـ:
     * - ربط الشاشة بملف XML.
     * - ربط عناصر الواجهة بالكود.
     * - تهيئة Firebase.
     * - تجهيز طلبات الأذونات.
     * - تجهيز اختيار الصورة.
     * - تحميل بيانات المستخدم.
     * - تجهيز أزرار التعديل والخروج.
     *
     * بدونها لن يتم تجهيز شاشة البروفايل للعمل.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ربط Java مع تصميم الشاشة.
        setContentView(R.layout.activity_profile);

        // ربط عناصر الواجهة من XML مع متغيرات Java.
        tvName = findViewById(R.id.tvName);
        tvSubtitle = findViewById(R.id.tvSubtitle);
        tvHeightValue = findViewById(R.id.tvHeightValue);
        tvWeightValue = findViewById(R.id.tvWeightValue);
        tvAgeValue = findViewById(R.id.tvAgeValue);
        imgProfile = findViewById(R.id.imgProfile);
        btnEditProfile = findViewById(R.id.btnEditProfile);
        btnLogout = findViewById(R.id.btnLogout);

        // تهيئة FirebaseAuth للحصول على المستخدم الحالي.
        auth = FirebaseAuth.getInstance();

        // تهيئة Firebase Database للوصول إلى بيانات المستخدم.
        dbRef = FirebaseDatabase.getInstance().getReference();

        // تجهيز الـ Launchers المسؤولة عن طلب الأذونات.
        registerPermissionLaunchers();

        // تجهيز الـ Launcher المسؤول عن فتح المعرض واختيار صورة.
        registerImagePickerLauncher();

        // تحميل بيانات المستخدم من Firebase وعرضها في الشاشة.
        loadUserData();

        /**
         * عند الضغط على صورة البروفايل:
         * يتم فحص الإذن أولًا،
         * ثم فتح المعرض لاختيار صورة.
         *
         * بدون هذا Listener لن يستطيع المستخدم تغيير الصورة.
         */
        imgProfile.setOnClickListener(v -> checkAndRequestImagePermission());

        /**
         * زر Edit Profile:
         * يفتح شاشة AboutYourself حتى يعدل المستخدم بياناته.
         */
        btnEditProfile.setOnClickListener(v ->
                startActivity(new Intent(Profile.this, AboutYourself.class))
        );

        /**
         * زر Logout:
         * يسجل خروج المستخدم من Firebase
         * ثم يعيده إلى شاشة SignIn.
         */
        btnLogout.setOnClickListener(v -> {
            auth.signOut();
            startActivity(new Intent(Profile.this, SignIn.class));
            finish();
        });
    }

    /**
     * registerPermissionLaunchers:
     * ---------------------------------------------------------
     * تجهز الأدوات المسؤولة عن طلب أذونات الصور أو التخزين.
     *
     * استخدمنا ActivityResultLauncher لأنها الطريقة الحديثة
     * بدل onActivityResult القديمة.
     *
     * بدون هذه الدالة لن نستطيع طلب إذن قراءة الصور بطريقة منظمة.
     */
    private void registerPermissionLaunchers() {

        /**
         * هذا الـ Launcher خاص بـ Android 13 وما فوق.
         *
         * READ_MEDIA_IMAGES:
         * إذن قراءة الصور في الإصدارات الحديثة.
         */
        requestReadMediaImagesPermission =
                registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                    if (isGranted) {
                        Log.d(TAG, "READ_MEDIA_IMAGES permission granted");

                        // إذا المستخدم وافق على الإذن، نفتح المعرض.
                        openImagePicker();
                    } else {
                        Log.d(TAG, "READ_MEDIA_IMAGES permission denied");

                        Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                    }
                });

        /**
         * هذا الـ Launcher خاص بـ Android 12 وما أقل.
         *
         * READ_EXTERNAL_STORAGE:
         * إذن قراءة التخزين في الإصدارات القديمة.
         */
        requestReadExternalStoragePermission =
                registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                    if (isGranted) {
                        Log.d(TAG, "READ_EXTERNAL_STORAGE permission granted");

                        // إذا الإذن ممنوح، نفتح المعرض.
                        openImagePicker();
                    } else {
                        Log.d(TAG, "READ_EXTERNAL_STORAGE permission denied");

                        Toast.makeText(this, "Storage permission denied", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * registerImagePickerLauncher:
     * ---------------------------------------------------------
     * تجهز Launcher مسؤول عن فتح المعرض
     * والحصول على الصورة التي يختارها المستخدم.
     *
     * بدون هذه الدالة لن نستطيع استقبال الصورة المختارة.
     */
    private void registerImagePickerLauncher() {

        pickImageLauncher =
                registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {

                    /**
                     * uri:
                     * هو رابط الصورة التي اختارها المستخدم.
                     *
                     * إذا كانت null يعني المستخدم لم يختر صورة.
                     */
                    if (uri != null) {

                        selectedImageUri = uri;

                        // عرض الصورة مباشرة داخل ImageView.
                        imgProfile.setImageURI(uri);

                        // تحويل الصورة إلى Base64 String حتى نحفظها في Firebase.
                        selectedImageString = convertImageToString(uri);

                        if (selectedImageString != null) {

                            // حفظ الصورة في Firebase.
                            saveProfileImageToFirebase(selectedImageString);
                        }
                    }
                });
    }

    /**
     * checkAndRequestImagePermission:
     * ---------------------------------------------------------
     * تفحص إصدار Android وتطلب الإذن المناسب.
     *
     * Android 13 وما فوق:
     * يستخدم READ_MEDIA_IMAGES.
     *
     * Android 12 وما أقل:
     * يستخدم READ_EXTERNAL_STORAGE.
     *
     * أهمية الدالة:
     * بدونها قد لا يستطيع التطبيق الوصول إلى الصور،
     * أو قد يفشل على بعض إصدارات Android.
     */
    private void checkAndRequestImagePermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES)
                    != PackageManager.PERMISSION_GRANTED) {

                requestReadMediaImagesPermission.launch(Manifest.permission.READ_MEDIA_IMAGES);

            } else {

                openImagePicker();
            }

        } else {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                requestReadExternalStoragePermission.launch(Manifest.permission.READ_EXTERNAL_STORAGE);

            } else {

                openImagePicker();
            }
        }
    }

    /**
     * openImagePicker:
     * ---------------------------------------------------------
     * تفتح معرض الصور لاختيار صورة فقط.
     *
     * image/* تعني:
     * اسمح للمستخدم باختيار ملفات من نوع صورة فقط.
     *
     * بدونها لن يتم فتح المعرض.
     */
    private void openImagePicker() {
        pickImageLauncher.launch("image/*");
    }

    /**
     * convertImageToString:
     * ---------------------------------------------------------
     * تحول الصورة التي اختارها المستخدم
     * إلى نص Base64.
     *
     * الخطوات:
     * - فتح الصورة من الجهاز.
     * - تحويلها إلى Bitmap.
     * - ضغطها لتقليل الحجم.
     * - تحويلها إلى byte array.
     * - تحويل byte array إلى Base64 String.
     *
     * أهمية الدالة:
     * Firebase Realtime Database لا يحفظ الصورة كملف مباشرة،
     * لذلك نحولها إلى نص حتى نستطيع تخزينها.
     *
     * @param uri رابط الصورة المختارة.
     * @return نص Base64 يمثل الصورة، أو null إذا فشل التحويل.
     */
    public String convertImageToString(Uri uri) {
        InputStream inputStream;
        String imageString;

        try {
            // فتح الصورة من الجهاز.
            inputStream = getContentResolver().openInputStream(uri);

            // تحويل الصورة إلى Bitmap.
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

            if (bitmap == null) {
                Toast.makeText(this, "Failed to process image", Toast.LENGTH_SHORT).show();
                return null;
            }

            // تخزين الصورة كـ bytes.
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            // ضغط الصورة بصيغة JPEG بنسبة جودة 40 لتقليل الحجم.
            bitmap.compress(Bitmap.CompressFormat.JPEG, 40, outputStream);

            // تحويل الصورة المضغوطة إلى byte array.
            byte[] imageBytes = outputStream.toByteArray();

            // تحويل الـ bytes إلى String باستخدام Base64.
            imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);

            return imageString;

        } catch (FileNotFoundException e) {

            Toast.makeText(this, "File not found", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "Image file not found", e);

            return null;
        }
    }

    /**
     * stringToBitmap:
     * ---------------------------------------------------------
     * تحول صورة محفوظة كنص Base64
     * إلى Bitmap حتى نستطيع عرضها داخل ImageView.
     *
     * أهمية الدالة:
     * عند تحميل الصورة من Firebase،
     * تكون محفوظة كنص،
     * لذلك يجب تحويلها مرة أخرى إلى صورة.
     *
     * @param imageString نص Base64 للصورة.
     * @return Bitmap جاهز للعرض، أو null إذا فشل التحويل.
     */
    private Bitmap stringToBitmap(String imageString) {

        if (imageString == null || imageString.isEmpty()) return null;

        try {

            // تحويل Base64 إلى bytes.
            byte[] decodedString = Base64.decode(imageString, Base64.DEFAULT);

            // تحويل bytes إلى Bitmap.
            return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        } catch (Exception e) {

            Log.e(TAG, "Failed to decode image string", e);

            return null;
        }
    }

    /**
     * saveProfileImageToFirebase:
     * ---------------------------------------------------------
     * تحفظ صورة البروفايل داخل Firebase
     * بعد تحويلها إلى Base64 String.
     *
     * تستخدم UID حتى تحفظ الصورة
     * داخل بيانات المستخدم الحالي فقط.
     *
     * بدون UID قد تختلط صور المستخدمين.
     *
     * @param imageString الصورة كنص Base64.
     */
    private void saveProfileImageToFirebase(String imageString) {

        if (auth.getCurrentUser() == null) return;

        // الحصول على رقم المستخدم الحالي في Firebase.
        String uid = auth.getCurrentUser().getUid();

        /**
         * HashMap:
         * نخزن البيانات بشكل key-value.
         *
         * المفتاح:
         * profileImage
         *
         * القيمة:
         * نص الصورة Base64.
         */
        HashMap<String, Object> map = new HashMap<>();
        map.put("profileImage", imageString);

        /**
         * updateChildren:
         * تحدث profileImage فقط
         * بدون حذف باقي بيانات profile
         * مثل الطول والوزن والعمر.
         */
        dbRef.child("users").child(uid).child("profile")
                .updateChildren(map)
                .addOnSuccessListener(aVoid ->
                        Toast.makeText(this, "Profile image saved", Toast.LENGTH_SHORT).show()
                )
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Failed to save image", Toast.LENGTH_SHORT).show()
                );
    }

    /**
     * loadUserData:
     * ---------------------------------------------------------
     * تحمل بيانات المستخدم من Firebase
     * وتعرضها داخل شاشة Profile.
     *
     * تقرأ:
     * - الاسم
     * - الجنس
     * - الطول
     * - الوزن
     * - العمر
     * - صورة البروفايل
     *
     * بدون هذه الدالة ستبقى شاشة Profile فارغة
     * أو لن تعرض بيانات المستخدم الحقيقية.
     */
    private void loadUserData() {

        if (auth.getCurrentUser() == null) return;

        String uid = auth.getCurrentUser().getUid();

        dbRef.child("users").child(uid).child("profile")
                .addListenerForSingleValueEvent(new ValueEventListener() {

                    /**
                     * onDataChange:
                     * ---------------------------------------------------------
                     * يتم استدعاؤها عندما يتم جلب البيانات بنجاح من Firebase.
                     *
                     */
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {

                        if (snapshot.exists()) {

                            // قراءة البيانات من Firebase.
                            String name = snapshot.child("name").getValue(String.class);
                            String gender = snapshot.child("gender").getValue(String.class);
                            Double height = snapshot.child("height").getValue(Double.class);
                            Double weight = snapshot.child("weight").getValue(Double.class);
                            Long age = snapshot.child("age").getValue(Long.class);
                            String imageString = snapshot.child("profileImage").getValue(String.class);

                            // عرض البيانات في الواجهة، وإذا كانت غير موجودة نعرض قيمة افتراضية.
                            tvName.setText(name != null ? name : "User Name");
                            tvSubtitle.setText(gender != null ? gender : "Fitness motivated");
                            tvHeightValue.setText(height != null ? height + " cm" : "--");
                            tvWeightValue.setText(weight != null ? weight + " kg" : "--");
                            tvAgeValue.setText(age != null ? age.toString() : "--");

                            // تحويل صورة Base64 إلى Bitmap وعرضها.
                            Bitmap bitmap = stringToBitmap(imageString);

                            if (bitmap != null) {
                                imgProfile.setImageBitmap(bitmap);
                            }
                        }
                    }

                    /**
                     * onCancelled:
                     * ---------------------------------------------------------
                     * يتم استدعاؤها إذا فشل تحميل البيانات من Firebase.
                     */
                    @Override
                    public void onCancelled(DatabaseError error) {
                        Toast.makeText(Profile.this, "Failed to load data", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}