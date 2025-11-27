package com.example.finalhamada;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SplashScreen extends AppCompatActivity {

    private Handler handler;
    private Runnable runnable;
    private int splashDuration = 3000;

    private static final String TAG = SplashScreen.class.getSimpleName();
    private static final String SPLASH_SCREEN_THREAD_NAME = "SplashScreenThread";

    /**
     * onCreate()
     * ----------------------------
     * EN:
     * Initializes the splash screen, applies edge-to-edge UI,
     * prepares a delayed task using Handler, and after a fixed time
     * navigates the user to the SignIn screen.
     *
     * AR:
     * تهيئة شاشة البداية، تطبيق واجهة Edge-to-Edge،
     * تجهيز مهمة مؤجلة عبر الـ Handler، وبعد مدة محددة
     * يتم نقل المستخدم إلى شاشة تسجيل الدخول.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash_screen);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.splashLayout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        handler = new Handler();

        runnable = new Runnable() {
            /**
             * run()
             * ----------------------------
             * EN:
             * Executed after the splash countdown finishes.
             * Responsible for navigating to the SignIn activity
             * and closing the splash screen.
             *
             * AR:
             * يتم تنفيذها بعد انتهاء مدة شاشة البداية.
             * مسؤولة عن الانتقال لشاشة تسجيل الدخول
             * وإغلاق شاشة السبلاتش.
             */
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, SignIn.class);
                startActivity(intent);
                finish();
            }
        };

        // EN: Start the delayed splash timer.
        // AR: بدء مؤقت السبلاتش المتأخر.
        handler.postDelayed(runnable, splashDuration);
    }
}
