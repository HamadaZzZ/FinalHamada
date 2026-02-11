package com.example.finalhamada;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

/**
 * ============================================================
 * FoodsActivity
 * ============================================================
 * شاشة إدارة الأغذية في التطبيق.
 *
 * الوظائف الرئيسية:
 * 1️⃣ عرض TabLayout مع ViewPager2 يحتوي على تابين:
 *    - "Today's Log": قائمة طعام المستخدم اليوم
 *    - "Explore": استكشاف وجبات جديدة
 * 2️⃣ FloatingActionButton لإضافة طعام جديد
 * 3️⃣ تحديث التاب الأول تلقائيًا عند العودة من AddFoods أو Explore
 *
 * ملاحظات تقنية:
 * - يستخدم FragmentStateAdapter لإدارة Fragment لكل تاب.
 * - implements FoodListFragment.OnDataUpdateListener لتحديث البيانات عند العودة.
 * - onResume يستدعي onDataUpdated لضمان تحديث عرض البيانات دائمًا.
 * ============================================================
 */
public class FoodsActivtiy extends AppCompatActivity implements FoodListFragment.OnDataUpdateListener {

    /** ViewPager لإدارة التابس */
    private ViewPager2 viewPager;

    /** Adapter الخاص بالـ ViewPager */
    private FoodPagerAdapter adapter;

    /**
     * onCreate
     * --------------------------------------------------
     * تُستدعى عند إنشاء الشاشة لأول مرة.
     * تقوم بـ:
     * 1️⃣ ربط Toolbar و ViewPager و TabLayout و FAB.
     * 2️⃣ إعداد Adapter للتابس.
     * 3️⃣ ربط TabLayout بالـ ViewPager.
     * 4️⃣ إعداد FAB للانتقال إلى AddFoods.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foods_activtiy);

        setSupportActionBar(findViewById(R.id.toolbar));
        viewPager = findViewById(R.id.viewPager);
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        FloatingActionButton fab = findViewById(R.id.fab_add_food);

        // إعداد Adapter وربطه بالـ ViewPager
        adapter = new FoodPagerAdapter(this);
        viewPager.setAdapter(adapter);

        // ربط TabLayout بالـ ViewPager
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            tab.setText(position == 0 ? "Today's Log" : "Explore");
        }).attach();

        // FloatingActionButton لإضافة طعام جديد
        fab.setOnClickListener(v -> startActivity(new Intent(this, AddFoods.class)));
    }

    /**
     * onResume
     * --------------------------------------------------
     * تُستدعى كل مرة تظهر الشاشة بعد أن تكون مخفية.
     * تقوم باستدعاء onDataUpdated لضمان تحديث التاب الأول تلقائيًا.
     */
    @Override
    protected void onResume() {
        super.onResume();
        if (adapter != null) onDataUpdated();
    }

    /**
     * onDataUpdated
     * --------------------------------------------------
     * يُستدعى عند تعديل بيانات الطعام (AddFoods أو Explore).
     * يقوم بتحديث ViewPager للانتقال لتاب "Today's Log" تلقائيًا.
     */
    @Override
    public void onDataUpdated() {
        viewPager.setCurrentItem(0, true); // true = انتقال سلس مع الانزلاق
    }

    /**
     * ============================================================
     * FoodPagerAdapter
     * ============================================================
     * Adapter لإدارة Fragment لكل تاب في ViewPager2.
     *
     * الوظائف:
     * - position 0 → Today's Log
     * - position 1 → Explore
     *
     * يستخدم FragmentStateAdapter لإدارة دورة حياة كل Fragment بشكل فعّال.
     */
    private static class FoodPagerAdapter extends FragmentStateAdapter {

        /**
         * Constructor
         * @param activity النشاط المضيف (FoodsActivity)
         */
        public FoodPagerAdapter(@NonNull AppCompatActivity activity) {
            super(activity);
        }

        /**
         * createFragment
         * --------------------------------------------------
         * تحديد Fragment لكل تاب حسب position:
         * @param position رقم التاب (0 أو 1)
         * @return Fragment المناسب للتاب
         */
        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return position == 0
                    ? FoodListFragment.newInstance(FoodListFragment.TYPE_USER_LOG)
                    : FoodListFragment.newInstance(FoodListFragment.TYPE_EXPLORE);
        }

        /**
         * getItemCount
         * --------------------------------------------------
         * عدد التابس في الشاشة
         * @return 2 تبس: Today's Log و Explore
         */
        @Override
        public int getItemCount() {
            return 2;
        }
    }
}
