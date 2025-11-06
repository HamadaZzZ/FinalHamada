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
    // تعريف كائن من نوع Handler لإدارة المهام المؤجلة
private Runnable runnable;
    // تعريف كائن من نوع Runnable يحتوي على الكود الذي سيتم تشغيله بعد مدة معينة
private int splashDuration = 3000;
// تعريف متغير لتحديد مدة شاشة البداية (Splash) بالملّي ثانية (2000 = ثانيتين)

    private static final String TAG = SplashScreen.class.getSimpleName();
    private static final String SPLASH_SCREEN_THREAD_NAME = "SplashScreenThread";
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
        //
        runnable = new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, SignIn.class);
                startActivity(intent);
                finish();
            }
        };
        handler.postDelayed(runnable, splashDuration);
    }
}