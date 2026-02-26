package com.example.finalhamada; // تعريف الحزمة التي ينتمي لها هذا الكلاس داخل المشروع

import android.content.Context; // Context يمثل البيئة الحالية (Activity) ويُستخدم للوصول للموارد وقاعدة البيانات
import android.os.Bundle; // Bundle يُستخدم لحفظ البيانات المؤقتة عند إنشاء الـ Fragment
import android.view.LayoutInflater; // LayoutInflater يحول XML إلى View حقيقي
import android.view.View; // View هو الكلاس الأب لكل عناصر الواجهة
import android.view.ViewGroup; // ViewGroup هو حاوية تحتوي عناصر أخرى
import android.widget.Toast; // Toast لعرض رسالة قصيرة للمستخدم

import androidx.annotation.NonNull; // تعني أن القيمة لا يمكن أن تكون null
import androidx.annotation.Nullable; // تعني أن القيمة ممكن أن تكون null
import androidx.fragment.app.Fragment; // Fragment هو جزء من الشاشة داخل Activity
import androidx.recyclerview.widget.LinearLayoutManager; // يحدد ترتيب العناصر عموديًا في RecyclerView
import androidx.recyclerview.widget.RecyclerView; // RecyclerView لعرض قائمة عناصر قابلة للتمرير

import com.example.finalhamada.data.AppDataBase.AppDataBase1; // قاعدة بيانات Room المحلية
import com.example.finalhamada.data.MyTaskTable.FoodAdapter; // Adapter يربط البيانات مع RecyclerView
import com.example.finalhamada.data.MyTaskTable.FoodCategory; // Model يمثل فئة طعام (عنوان + عناصر)
import com.example.finalhamada.data.MyTaskTable.UserFood; // Model يمثل عنصر طعام واحد
import com.google.android.material.dialog.MaterialAlertDialogBuilder; // لإنشاء Dialog حديث

import java.text.SimpleDateFormat; // لتنسيق التاريخ كنص
import java.util.ArrayList; // قائمة ديناميكية
import java.util.Date; // يمثل الوقت الحالي
import java.util.List; // Interface يمثل قائمة
import java.util.Locale; // لتحديد لغة وتنسيق النظام

public class FoodListFragment extends Fragment implements FoodAdapter.AdapterInteractionListener {
// تعريف Fragment جديد ويطبق Interface من Adapter للتفاعل مع الضغطات

    public static final int TYPE_USER_LOG = 0; // ثابت يمثل عرض سجل المستخدم
    public static final int TYPE_EXPLORE = 1; // ثابت يمثل عرض قائمة الاستكشاف
    private static final String ARG_TYPE = "fragment_type"; // مفتاح لتخزين نوع الـ Fragment داخل Bundle

    private RecyclerView recyclerView; // RecyclerView لعرض القائمة
    private FoodAdapter foodAdapter; // Adapter لربط البيانات بالعرض
    private int fragmentType; // يخزن نوع الـ Fragment الحالي
    private OnDataUpdateListener dataUpdateListener; // Interface للتواصل مع Activity

    private final List<Object> displayItems = new ArrayList<>(); // قائمة عناصر العرض (فئات + أطعمة)
    private final List<FoodCategory> originalCategories = new ArrayList<>(); // قائمة الفئات الأصلية

    public interface OnDataUpdateListener { // Interface تُستخدم لإبلاغ الـ Activity بتحديث البيانات
        void onDataUpdated(); // دالة تُستدعى عند إضافة أو حذف طعام
    }

    public static FoodListFragment newInstance(int type) { // دالة لإنشاء Fragment مع تحديد نوعه
        FoodListFragment fragment = new FoodListFragment(); // إنشاء Fragment
        Bundle args = new Bundle(); // إنشاء Bundle
        args.putInt(ARG_TYPE, type); // تخزين النوع داخل Bundle
        fragment.setArguments(args); // تمرير البيانات للـ Fragment
        return fragment; // إرجاع Fragment جاهز
    }

    @Override
    public void onAttach(@NonNull Context context) { // تُستدعى عند ربط الـ Fragment بالـ Activity
        super.onAttach(context); // استدعاء الأب
        if (context instanceof OnDataUpdateListener) { // التأكد أن الـ Activity تطبق الواجهة
            dataUpdateListener = (OnDataUpdateListener) context; // حفظ المرجع
        } else {
            throw new RuntimeException(context + " must implement OnDataUpdateListener"); // خطأ إذا لم تطبق Activity الواجهة
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) { // تُستدعى عند إنشاء الـ Fragment
        super.onCreate(savedInstanceState); // استدعاء الأب
        if (getArguments() != null) { // التأكد أن هناك بيانات مرسلة
            fragmentType = getArguments().getInt(ARG_TYPE); // قراءة نوع Fragment
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food_list, container, false); // تحويل XML إلى View
        recyclerView = view.findViewById(R.id.recyclerView); // ربط RecyclerView
        setupRecyclerView(); // إعداد RecyclerView
        return view; // إرجاع View النهائي
    }

    @Override
    public void onResume() { // تُستدعى عند العودة للـ Fragment
        super.onResume(); // استدعاء الأب
        loadData(); // إعادة تحميل البيانات
    }

    private void setupRecyclerView() {
        // وظيفة الدالة: إعداد RecyclerView (تحديد الاتجاه وربط Adapter)
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext())); // عرض العناصر عموديًا
        foodAdapter = new FoodAdapter(displayItems, this, fragmentType); // إنشاء Adapter وتمرير القائمة
        recyclerView.setAdapter(foodAdapter); // ربط Adapter بالـ RecyclerView
    }

    private void loadData() {
        // وظيفة الدالة: تحميل البيانات حسب نوع الـ Fragment
        originalCategories.clear(); // مسح البيانات القديمة
        if (fragmentType == TYPE_USER_LOG) { // إذا كان عرض سجل المستخدم
            originalCategories.addAll(getUserLogGroup()); // جلب سجل المستخدم
        } else {
            originalCategories.addAll(getExploreFoodCategories()); // جلب قائمة الاستكشاف
        }
        buildDisplayList(); // بناء القائمة النهائية للعرض
    }

    private void buildDisplayList() {
        // وظيفة الدالة: تجهيز القائمة المعروضة (فئات + عناصر داخلها)
        displayItems.clear(); // مسح القائمة
        for (FoodCategory category : originalCategories) { // المرور على كل فئة
            displayItems.add(category); // إضافة عنوان الفئة
            if (category.isExpanded() && category.getFoodItems() != null) { // إذا كانت الفئة مفتوحة
                displayItems.addAll(category.getFoodItems()); // إضافة عناصرها
            }
        }
        if (foodAdapter != null) { // التأكد من وجود Adapter
            foodAdapter.notifyDataSetChanged(); // تحديث العرض
        }
    }

    private List<FoodCategory> getUserLogGroup() {
        // وظيفة الدالة: جلب سجل المستخدم من قاعدة بيانات Room
        List<FoodCategory> categoryList = new ArrayList<>(); // إنشاء قائمة فئات
        AppDataBase1 db = AppDataBase1.getDatabase(getContext()); // جلب قاعدة البيانات
        List<UserFood> userFoods = db.userFoodQuery().getAll(); // جلب كل الأطعمة

        if (userFoods.isEmpty()) { // إذا السجل فارغ
            UserFood exampleFood = new UserFood("Log is empty. Add food from Explore!", 0, 0, 0, 0); // إنشاء عنصر وهمي
            exampleFood.setId(-1); // تعيين ID خاص لتمييزه
            userFoods.add(exampleFood); // إضافته للقائمة
        }

        categoryList.add(new FoodCategory("Today's Log", userFoods, true)); // إنشاء فئة بعنوان Today's Log
        return categoryList; // إرجاع القائمة
    }

    private List<FoodCategory> getExploreFoodCategories() {
        // وظيفة الدالة: إنشاء قائمة أطعمة جاهزة للاستكشاف
        List<FoodCategory> categories = new ArrayList<>(); // قائمة الفئات

        // (باقي الكود كما هو لإنشاء قوائم البروتين، الكارب، الدهون، الخضار، الفواكه)
        // كل سطر proteinList.add(...) ينشئ عنصر UserFood جديد بقيم غذائية محددة
        // ثم يتم تجميعهم داخل FoodCategory باسم الفئة

        return categories; // إرجاع القائمة النهائية
    }

    @Override
    public void onAddFoodClicked(UserFood food) {
        // وظيفة الدالة: إضافة طعام إلى سجل المستخدم
        AppDataBase1 db = AppDataBase1.getDatabase(getContext()); // جلب قاعدة البيانات
        UserFood newFoodEntry = new UserFood(food.getFoodName(), food.getCalories(), food.getProtein(), food.getCarbs(), food.getFat()); // إنشاء نسخة جديدة
        newFoodEntry.setDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date())); // حفظ تاريخ اليوم
        db.userFoodQuery().insert(newFoodEntry); // إدخال الطعام في Room

        Toast.makeText(getContext(), food.getFoodName() + " added!", Toast.LENGTH_SHORT).show(); // رسالة نجاح

        if (dataUpdateListener != null) { // إذا كان هناك Listener
            dataUpdateListener.onDataUpdated(); // إبلاغ Activity أن البيانات تغيرت
        }
    }

    @Override
    public void onDeleteFoodClicked(UserFood food) {
        // وظيفة الدالة: حذف طعام من السجل
        if (food.getId() == -1) return; // لا تحذف العنصر الوهمي

        new MaterialAlertDialogBuilder(requireContext()) // إنشاء Dialog تأكيد
                .setTitle("Delete Food") // عنوان
                .setMessage("Delete '" + food.getFoodName() + "' from your log?") // رسالة
                .setNegativeButton("Cancel", null) // زر إلغاء
                .setPositiveButton("Delete", (dialog, which) -> { // زر تأكيد
                    AppDataBase1 db = AppDataBase1.getDatabase(getContext()); // جلب قاعدة البيانات
                    db.userFoodQuery().deleteFoodById(food.getId()); // حذف الطعام حسب ID
                    Toast.makeText(getContext(), "Food deleted", Toast.LENGTH_SHORT).show(); // رسالة نجاح
                    loadData(); // إعادة تحميل البيانات
                })
                .show(); // عرض الـ Dialog
    }

    @Override
    public void onCategoryHeaderClicked(int position) {
        // وظيفة الدالة: فتح/إغلاق الفئة عند الضغط عليها
        if (displayItems.get(position) instanceof FoodCategory) { // التأكد أنه عنوان فئة
            FoodCategory category = (FoodCategory) displayItems.get(position); // تحويله إلى FoodCategory
            category.setExpanded(!category.isExpanded()); // عكس حالة الفتح/الإغلاق
            buildDisplayList(); // إعادة بناء القائمة
        }
    }
}