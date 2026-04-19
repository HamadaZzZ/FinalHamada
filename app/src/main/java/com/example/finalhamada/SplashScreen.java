package com.example.finalhamada;

import android.content.Intent; // استيراد كلاس Intent للانتقال بين الواجهات.
import android.os.Bundle; // كلاس Bundle لتخزين حالة الـ Activity.
import android.os.Handler; // كلاس Handler لإدارة المهام المؤجلة أو التي تعمل في الخلفية.

import androidx.appcompat.app.AppCompatActivity; // الكلاس الأساسي للشاشات.
import androidx.core.graphics.Insets; // للتعامل مع حواف الشاشة (System Bars).
import androidx.core.view.ViewCompat; // لتوفير ميزات التوافق لواجهة المستخدم.
import androidx.core.view.WindowInsetsCompat; // للتعامل مع مسافات النظام (أشرطة الحالة والتنقل).

/**
 * SplashScreen: شاشة البداية (الترحيبية)
 * ---------------------------------------------------------
 * تظهر هذه الشاشة عند فتح التطبيق لأول مرة لفترة زمنية محددة،
 * ثم تقوم بنقل المستخدم تلقائياً إلى شاشة تسجيل الدخول.
 */
public class SplashScreen extends AppCompatActivity {

    // متغير لإدارة المهام المؤجلة
    private Handler handler;
    
    // المهمة التي سيتم تنفيذها بعد انتهاء الوقت
    private Runnable runnable;
    
    // مدة ظهور الشاشة بالملي ثانية (3000 ملي ثانية = 3 ثوانٍ)
    private int splashDuration = 3000;

    // علامة لتحديد السجلات (Logs) الخاصة بهذه الشاشة
    private static final String TAG = SplashScreen.class.getSimpleName();
    
    // اسم اختياري لخيط العمل الخاص بالشاشة
    private static final String SPLASH_SCREEN_THREAD_NAME = "SplashScreenThread";

    /**
     * دالة onCreate: يتم استدعاؤها عند إنشاء الشاشة.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // ربط الكود بملف التصميم activity_splash_screen.xml
        setContentView(R.layout.activity_splash_screen);

        // ضبط واجهة المستخدم لتغطي الشاشة بالكامل (Edge-to-Edge) مع مراعاة أشرطة النظام
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.splashLayout), (v, insets) -> {
            // الحصول على أبعاد أشرطة النظام (أعلى وأسفل الشاشة)
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            // ضبط الهوامش (Padding) لضمان عدم تداخل المحتوى مع أشرطة النظام
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // إنشاء كائن Handler جديد لإدارة المؤقت
        handler = new Handler();

        // تعريف المهمة (Runnable) التي سيتم تشغيلها بعد انتهاء المدة
        runnable = new Runnable() {
            @Override
            public void run() {
                // إنشاء Intent للانتقال من هذه الشاشة (SplashScreen) إلى شاشة تسجيل الدخول (SignIn)
                Intent intent = new Intent(SplashScreen.this, SignIn.class);
                startActivity(intent); // بدء الانتقال
                
                // إنهاء شاشة البداية لضمان عدم العودة إليها عند الضغط على زر الرجوع
                finish();
            }
        };

        // تشغيل المهمة بعد مرور الوقت المحدد (splashDuration)
        handler.postDelayed(runnable, splashDuration);
    }

    /**
     * دالة onDestroy: يتم استدعاؤها عند تدمير الشاشة.
     * نستخدمها لإلغاء المهام المؤجلة إذا قام المستخدم بإغلاق التطبيق قبل انتهاء الوقت.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // إزالة المهمة من قائمة الانتظار لتجنب حدوث تسريب في الذاكرة (Memory Leak)
        if (handler != null && runnable != null) {
            handler.removeCallbacks(runnable);
        }
    }
}
