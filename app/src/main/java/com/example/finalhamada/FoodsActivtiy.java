package com.example.finalhamada;

import android.content.Intent; // استيراد كلاس Intent للانتقال بين الواجهات (Activities).
import android.os.Bundle; // كلاس Bundle لحفظ واسترجاع حالة الشاشة.

import androidx.annotation.NonNull; // وسام للتأكيد على أن القيمة لا يمكن أن تكون null.
import androidx.appcompat.app.AppCompatActivity; // الكلاس الأساسي للشاشات.
import androidx.fragment.app.Fragment; // الكلاس الأساسي للأجزاء (Fragments).
import androidx.viewpager2.adapter.FragmentStateAdapter; // محول خاص لإدارة الأجزاء داخل ViewPager2.
import androidx.viewpager2.widget.ViewPager2; // عنصر واجهة يسمح بالتنقل بين الصفحات بالسحب يميناً ويساراً.

import com.google.android.material.floatingactionbutton.FloatingActionButton; // الزر العائم (FAB).
import com.google.android.material.tabs.TabLayout; // عنصر واجهة لعرض علامات التبويب (Tabs).
import com.google.android.material.tabs.TabLayoutMediator; // أداة لربط TabLayout بـ ViewPager2.

/**
 * FoodsActivity: شاشة إدارة الأطعمة والتغذية.
 * ---------------------------------------------------------
 * تتيح هذه الشاشة للمستخدم:
 * 1. عرض سجل الطعام اليومي الخاص به (Today's Log).
 * 2. استكشاف قائمة أطعمة مقترحة (Explore).
 * 3. إضافة أطعمة جديدة عبر زر عائم.
 * تعتمد الشاشة على نظام التبويبات (Tabs) لسهولة التنقل.
 */
public class FoodsActivtiy extends AppCompatActivity implements FoodListFragment.OnDataUpdateListener {

    // === عناصر واجهة المستخدم (UI Elements) ===
    
    // عنصر عرض الصفحات المنزلقة
    private ViewPager2 viewPager;
    
    // المحول المسؤول عن تزويد ViewPager بالصفحات (Fragments)
    private FoodPagerAdapter adapter;

    /**
     * دالة onCreate: يتم استدعاؤها عند بدء إنشاء الشاشة.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // ربط ملف التصميم activity_foods_activtiy.xml بهذا الكود
        setContentView(R.layout.activity_foods_activtiy);

        // إعداد شريط الأدوات العلوي (Toolbar)
        setSupportActionBar(findViewById(R.id.toolbar));
        
        // --- ربط العناصر بالمعرفات (IDs) من ملف الـ XML ---
        viewPager = findViewById(R.id.viewPager);
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        FloatingActionButton fab = findViewById(R.id.fab_add_food);

        // تهيئة المحول وربطه بـ ViewPager2
        adapter = new FoodPagerAdapter(this);
        viewPager.setAdapter(adapter);

        /**
         * ربط الـ TabLayout بـ ViewPager2.
         * TabLayoutMediator تقوم بتعيين عناوين التبويبات بناءً على موضع الصفحة.
         */
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            // تحديد نص التبويب بناءً على الموضع (0 أو 1)
            tab.setText(position == 0 ? "سجلي اليومي" : "استكشاف");
        }).attach();

        // --- إعداد حدث النقر على الزر العائم (إضافة طعام) ---
        fab.setOnClickListener(v -> {
            // الانتقال لشاشة "AddFoods" لإدخال طعام جديد يدوياً
            startActivity(new Intent(this, AddFoods.class));
        });
    }

    /**
     * دالة onResume: يتم استدعاؤها عندما تعود الشاشة للواجهة.
     */
    @Override
    protected void onResume() {
        super.onResume();
        // التحقق من تحديث البيانات لضمان دقة العرض
        if (adapter != null) onDataUpdated();
    }

    /**
     * دالة onDataUpdated: يتم استدعاؤها عند حدوث تغيير في البيانات (إضافة/حذف).
     * تنفذ واجهة OnDataUpdateListener المعرفة في FoodListFragment.
     */
    @Override
    public void onDataUpdated() {
        // إعادة المستخدم تلقائياً للتبويب الأول (السجل اليومي) عند تحديث البيانات
        viewPager.setCurrentItem(0, true);
    }

    /**
     * FoodPagerAdapter: فئة داخلية (Inner Class) لإدارة صفحات التبويب.
     * تستخدم FragmentStateAdapter لضمان كفاءة استهلاك الذاكرة.
     */
    private static class FoodPagerAdapter extends FragmentStateAdapter {

        /**
         * مشيد الفئة (Constructor).
         * @param activity النشاط المضيف.
         */
        public FoodPagerAdapter(@NonNull AppCompatActivity activity) {
            super(activity);
        }

        /**
         * دالة createFragment: لإنشاء الجزء (Fragment) المناسب لكل تبويب.
         * @param position رقم التبويب المختار.
         * @return كائن من نوع Fragment.
         */
        @NonNull
        @Override
        public Fragment createFragment(int position) {
            // إذا كان الموضع 0 ننشئ قائمة السجل اليومي، وإذا كان 1 ننشئ قائمة الاستكشاف
            return position == 0
                    ? FoodListFragment.newInstance(FoodListFragment.TYPE_USER_LOG)
                    : FoodListFragment.newInstance(FoodListFragment.TYPE_EXPLORE);
        }

        /**
         * دالة getItemCount: تعيد عدد التبويبات الكلي.
         */
        @Override
        public int getItemCount() {
            return 2; // لدينا تبويبان فقط
        }
    }
}
