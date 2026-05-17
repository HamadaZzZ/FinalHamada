package com.example.finalhamada;

import android.content.Intent; // Intent يستخدم للانتقال من شاشة إلى شاشة أخرى.
import android.os.Bundle; // Bundle يُستخدم لحفظ حالة الشاشة عند إنشائها أو إعادة إنشائها.

import androidx.annotation.NonNull; // للتأكيد أن القيمة لا يجب أن تكون null.
import androidx.appcompat.app.AppCompatActivity; // الكلاس الأساسي للشاشات في Android.
import androidx.fragment.app.Fragment; // الكلاس الأساسي لأي Fragment.
import androidx.viewpager2.adapter.FragmentStateAdapter; // Adapter مسؤول عن إدارة Fragments داخل ViewPager2.
import androidx.viewpager2.widget.ViewPager2; // عنصر يسمح بالتنقل بين صفحات مختلفة بالسحب.

import com.google.android.material.floatingactionbutton.FloatingActionButton; // زر عائم لإضافة طعام جديد.
import com.google.android.material.tabs.TabLayout; // شريط تبويبات لعرض Today’s Log و Explore.
import com.google.android.material.tabs.TabLayoutMediator; // يربط TabLayout مع ViewPager2.

/**
 * FoodsActivtiy:
 * ---------------------------------------------------------
 * هذه الشاشة مسؤولة عن إدارة قسم الطعام في التطبيق.
 *
 * تعرض للمستخدم تبويبين:
 * 1. Today's Log: سجل الطعام اليومي.
 * 2. Explore: قائمة أطعمة جاهزة يمكن إضافتها.
 *
 * كما تحتوي على زر عائم (+)
 * يفتح شاشة AddFoods لإضافة طعام يدويًا.
 *
 * أهمية الشاشة:
 * بدون هذه الشاشة لن يستطيع المستخدم إدارة الطعام،
 * أو التنقل بين السجل اليومي وقائمة الأطعمة الجاهزة.
 */
public class FoodsActivtiy extends AppCompatActivity implements FoodListFragment.OnDataUpdateListener {

    /**
     * ViewPager2:
     * ---------------------------------------------------------
     * مسؤول عن عرض أكثر من صفحة داخل نفس الشاشة.
     * f
     * في هذه الشاشة يعرض:
     * - Fragment للسجل اليومي.
     * - Fragment للاستكشاف.
     *
     * بدون ViewPager2 لن نستطيع التنقل بين التبويبين بالسحب.
     */
    private ViewPager2 viewPager;

    /**
     * FoodPagerAdapter:
     * ---------------------------------------------------------
     * Adapter مسؤول عن تزويد ViewPager2 بالـ Fragments المناسبة.
     *
     * بدون هذا الـ Adapter لن يعرف ViewPager2
     * أي Fragment يعرض في كل تبويب.
     */
    private FoodPagerAdapter adapter;

    /**
     * onCreate:
     * ---------------------------------------------------------
     * أول دالة تعمل عند فتح شاشة FoodsActivity.
     *
     * تقوم بـ:
     * - ربط الشاشة بملف XML.
     * - تجهيز Toolbar.
     * - ربط ViewPager2 و TabLayout.
     * - إنشاء Adapter.
     * - ربط التبويبات بالصفحات.
     * - تجهيز زر إضافة الطعام.
     *
     * أهمية الدالة:
     * بدونها لن يتم بناء الشاشة أو ربط عناصرها بالكود.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ربط شاشة Java مع ملف التصميم XML الخاص بها.
        setContentView(R.layout.activity_foods_activtiy);

        /**
         * setSupportActionBar:
         * ---------------------------------------------------------
         * تجعل الـ Toolbar الموجود في XML يعمل كشريط علوي رسمي للشاشة.
         *
         * بدونها قد يظهر الـ Toolbar كشكل فقط
         * بدون التعامل معه كـ ActionBar.
         */
        setSupportActionBar(findViewById(R.id.toolbar));

        // ربط ViewPager2 من XML بالكود.
        viewPager = findViewById(R.id.viewPager);

        // ربط TabLayout من XML بالكود.
        TabLayout tabLayout = findViewById(R.id.tabLayout);

        // ربط الزر العائم من XML بالكود.
        FloatingActionButton fab = findViewById(R.id.fab_add_food);

        /**
         * إنشاء Adapter وربطه مع ViewPager2.
         *
         * أهمية هذا الجزء:
         * ViewPager2 لا يعرف وحده ماذا يعرض.
         * لذلك يحتاج Adapter يحدد له الـ Fragments.
         */
        adapter = new FoodPagerAdapter(this);
        viewPager.setAdapter(adapter);

        /**
         * TabLayoutMediator:
         * ---------------------------------------------------------
         * يربط بين TabLayout و ViewPager2.
         *
         * وظيفته:
         * عندما يضغط المستخدم على Tab،
         * ينتقل ViewPager2 للصفحة المناسبة.
         *
         * وأيضًا عندما يسحب المستخدم بين الصفحات،
         * يتغير الـ Tab تلقائيًا.
         *
         * بدون TabLayoutMediator:
         * قد يظهر TabLayout و ViewPager2،
         * لكنهما لن يكونا مربوطين ببعض.
         */
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {

            /**
             * تحديد اسم كل تبويب حسب رقمه.
             *
             * position == 0:
             * التبويب الأول هو Today's Log.
             *
             * position == 1:
             * التبويب الثاني هو Explore.
             */
            tab.setText(position == 0 ? "Today's Log" : "Explore");

        }).attach();//تفعيل الربط
        /**
         * OnClickListener للزر العائم:
         * ---------------------------------------------------------
         * ينتظر ضغط المستخدم على زر (+).
         *
         * عند الضغط:
         * ينتقل المستخدم إلى شاشة AddFoods
         * لإضافة طعام جديد يدويًا.
         *
         * بدون هذا Listener:
         * الزر سيظهر في الشاشة لكنه لن يفعل شيئًا عند الضغط عليه.
         */
        fab.setOnClickListener(v -> {

            // الانتقال إلى شاشة إضافة الطعام.
            startActivity(new Intent(this, AddFoods.class));
        });
    }

    /**
     * onResume:
     * ---------------------------------------------------------
     * تُستدعى عندما ترجع هذه الشاشة للواجهة.
     *
     * مثال:
     * إذا ذهب المستخدم إلى AddFoods ثم رجع،
     * يتم استدعاء onResume.
     *
     * استخدمناها هنا حتى نحدث العرض
     * ونعيد المستخدم إلى تبويب Today's Log.
     *
     * بدونها:
     * قد يرجع المستخدم من إضافة الطعام
     * ولا يرى التحديث مباشرة بالشكل المطلوب.
     */
    @Override
    protected void onResume() {
        super.onResume();

        // التأكد أن الـ Adapter موجود قبل استخدامه.
        if (adapter != null) onDataUpdated();
    }

    /**
     * onDataUpdated:
     * ---------------------------------------------------------
     * هذه الدالة تأتي من الواجهة:
     * FoodListFragment.OnDataUpdateListener
     *
     * يتم استدعاؤها عندما تتغير بيانات الطعام
     * مثل إضافة أو حذف طعام.
     *
     * وظيفتها هنا:
     * إعادة المستخدم إلى التبويب الأول Today's Log.
     *
     * بدونها:
     * بعد إضافة طعام من Explore أو AddFoods
     * قد يبقى المستخدم في نفس التبويب ولا يرى السجل اليومي مباشرة.
     */
    @Override
    public void onDataUpdated() {

        // الانتقال للتبويب الأول مع حركة انتقال.
        viewPager.setCurrentItem(0, true);
    }

    /**
     * FoodPagerAdapter:
     * ---------------------------------------------------------
     * كلاس داخلي مسؤول عن إدارة الـ Fragments داخل ViewPager2.
     *
     استخدام  FragmentStateAdapter
     لأنه مناسب لإدارة وعرض Fragments
     داخل ViewPager2.

     كما أنه يساعد في تحسين استهلاك الذاكرة،
     لأنه يدمر الـ Fragments غير المستخدمة
     ويعيد إنشائها عند الحاجة.
     * أهمية هذا الكلاس:
     * بدونه ViewPager2 لن يعرف عدد الصفحات
     * ولا أي Fragment يعرض في كل صفحة.
     */
    private static class FoodPagerAdapter extends FragmentStateAdapter {

        /**
         * Constructor:
         * ---------------------------------------------------------
         * يستقبل الـ Activity التي تحتوي على ViewPager2.
         *
         * نرسلها إلى super
         * حتى يستطيع FragmentStateAdapter إدارة الـ Fragments
         * داخل هذه الشاشة.
         */
        public FoodPagerAdapter(@NonNull AppCompatActivity activity) {
            super(activity);
        }

        /**
         * createFragment:
         * ---------------------------------------------------------
         * هذه الدالة يطلبها ViewPager2 من الـ Adapter.
         *
         * وظيفتها:
         * إنشاء Fragment مناسب حسب رقم التبويب.
         *
         * position == 0:
         * إنشاء Fragment خاص بالسجل اليومي.
         *
         * position == 1:
         * إنشاء Fragment خاص بقائمة Explore.
         *
         * بدون هذه الدالة:
         * ViewPager2 لن يعرف ماذا يعرض في الصفحات.
         */
        @NonNull
        @Override
        public Fragment createFragment(int position) {

            return position == 0
                    ? FoodListFragment.newInstance(FoodListFragment.TYPE_USER_LOG)
                    : FoodListFragment.newInstance(FoodListFragment.TYPE_EXPLORE);
        }

        /**
         * getItemCount:
         * ---------------------------------------------------------
         * ترجع عدد الصفحات داخل ViewPager2.
         *
         * في هذه الشاشة لدينا صفحتان فقط:
         * 1. Today's Log
         * 2. Explore
         *
         * لو رجعت رقم غير صحيح،
         * عدد التبويبات والصفحات سيكون خاطئًا.
         */
        @Override
        public int getItemCount() {
            return 2;
        }
    }
}