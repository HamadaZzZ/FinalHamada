package com.example.finalhamada; // مكان الكلاس داخل المشروع

import android.Manifest; // أذونات النظام
import android.content.Intent; // للانتقال بين الشاشات
import android.content.pm.PackageManager; // لفحص الأذونات
import android.graphics.Bitmap; // يمثل الصورة بعد تحويلها
import android.graphics.BitmapFactory; // يحول Stream أو byte[] إلى Bitmap
import android.net.Uri; // يمثل رابط الصورة المختارة
import android.os.Build; // لمعرفة إصدار Android
import android.os.Bundle; // لحفظ حالة الشاشة
import android.util.Base64; // لتحويل الصورة إلى نص Base64 والعكس
import android.util.Log; // لطباعة رسائل في Logcat
import android.widget.Button; // زر
import android.widget.ImageView; // لعرض الصورة
import android.widget.TextView; // لعرض النصوص
import android.widget.Toast; // رسالة قصيرة للمستخدم

import androidx.activity.result.ActivityResultLauncher; // مشغل الطلبات الحديث
import androidx.activity.result.contract.ActivityResultContracts; // نوع الطلب: إذن أو صورة
import androidx.appcompat.app.AppCompatActivity; // كلاس Activity الأساسي
import androidx.core.content.ContextCompat; // لفحص الإذن

import com.google.firebase.auth.FirebaseAuth; // Firebase Auth
import com.google.firebase.database.DataSnapshot; // قراءة البيانات
import com.google.firebase.database.DatabaseError; // أخطاء Firebase
import com.google.firebase.database.DatabaseReference; // مرجع Firebase
import com.google.firebase.database.FirebaseDatabase; // قاعدة البيانات
import com.google.firebase.database.ValueEventListener; // مستمع قراءة البيانات

import java.io.ByteArrayOutputStream; // يحول الصورة إلى bytes
import java.io.FileNotFoundException; // خطأ إذا الملف غير موجود
import java.io.InputStream; // قراءة الصورة من الجهاز
import java.util.HashMap; // تخزين البيانات كمفتاح وقيمة

public class Profile extends AppCompatActivity {

    private static final String TAG = "Profile"; // اسم يظهر في Logcat

    private TextView tvName, tvSubtitle, tvHeightValue, tvWeightValue, tvAgeValue; // نصوص البيانات
    private ImageView imgProfile; // صورة البروفايل
    private Button btnEditProfile, btnLogout; // أزرار التعديل والخروج

    private FirebaseAuth auth; // مصادقة Firebase
    private DatabaseReference dbRef; // قاعدة بيانات Firebase

    private Uri selectedImageUri; // رابط الصورة المختارة من المعرض
    private String selectedImageString; // الصورة بعد تحويلها إلى Base64

    private ActivityResultLauncher<String> requestReadMediaImagesPermission; // طلب إذن الصور Android 13+
    private ActivityResultLauncher<String> requestReadExternalStoragePermission; // طلب إذن التخزين Android أقل من 13
    private ActivityResultLauncher<String> pickImageLauncher; // فتح المعرض واختيار صورة

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // تشغيل إنشاء الشاشة
        setContentView(R.layout.activity_profile); // ربط Java مع XML

        tvName = findViewById(R.id.tvName); // ربط اسم المستخدم
        tvSubtitle = findViewById(R.id.tvSubtitle); // ربط النص الفرعي
        tvHeightValue = findViewById(R.id.tvHeightValue); // ربط الطول
        tvWeightValue = findViewById(R.id.tvWeightValue); // ربط الوزن
        tvAgeValue = findViewById(R.id.tvAgeValue); // ربط العمر
        imgProfile = findViewById(R.id.imgProfile); // ربط صورة البروفايل
        btnEditProfile = findViewById(R.id.btnEditProfile); // ربط زر التعديل
        btnLogout = findViewById(R.id.btnLogout); // ربط زر الخروج

        auth = FirebaseAuth.getInstance(); // تهيئة Firebase Auth
        dbRef = FirebaseDatabase.getInstance().getReference(); // تهيئة Firebase Database

        registerPermissionLaunchers(); // تجهيز مشغلات الأذونات
        registerImagePickerLauncher(); // تجهيز مشغل اختيار الصورة

        loadUserData(); // تحميل بيانات المستخدم من Firebase

        imgProfile.setOnClickListener(v -> checkAndRequestImagePermission()); // عند الضغط على الصورة نطلب إذن ونفتح المعرض

        btnEditProfile.setOnClickListener(v ->
                startActivity(new Intent(Profile.this, AboutYourself.class)) // فتح شاشة تعديل البيانات
        );

        btnLogout.setOnClickListener(v -> {
            auth.signOut(); // تسجيل خروج من Firebase
            startActivity(new Intent(Profile.this, SignIn.class)); // الرجوع لشاشة الدخول
            finish(); // إغلاق Profile
        });
    }

    private void registerPermissionLaunchers() {
        requestReadMediaImagesPermission =
                registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                    if (isGranted) { // إذا المستخدم وافق
                        Log.d(TAG, "READ_MEDIA_IMAGES permission granted");
                        openImagePicker(); // افتح المعرض
                    } else { // إذا رفض
                        Log.d(TAG, "READ_MEDIA_IMAGES permission denied");
                        Toast.makeText(this, "تم رفض إذن قراءة الصور", Toast.LENGTH_SHORT).show();
                    }
                });

        requestReadExternalStoragePermission =
                registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                    if (isGranted) { // إذا المستخدم وافق
                        Log.d(TAG, "READ_EXTERNAL_STORAGE permission granted");
                        openImagePicker(); // افتح المعرض
                    } else { // إذا رفض
                        Log.d(TAG, "READ_EXTERNAL_STORAGE permission denied");
                        Toast.makeText(this, "تم رفض إذن قراءة التخزين", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void registerImagePickerLauncher() {
        pickImageLauncher =
                registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {
                    if (uri != null) { // إذا المستخدم اختار صورة
                        selectedImageUri = uri; // حفظ رابط الصورة
                        imgProfile.setImageURI(uri); // عرض الصورة داخل ImageView

                        selectedImageString = convertImageToString(uri); // تحويل الصورة إلى Base64

                        if (selectedImageString != null) { // إذا التحويل نجح
                            saveProfileImageToFirebase(selectedImageString); // حفظ الصورة في Firebase
                        }
                    }
                });
    }

    private void checkAndRequestImagePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) { // Android 13+
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES)
                    != PackageManager.PERMISSION_GRANTED) { // إذا الإذن غير ممنوح
                requestReadMediaImagesPermission.launch(Manifest.permission.READ_MEDIA_IMAGES); // اطلب إذن الصور
            } else {
                openImagePicker(); // الإذن موجود، افتح المعرض
            }
        } else { // Android 12 وأقل
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) { // إذا إذن التخزين غير ممنوح
                requestReadExternalStoragePermission.launch(Manifest.permission.READ_EXTERNAL_STORAGE); // اطلب الإذن
            } else {
                openImagePicker(); // الإذن موجود، افتح المعرض
            }
        }
    }

    private void openImagePicker() {
        pickImageLauncher.launch("image/*"); // فتح المعرض لاختيار صورة فقط
    }

    public String convertImageToString(Uri uri) {
        InputStream inputStream; // قارئ الصورة
        String imageString; // النص النهائي Base64

        try {
            inputStream = getContentResolver().openInputStream(uri); // فتح الصورة من الجهاز
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream); // تحويل الصورة إلى Bitmap

            if (bitmap == null) { // إذا فشل التحويل
                Toast.makeText(this, "Failed to process image", Toast.LENGTH_SHORT).show();
                return null;
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream(); // تخزين الصورة كـ bytes
            bitmap.compress(Bitmap.CompressFormat.JPEG, 40, outputStream); // ضغط الصورة لتقليل الحجم
            byte[] imageBytes = outputStream.toByteArray(); // تحويلها إلى byte array
            imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT); // تحويل bytes إلى Base64

            return imageString; // إرجاع نص الصورة

        } catch (FileNotFoundException e) { // إذا الصورة غير موجودة
            Toast.makeText(this, "Failed file not found", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "Image file not found", e);
            return null;
        }
    }

    private Bitmap stringToBitmap(String imageString) {
        if (imageString == null || imageString.isEmpty()) return null; // إذا النص فارغ

        try {
            byte[] decodedString = Base64.decode(imageString, Base64.DEFAULT); // تحويل Base64 إلى bytes
            return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length); // تحويل bytes إلى Bitmap
        } catch (Exception e) { // إذا صار خطأ بالتحويل
            Log.e(TAG, "Failed to decode image string", e);
            return null;
        }
    }

    private void saveProfileImageToFirebase(String imageString) {
        if (auth.getCurrentUser() == null) return; // حماية إذا ما في مستخدم

        String uid = auth.getCurrentUser().getUid(); // UID تبع المستخدم

        HashMap<String, Object> map = new HashMap<>(); // تجهيز البيانات
        map.put("profileImage", imageString); // حفظ الصورة كنص Base64

        dbRef.child("users").child(uid).child("profile")
                .updateChildren(map) // تحديث الصورة داخل profile
                .addOnSuccessListener(aVoid ->
                        Toast.makeText(this, "تم حفظ صورة البروفايل", Toast.LENGTH_SHORT).show()
                )
                .addOnFailureListener(e ->
                        Toast.makeText(this, "فشل حفظ الصورة", Toast.LENGTH_SHORT).show()
                );
    }

    private void loadUserData() {
        if (auth.getCurrentUser() == null) return; // إذا ما في مستخدم، أوقف

        String uid = auth.getCurrentUser().getUid(); // UID المستخدم

        dbRef.child("users").child(uid).child("profile")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (snapshot.exists()) { // إذا يوجد بيانات
                            String name = snapshot.child("name").getValue(String.class); // قراءة الاسم
                            String gender = snapshot.child("gender").getValue(String.class); // قراءة الجنس
                            Double height = snapshot.child("height").getValue(Double.class); // قراءة الطول
                            Double weight = snapshot.child("weight").getValue(Double.class); // قراءة الوزن
                            Long age = snapshot.child("age").getValue(Long.class); // قراءة العمر
                            String imageString = snapshot.child("profileImage").getValue(String.class); // قراءة الصورة

                            tvName.setText(name != null ? name : "اسم المستخدم"); // عرض الاسم
                            tvSubtitle.setText(gender != null ? gender : "متحمس للياقة البدنية"); // عرض الجنس
                            tvHeightValue.setText(height != null ? height + " سم" : "--"); // عرض الطول
                            tvWeightValue.setText(weight != null ? weight + " كجم" : "--"); // عرض الوزن
                            tvAgeValue.setText(age != null ? age.toString() : "--"); // عرض العمر

                            Bitmap bitmap = stringToBitmap(imageString); // تحويل الصورة من Base64 إلى Bitmap
                            if (bitmap != null) { // إذا الصورة موجودة
                                imgProfile.setImageBitmap(bitmap); // عرض الصورة المخزنة
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        Toast.makeText(Profile.this, "فشل تحميل البيانات", Toast.LENGTH_SHORT).show(); // رسالة خطأ
                    }
                });
    }
}