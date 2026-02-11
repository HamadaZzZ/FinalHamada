package com.example.finalhamada;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

/**
 * ============================================================
 * ProfileActivity
 * ============================================================
 *
 * شاشة الملف الشخصي للمستخدم:
 * - عرض بياناته الحقيقية من Firebase Realtime Database
 * - ربط كل TextView بالقيم الحقيقية (Height, Weight, Age)
 * - زر Edit Profile للذهاب إلى شاشة تعديل البيانات
 * - زر Logout لتسجيل الخروج
 *
 * استخدام Realtime Database:
 * - HashMap لتحويل البيانات تلقائيًا إلى JSON
 * - updateChildren لتحديث أي بيانات بدون حذف بيانات أخرى
 */
public class Profile extends AppCompatActivity {

    private TextView tvName, tvSubtitle, tvHeightValue, tvWeightValue, tvAgeValue;
    private ImageView imgProfile;
    private Button btnEditProfile, btnLogout;

    private FirebaseAuth auth;
    private DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // ====== Bind UI ======
        tvName = findViewById(R.id.tvName);
        tvSubtitle = findViewById(R.id.tvSubtitle);
        tvHeightValue = findViewById(R.id.tvHeightValue);
        tvWeightValue = findViewById(R.id.tvWeightValue);
        tvAgeValue = findViewById(R.id.tvAgeValue);
        imgProfile = findViewById(R.id.imgProfile);
        btnEditProfile = findViewById(R.id.btnEditProfile);
        btnLogout = findViewById(R.id.btnLogout);

        // ====== Firebase ======
        auth = FirebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference();

        loadUserData();

        btnEditProfile.setOnClickListener(v -> {
            startActivity(new Intent(Profile.this, AboutYourself.class));
        });

        btnLogout.setOnClickListener(v -> {
            auth.signOut();
            startActivity(new Intent(Profile.this, SignIn.class));
            finish();
        });
    }

    /**
     * loadUserData
     * ---------------------------------------------
     * قراءة البيانات من Firebase Realtime Database وعرضها على الشاشة.
     *
     * @implNote - DataSnapshot يمثل "صورة" للبيانات الموجودة في المسار.
     * snapshot هو حاوية للبيانات اللي جايه من Firebase Realtime Database.
     * exists() هي دالة (function) تتحقق مما إذا كانت هذه البيانات موجودة فعلاً في هذا المسار.
     * إذا كانت موجودة، يمكننا قراءة القيم بأمان.
     * - مثال:
     * DataSnapshot snapshot = dbRef.child("users").child(uid).child("profile").get();
     * if (snapshot.exists()) {
     * Long age = snapshot.child("age").getValue(Long.class);
     * }
     */
    private void loadUserData() {
        String uid = auth.getCurrentUser().getUid();

        dbRef.child("users").child(uid).child("profile")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        // تحقق إذا كانت البيانات موجودة قبل القراءة
                        if (snapshot.exists()) {
                            String name = snapshot.child("name").getValue(String.class);
                            String subtitle = snapshot.child("gender").getValue(String.class);
                            Double height = snapshot.child("height").getValue(Double.class);
                            Double weight = snapshot.child("weight").getValue(Double.class);
                            Long age = snapshot.child("age").getValue(Long.class);

                            tvName.setText(name != null ? name : "User Name");
                            tvSubtitle.setText(subtitle != null ? subtitle : "Fitness Enthusiast");
                            tvHeightValue.setText(height != null ? height + " cm" : "--");
                            tvWeightValue.setText(weight != null ? weight + " kg" : "--");
                            tvAgeValue.setText(age != null ? age.toString() : "--");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // حدث خطأ أثناء قراءة البيانات
                    }
                });
    }
}
