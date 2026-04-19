package com.example.finalhamada;

import android.content.Intent; // استيراد كلاس Intent للانتقال بين الواجهات (Activities).
import android.os.Bundle; // كلاس Bundle لحفظ واسترجاع حالة الشاشة.
import android.widget.ImageView; // تمثيل لعنصر عرض الصور في واجهة المستخدم.
import android.widget.TextView; // تمثيل لعنصر عرض النصوص.

import androidx.appcompat.app.AppCompatActivity; // الكلاس الأساسي للشاشات.

/**
 * Progress Activity: شاشة عرض التقدم والإحصائيات.
 * ---------------------------------------------------------
 * تهدف هذه الشاشة إلى عرض ملخص لتقدم المستخدم في رحلته الرياضية،
 * بما في ذلك الرسوم البيانية التوضيحية وملخصات الأداء.
 */
public class Progress extends AppCompatActivity {

    // === عناصر واجهة المستخدم (UI Elements) ===
    
    // زر الرجوع للشاشة السابقة
    private ImageView btnBack;
    
    // عنوان الشاشة الرئيسي
    private TextView tvTitle;

    // عناصر عرض الرسم البياني
    private ImageView imgChart; // صورة توضيحية للرسم البياني
    private TextView tvChartTitle; // عنوان الرسم البياني
    private TextView tvChartSubtitle; // وصف إضافي للرسم البياني

    // عناصر ملخص الأداء
    private ImageView ivIcon; // أيقونة الملخص
    private TextView tvSummaryTitle; // عنوان الملخص
    private TextView tvSummaryBody; // تفاصيل الملخص النصية

    /**
     * دالة onCreate: يتم استدعاؤها عند بدء إنشاء الشاشة.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // ربط ملف التصميم activity_progress.xml بهذا الكود البرمجي
        setContentView(R.layout.activity_progress);

        // --- ربط المتغيرات بالمعرفات (IDs) من ملف الـ XML ---
        btnBack = findViewById(R.id.btnBack);
        tvTitle = findViewById(R.id.tvTitle);

        imgChart = findViewById(R.id.imgChart);
        tvChartTitle = findViewById(R.id.tvChartTitle);
        tvChartSubtitle = findViewById(R.id.tvChartSubtitle);

        ivIcon = findViewById(R.id.ivIcon);
        tvSummaryTitle = findViewById(R.id.tvSummaryTitle);
        tvSummaryBody = findViewById(R.id.tvSummaryBody);

        // --- إعداد حدث النقر على زر الرجوع ---
        btnBack.setOnClickListener(v -> {
            // الانتقال إلى لوحة التحكم الرئيسية (DashboardActivity)
            startActivity(new Intent(Progress.this, DashboardActivity.class));
            // إنهاء الشاشة الحالية
            finish();
        });
    }
}
