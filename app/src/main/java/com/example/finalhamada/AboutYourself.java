            package com.example.finalhamada;

            import android.content.Intent;
            import android.os.Bundle;
            import android.widget.Button;
            import android.widget.EditText;
            import android.widget.RadioButton;
            import android.widget.RadioGroup;
            import android.widget.Toast;

            import androidx.appcompat.app.AppCompatActivity;
            import androidx.core.graphics.Insets;
            import androidx.core.view.ViewCompat;
            import androidx.core.view.WindowInsetsCompat;

            import com.google.firebase.auth.FirebaseAuth;
            import com.google.firebase.database.DatabaseReference;
            import com.google.firebase.database.FirebaseDatabase;

            import java.util.HashMap;

            /**
             * ============================================================
             * AboutYourself Activity
             * ============================================================
             * هذه الشاشة تأخذ معلومات المستخدم الأساسية
             * ثم تقوم بحفظها داخل Firebase Realtime Database.
             */
            public class AboutYourself extends AppCompatActivity {

                private EditText etAge, etHeight, etWeight;
                private RadioGroup genderGroup;
                private RadioButton radioMale, radioFemale, radioOther;
                private Button nextButton;

                private FirebaseAuth auth;

                /**
                 * Firebase Realtime Database
                 * ------------------------------------------------------------
                 * هي قاعدة بيانات سحابية تخزن البيانات على شكل JSON (بدون جداول).
                 * أي تغيير يتم حفظه مباشرة على الإنترنت ويصبح متوفر فورًا.
                 *
                 * نستخدمها هنا لتخزين بيانات المستخدم مرة واحدة بعد التسجيل.
                 */
                private DatabaseReference dbRef;

                @Override
                protected void onCreate(Bundle savedInstanceState) {
                    super.onCreate(savedInstanceState);
                    setContentView(R.layout.activity_about_yourself);

                    // Edge-to-Edge Layout
                    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
                        Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                        v.setPadding(systemBars.left, systemBars.top,
                                systemBars.right, systemBars.bottom);
                        return insets;
                    });

                    // ربط العناصر
                    etAge = findViewById(R.id.etAge);
                    etHeight = findViewById(R.id.etHeight);
                    etWeight = findViewById(R.id.etWeight);
                    genderGroup = findViewById(R.id.genderGroup);
                    radioMale = findViewById(R.id.radioMale);
                    radioFemale = findViewById(R.id.radioFemale);
                    radioOther = findViewById(R.id.radioOther);
                    nextButton = findViewById(R.id.nextButton);

                    auth = FirebaseAuth.getInstance();

                    /**
                     * getReference() يعطينا نقطة بداية داخل قاعدة البيانات
                     * لنحدد أين سنحفظ البيانات.
                     */
                    dbRef = FirebaseDatabase.getInstance().getReference();

                    nextButton.setOnClickListener(v -> saveUserData());
                }

                /**
                 * قراءة البيانات من المستخدم ثم حفظها في Firebase.
                 */
                private void saveUserData() {

                    String ageStr = etAge.getText().toString().trim();
                    String heightStr = etHeight.getText().toString().trim();
                    String weightStr = etWeight.getText().toString().trim();

                    String gender = "";
                    int selectedId = genderGroup.getCheckedRadioButtonId();

                    if (selectedId == radioMale.getId()) gender = "Male";
                    else if (selectedId == radioFemale.getId()) gender = "Female";
                    else if (selectedId == radioOther.getId()) gender = "Other";

                    if (ageStr.isEmpty() || heightStr.isEmpty() || weightStr.isEmpty() || gender.isEmpty()) {
                        Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    long age;
                    double height, weight;

                    try {
                        age = Long.parseLong(ageStr);
                        height = Double.parseDouble(heightStr);
                        weight = Double.parseDouble(weightStr);
                    } catch (NumberFormatException e) {
                        Toast.makeText(this, "Enter valid numbers", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    /**
                     * HashMap
                     * ------------------------------------------------------------
                     * نستخدم HashMap لتجميع البيانات قبل إرسالها إلى Firebase.
                     *
                     * HashMap تخزن البيانات على شكل:
                     * Key → Value
                     *
                     * Firebase يستقبل البيانات كـ JSON،
                     * و HashMap تتحول تلقائيًا إلى JSON عند الحفظ.
                     *.
                     */
                    HashMap<String, Object> profileData = new HashMap<>();
                    profileData.put("age", age);
                    profileData.put("height", height);
                    profileData.put("weight", weight);
                    profileData.put("gender", gender);

                    String uid = auth.getCurrentUser().getUid();

                    /**
                     * يتم حفظ البيانات داخل المسار:
                     * users → uid → profile
                     *
                     * updateChildren يعني تحديث القيم فقط بدون حذف بيانات أخرى.
                     */
                    dbRef.child("users")
                            .child(uid)
                            .child("profile")
                            .updateChildren(profileData)
                            .addOnSuccessListener(aVoid -> {
                                Toast.makeText(this, "Data saved successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(AboutYourself.this, YourGoal.class));
                                finish();
                            })
                            .addOnFailureListener(e ->
                                    Toast.makeText(this, "Failed: " + e.getMessage(), Toast.LENGTH_LONG).show()
                            );
                }
            }
