package com.example.finalhamada;

import android.content.Context; // Context يمثل البيئة التي يعمل داخلها الـ Fragment أو Activity.
import android.os.Bundle; // Bundle يستخدم لحفظ وتمرير بيانات صغيرة بين أجزاء التطبيق.
import android.view.LayoutInflater; // يستخدم لتحويل ملف XML إلى View حقيقي يظهر على الشاشة.
import android.view.View; // الأب الأساسي لكل عناصر الواجهة.
import android.view.ViewGroup; // حاوية ممكن تحتوي عناصر واجهة بداخلها.
import android.widget.Toast; // رسالة قصيرة تظهر للمستخدم.

import androidx.annotation.NonNull; // يعني أن القيمة لا يجب أن تكون null.
import androidx.annotation.Nullable; // يعني أن القيمة ممكن تكون null.
import androidx.fragment.app.Fragment; // الكلاس الأساسي لأي Fragment.
import androidx.recyclerview.widget.LinearLayoutManager; // ينظم عناصر RecyclerView بشكل عمودي.
import androidx.recyclerview.widget.RecyclerView; // عنصر لعرض قائمة طويلة بكفاءة.

import com.example.finalhamada.data.AppDataBase.AppDataBase1; // قاعدة بيانات Room الرئيسية.
import com.example.finalhamada.data.MyTaskTable.FoodAdapter; // Adapter لعرض الطعام والفئات.
import com.example.finalhamada.data.MyTaskTable.FoodCategory; // كلاس يمثل فئة طعام مثل Protein.
import com.example.finalhamada.data.MyTaskTable.UserFood; // كلاس يمثل عنصر طعام.
import com.google.android.material.dialog.MaterialAlertDialogBuilder; // Dialog لتأكيد الحذف.

import java.text.SimpleDateFormat; // لتنسيق التاريخ.
import java.util.ArrayList; // قائمة قابلة للتعديل.
import java.util.Date; // التاريخ الحالي.
import java.util.List; // واجهة عامة للقوائم.
import java.util.Locale; // لتحديد تنسيق التاريخ حسب اللغة/المنطقة.

/**
 * FoodListFragment:
 * ---------------------------------------------------------
 * هذا Fragment مسؤول عن عرض قائمة الطعام داخل FoodsActivity.
 *
 * نفس الـ Fragment يستخدم في حالتين:
 * 1. TYPE_USER_LOG:
 *    يعرض الطعام الذي أضافه المستخدم إلى سجله اليومي.
 *
 * 2. TYPE_EXPLORE:
 *    يعرض أطعمة جاهزة مقسمة حسب فئات مثل Protein وCarbs.
 *
 * أهمية الكلاس:
 * بدون هذا Fragment لن تظهر قوائم الطعام داخل تبويبات FoodsActivity.
 */
public class FoodListFragment extends Fragment implements FoodAdapter.AdapterInteractionListener {

    /**
     * TYPE_USER_LOG:
     * ---------------------------------------------------------
     * ثابت يمثل وضع عرض سجل الطعام اليومي.
     *
     * إذا كان fragmentType يساوي هذا الرقم،
     * سيتم تحميل الطعام من قاعدة بيانات Room.
     */
    public static final int TYPE_USER_LOG = 0;

    /**
     * TYPE_EXPLORE:
     * ---------------------------------------------------------
     * ثابت يمثل وضع عرض أطعمة جاهزة للاستكشاف.
     *
     * إذا كان fragmentType يساوي هذا الرقم،
     * سيتم عرض قائمة أطعمة جاهزة داخل التطبيق.
     */
    public static final int TYPE_EXPLORE = 1;

    /**
     * ARG_TYPE:
     * ---------------------------------------------------------
     * مفتاح يستخدم داخل Bundle
     * لإرسال نوع الـ Fragment عند إنشائه.
     *
     * بدون هذا المفتاح لن يعرف الـ Fragment
     * هل يجب أن يعرض Today's Log أو Explore.
     */
    private static final String ARG_TYPE = "fragment_type";

    /**
     * RecyclerView:
     * ---------------------------------------------------------
     * يعرض قائمة الطعام والفئات على الشاشة.
     *
     * بدون RecyclerView لن نستطيع عرض قائمة ديناميكية
     * من عناصر كثيرة بشكل منظم.
     */
    private RecyclerView recyclerView;

    /**
     * FoodAdapter:
     * ---------------------------------------------------------
     * يربط البيانات الموجودة في displayItems
     * مع عناصر الواجهة داخل RecyclerView.
     *
     * بدون Adapter ستبقى RecyclerView فارغة.
     */
    private FoodAdapter foodAdapter;

    /**
     * fragmentType:
     * ---------------------------------------------------------
     * يخزن نوع الـ Fragment الحالي:
     * TYPE_USER_LOG أو TYPE_EXPLORE.
     *
     * من خلاله نقرر أي بيانات يجب تحميلها.
     */
    private int fragmentType;

    /**
     * dataUpdateListener:
     * ---------------------------------------------------------
     * يستخدم لإخبار FoodsActivity
     * أن بيانات الطعام تغيرت.
     *
     * مثال:
     * عند إضافة طعام من Explore،
     * نطلب من Activity الرجوع إلى تبويب Today's Log.
     */
    private OnDataUpdateListener dataUpdateListener;

    /**
     * displayItems:
     * ---------------------------------------------------------
     * القائمة النهائية التي يتم عرضها داخل RecyclerView.
     *
     * تحتوي على نوعين:
     * - FoodCategory
     * - UserFood
     *
     * استخدمنا List<Object>
     * لأن القائمة تحتوي أكثر من نوع بيانات.
     */
    private final List<Object> displayItems = new ArrayList<>();

    /**
     * originalCategories:
     * ---------------------------------------------------------
     * القائمة الأصلية للفئات قبل تحويلها لقائمة عرض.
     *
     * نستخدمها للحفاظ على بيانات الفئات
     * وحالة الفتح والإغلاق لكل فئة.
     */
    private final List<FoodCategory> originalCategories = new ArrayList<>();

    /**
     * OnDataUpdateListener:
     * ---------------------------------------------------------
     * Interface للتواصل بين Fragment و Activity.
     *
     * عندما يحدث تغيير في البيانات داخل Fragment،
     * نستدعي onDataUpdated حتى تعرف FoodsActivity أن البيانات تغيرت.
     *
     * بدون هذه الواجهة سيكون من الصعب على Fragment
     * إخبار Activity بوجود تحديث.
     */
    public interface OnDataUpdateListener {
        void onDataUpdated();
    }

    /**
     * newInstance:
     * ---------------------------------------------------------
     * طريقة منظمة لإنشاء FoodListFragment
     * مع إرسال نوعه داخل Bundle.
     *
     * @param type نوع الـ Fragment:
     *             TYPE_USER_LOG أو TYPE_EXPLORE
     *
     * أهمية الدالة:
     * بدونها سننشئ Fragment بدون معرفة نوع البيانات
     * التي يجب عرضها.
     */
    public static FoodListFragment newInstance(int type) {
        FoodListFragment fragment = new FoodListFragment();

        Bundle args = new Bundle();

        args.putInt(ARG_TYPE, type);

        fragment.setArguments(args);

        return fragment;
    }

    /**
     * onAttach:
     * ---------------------------------------------------------
     * يتم استدعاؤها عندما يتم ربط الـ Fragment بالـ Activity.
     *
     * هنا نتأكد أن الـ Activity التي تحتوي هذا Fragment
     * تطبق OnDataUpdateListener.
     *
     * أهمية الدالة:
     * بدونها لن نستطيع إرسال إشعار إلى FoodsActivity
     * عندما يتم تحديث بيانات الطعام.
     */
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof OnDataUpdateListener) {
            dataUpdateListener = (OnDataUpdateListener) context;
        } else {
            throw new RuntimeException(context + " must implement OnDataUpdateListener");
        }
    }

    /**
     * onCreate:
     * ---------------------------------------------------------
     * يتم استدعاؤها عند إنشاء الـ Fragment.
     *
     * هنا نقرأ fragmentType من Bundle
     * حتى نعرف هل هذا Fragment يعرض:
     * - Today's Log
     * أو
     * - Explore
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            fragmentType = getArguments().getInt(ARG_TYPE);
        }
    }

    /**
     * onCreateView:
     * ---------------------------------------------------------
     * يتم استدعاؤها لإنشاء واجهة الـ Fragment.
     *
     * تقوم بـ:
     * - تحميل ملف fragment_food_list.xml
     * - ربط RecyclerView
     * - تجهيز RecyclerView عن طريق setupRecyclerView()
     *
     * بدون هذه الدالة لن تظهر واجهة الـ Fragment.
     */
    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.fragment_food_list, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);

        setupRecyclerView();

        return view;
    }

    /**
     * onResume:
     * ---------------------------------------------------------
     * يتم استدعاؤها كل مرة يرجع فيها الـ Fragment للواجهة.
     *
     * نستخدمها لتحميل البيانات من جديد
     * حتى تظهر آخر التحديثات بعد الإضافة أو الحذف.
     *
     * بدونها قد لا تظهر البيانات الجديدة مباشرة.
     */
    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    /**
     * setupRecyclerView:
     * ---------------------------------------------------------
     * تجهز RecyclerView لعرض البيانات.
     *
     * تقوم بـ:
     * - تحديد طريقة العرض العمودية بواسطة LinearLayoutManager.
     * - إنشاء FoodAdapter.
     * - ربط الـ Adapter بالـ RecyclerView.
     *
     * بدون هذه الدالة ستبقى القائمة غير جاهزة للعرض.
     */
    private void setupRecyclerView() {

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        foodAdapter = new FoodAdapter(displayItems, this, fragmentType);

        recyclerView.setAdapter(foodAdapter);
    }

    /**
     * loadData:
     * ---------------------------------------------------------
     * تحدد مصدر البيانات حسب نوع الـ Fragment.
     *
     * إذا كان TYPE_USER_LOG:
     * يتم تحميل الطعام من قاعدة البيانات.
     *
     * إذا كان TYPE_EXPLORE:
     * يتم تحميل قائمة أطعمة جاهزة.
     *
     * بعدها تستدعي buildDisplayList
     * لبناء القائمة التي ستظهر للمستخدم.
     */
    private void loadData() {

        originalCategories.clear();

        if (fragmentType == TYPE_USER_LOG) {

            originalCategories.addAll(getUserLogGroup());

        } else {

            originalCategories.addAll(getExploreFoodCategories());

        }

        buildDisplayList();
    }

    /**
     * buildDisplayList:
     * ---------------------------------------------------------
     * تبني القائمة النهائية التي تظهر في RecyclerView.
     *
     * تضيف أولًا اسم الفئة،
     * وإذا كانت الفئة مفتوحة isExpanded = true،
     * تضيف الأطعمة الموجودة داخلها.
     *
     * أهمية الدالة:
     * بدونها لن تعمل فكرة فتح وإغلاق الفئات
     * ولن تظهر العناصر بشكل منظم.
     */
    private void buildDisplayList() {

        displayItems.clear();

        for (FoodCategory category : originalCategories) {

            displayItems.add(category);

            if (category.isExpanded() && category.getFoodItems() != null) {

                displayItems.addAll(category.getFoodItems());
            }
        }

        foodAdapter.notifyDataSetChanged();
    }

    /**
     * getUserLogGroup:
     * ---------------------------------------------------------
     * تجلب الأطعمة التي أضافها المستخدم
     * من قاعدة بيانات Room.
     *
     * إذا لم يكن هناك طعام،
     * تضيف عنصرًا وهميًا يخبر المستخدم أن السجل فارغ.
     *
     * أهمية الدالة:
     * بدونها لن يظهر سجل الطعام اليومي للمستخدم.
     */
    private List<FoodCategory> getUserLogGroup() {

        List<FoodCategory> categoryList = new ArrayList<>();

        AppDataBase1 db = AppDataBase1.getDatabase(getContext());

        List<UserFood> userFoods = db.userFoodQuery().getAll();

        if (userFoods.isEmpty()) {

            UserFood exampleFood =
                    new UserFood("Log is empty. Add food from Explore!", 0, 0, 0, 0);

            exampleFood.setId(-1);

            userFoods.add(exampleFood);
        }

        categoryList.add(
                new FoodCategory("Today's Log", userFoods, true)
        );

        return categoryList;
    }

    /**
     * getExploreFoodCategories:
     * ---------------------------------------------------------
     * تنشئ قائمة أطعمة جاهزة داخل التطبيق
     * مقسمة حسب الفئات:
     * Protein, Carbs, Fats, Fruits, Vegetables.
     *
     * هذه البيانات ليست من قاعدة البيانات،
     * بل بيانات جاهزة تساعد المستخدم يضيف أطعمة بسرعة.
     *
     * بدون هذه الدالة لن يظهر تبويب Explore.
     */
    private List<FoodCategory> getExploreFoodCategories() {

        List<FoodCategory> categories = new ArrayList<>();

        List<UserFood> proteinList = new ArrayList<>();

        proteinList.add(new UserFood("Chicken Breast", 165, 31, 0, 3.6));
        proteinList.add(new UserFood("Egg", 78, 6, 1, 5));
        proteinList.add(new UserFood("Tuna", 132, 28, 0, 1));
        proteinList.add(new UserFood("Greek Yogurt", 100, 10, 4, 0));

        categories.add(new FoodCategory("Protein", proteinList, true));

        List<UserFood> carbsList = new ArrayList<>();

        carbsList.add(new UserFood("Rice", 206, 4, 45, 0));
        carbsList.add(new UserFood("Potato", 161, 4, 37, 0));
        carbsList.add(new UserFood("Oats", 389, 17, 66, 7));

        categories.add(new FoodCategory("Carbs", carbsList, false));

        List<UserFood> fatsList = new ArrayList<>();

        fatsList.add(new UserFood("Avocado", 160, 2, 9, 15));
        fatsList.add(new UserFood("Olive Oil", 119, 0, 0, 14));
        fatsList.add(new UserFood("Almonds", 164, 6, 6, 14));

        categories.add(new FoodCategory("Fats", fatsList, false));

        List<UserFood> fruitsList = new ArrayList<>();

        fruitsList.add(new UserFood("Banana", 105, 1, 27, 0));
        fruitsList.add(new UserFood("Apple", 95, 0, 25, 0));
        fruitsList.add(new UserFood("Orange", 62, 1, 15, 0));

        categories.add(new FoodCategory("Fruits", fruitsList, false));

        List<UserFood> vegetablesList = new ArrayList<>();

        vegetablesList.add(new UserFood("Broccoli", 55, 4, 11, 0));
        vegetablesList.add(new UserFood("Carrot", 41, 1, 10, 0));
        vegetablesList.add(new UserFood("Spinach", 23, 3, 4, 0));

        categories.add(new FoodCategory("Vegetables", vegetablesList, false));

        return categories;
    }

    /**
     * onAddFoodClicked:
     * ---------------------------------------------------------
     * يتم استدعاؤها عندما يضغط المستخدم
     * على زر إضافة طعام من Explore.
     *
     * تقوم بـ:
     * - إنشاء نسخة جديدة من الطعام المختار.
     * - إضافة تاريخ اليوم.
     * - حفظ الطعام داخل Room Database.
     * - عرض رسالة نجاح.
     * - إخبار FoodsActivity أن البيانات تغيرت.
     *
     * بدونها لن يستطيع المستخدم إضافة الطعام
     * من Explore إلى Today's Log.
     */
    @Override
    public void onAddFoodClicked(UserFood food) {

        AppDataBase1 db = AppDataBase1.getDatabase(getContext());

        UserFood newFoodEntry = new UserFood(
                food.getFoodName(),
                food.getCalories(),
                food.getProtein(),
                food.getCarbs(),
                food.getFat()
        );

        newFoodEntry.setDate(
                new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                        .format(new Date())
        );

        db.userFoodQuery().insert(newFoodEntry);

        Toast.makeText(
                getContext(),
                food.getFoodName() + " added!",
                Toast.LENGTH_SHORT
        ).show();

        if (dataUpdateListener != null) {
            dataUpdateListener.onDataUpdated();
        }
    }

    /**
     * onDeleteFoodClicked:
     * ---------------------------------------------------------
     * يتم استدعاؤها عندما يضغط المستخدم
     * على حذف طعام من Today's Log.
     *
     * تقوم بعرض Dialog للتأكيد قبل الحذف.
     *
     * إذا وافق المستخدم:
     * - يتم حذف الطعام من Room Database.
     * - تظهر رسالة نجاح.
     * - يتم تحميل البيانات من جديد.
     *
     * بدونها لن يستطيع المستخدم حذف طعام من سجله.
     */
    @Override
    public void onDeleteFoodClicked(UserFood food) {

        if (food.getId() == -1) return;

        new MaterialAlertDialogBuilder(requireContext())

                .setTitle("Delete Food")

                .setMessage("Delete '" + food.getFoodName() + "' from your log?")

                .setNegativeButton("Cancel", null)

                .setPositiveButton("Delete", (dialog, which) -> {

                    AppDataBase1 db = AppDataBase1.getDatabase(getContext());

                    db.userFoodQuery().deleteFoodById(food.getId());

                    Toast.makeText(
                            getContext(),
                            "Food deleted",
                            Toast.LENGTH_SHORT
                    ).show();

                    loadData();
                })

                .show();
    }

    /**
     * onCategoryHeaderClicked:
     * ---------------------------------------------------------
     * يتم استدعاؤها عندما يضغط المستخدم
     * على عنوان فئة مثل Protein أو Carbs.
     *
     * تقوم بتغيير حالة الفئة:
     * - إذا كانت مفتوحة تصبح مغلقة.
     * - إذا كانت مغلقة تصبح مفتوحة.
     *
     * ثم تعيد بناء القائمة.
     *
     * بدونها لن تعمل ميزة Expand / Collapse للفئات.
     */
    @Override
    public void onCategoryHeaderClicked(int position) {

        if (displayItems.get(position) instanceof FoodCategory) {

            FoodCategory category =
                    (FoodCategory) displayItems.get(position);

            category.setExpanded(!category.isExpanded());

            buildDisplayList();
        }
    }
}