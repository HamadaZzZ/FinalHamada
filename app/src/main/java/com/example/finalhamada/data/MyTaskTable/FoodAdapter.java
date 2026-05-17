package com.example.finalhamada.data.MyTaskTable; // مكان الكلاس داخل المشروع

import android.view.LayoutInflater; // يستخدم لتحويل ملفات XML إلى Views حقيقية
import android.view.View; // يمثل عنصر واجهة داخل Android
import android.view.ViewGroup; // الحاوية الأب لعناصر RecyclerView
import android.widget.TextView; // يستخدم لعرض النصوص

import androidx.annotation.NonNull; // يعني أن القيمة لا يجب أن تكون null
import androidx.recyclerview.widget.RecyclerView; // يستخدم لعرض قائمة عناصر بكفاءة

import com.example.finalhamada.FoodListFragment; // للوصول إلى أنواع القوائم TYPE_EXPLORE و TYPE_USER_LOG
import com.example.finalhamada.R; // للوصول إلى ملفات المشروع مثل layouts و ids
import com.google.android.material.button.MaterialButton; // زر بتصميم Material Design

import java.util.List; // لتخزين قائمة العناصر
import java.util.Locale; // لتنسيق النصوص والأرقام

/**
 * ============================================================
 * FoodAdapter
 * ============================================================
 *
 * هذا الكلاس هو Adapter مخصص
 * لعرض الأطعمة والفئات داخل RecyclerView.
 *
 * وظيفته الأساسية:
 * - عرض Categories مثل Protein وCarbs.
 * - عرض الأطعمة داخل كل Category.
 * - التعامل مع أزرار Add وDelete.
 * - دعم نوعين مختلفين من القوائم:
 *   1. Explore
 *   2. User Log
 *
 * أهمية هذا الكلاس:
 * RecyclerView لا يستطيع عرض البيانات وحده،
 * لذلك استخدمت Adapter حتى يكون وسيط
 * بين البيانات والواجهة.
 *
 * استخدمت هذا الكلاس داخل FoodListFragment
 * لتنظيم وعرض الأطعمة بشكل مرتب وقابل للتفاعل.
 */
public class FoodAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    /**
     * ============================================================
     * VIEW_TYPE_CATEGORY_HEADER
     * ============================================================
     *
     * يمثل نوع العنصر الخاص برأس الفئة.
     *
     * استخدمته حتى أميز بين:
     * - Category Header
     * - Food Item
     *
     * داخل RecyclerView.
     */
    private static final int VIEW_TYPE_CATEGORY_HEADER = 1;

    /**
     * ============================================================
     * VIEW_TYPE_FOOD_ITEM
     * ============================================================
     *
     * يمثل نوع العنصر الخاص بالطعام الفردي.
     */
    private static final int VIEW_TYPE_FOOD_ITEM = 2;

    /**
     * ============================================================
     * displayItems
     * ============================================================
     *
     * هذه القائمة تحتوي على جميع العناصر
     * التي سيتم عرضها داخل RecyclerView.
     *
     * قد تحتوي على:
     * - FoodCategory
     * - UserFood
     *
     * لذلك استخدمت List<Object>.
     */
    private final List<Object> displayItems;

    /**
     * ============================================================
     * listener
     * ============================================================
     *
     * مستمع للتفاعل مع المستخدم.
     *
     * استخدمته للتعامل مع:
     * - إضافة طعام
     * - حذف طعام
     * - الضغط على Category
     *
     * بدون وضع المنطق داخل Adapter نفسه.
     */
    private final AdapterInteractionListener listener;

    /**
     * ============================================================
     * listType
     * ============================================================
     *
     * يحدد نوع القائمة الحالية:
     * - TYPE_USER_LOG
     * - TYPE_EXPLORE
     *
     * استخدمته لتحديد:
     * هل الزر سيكون Add أو Delete.
     */
    private final int listType;

    /**
     * ============================================================
     * AdapterInteractionListener
     * ============================================================
     *
     * Interface تستخدم للتواصل
     * بين الـ Adapter وFoodListFragment.
     *
     * أهميتها:
     * عند ضغط المستخدم على زر،
     * يقوم Adapter بإرسال الحدث للشاشة.
     */
    public interface AdapterInteractionListener {

        /**
         * ============================================================
         * onAddFoodClicked
         * ============================================================
         *
         * تعمل عند ضغط المستخدم
         * على زر إضافة الطعام.
         *
         * @param food الطعام الذي سيتم إضافته.
         */
        void onAddFoodClicked(UserFood food);

        /**
         * ============================================================
         * onDeleteFoodClicked
         * ============================================================
         *
         * تعمل عند ضغط المستخدم
         * على زر حذف الطعام.
         *
         * @param food الطعام الذي سيتم حذفه.
         */
        void onDeleteFoodClicked(UserFood food);

        /**
         * ============================================================
         * onCategoryHeaderClicked
         * ============================================================
         *
         * تعمل عند ضغط المستخدم
         * على رأس الفئة Category Header.
         *
         * @param position موقع الفئة داخل القائمة.
         */
        void onCategoryHeaderClicked(int position);
    }

    /**
     * ============================================================
     * Constructor
     * ============================================================
     *
     * يعمل عند إنشاء FoodAdapter.
     *
     * يستقبل:
     * - قائمة العناصر
     * - listener
     * - نوع القائمة
     *
     * @param displayItems العناصر التي سيتم عرضها.
     * @param listener مستمع التفاعل.
     * @param listType نوع القائمة.
     */
    public FoodAdapter(List<Object> displayItems,
                       AdapterInteractionListener listener,
                       int listType) {

        this.displayItems = displayItems;
        this.listener = listener;
        this.listType = listType;
    }

    /**
     * ============================================================
     * getItemViewType
     * ============================================================
     *
     * تحدد نوع العنصر الحالي.
     *
     * إذا كان العنصر FoodCategory
     * نرجع VIEW_TYPE_CATEGORY_HEADER.
     *
     * غير ذلك نرجع VIEW_TYPE_FOOD_ITEM.
     *
     * استخدمتها لأن RecyclerView
     * يحتوي أكثر من شكل عنصر.
     */
    @Override
    public int getItemViewType(int position) {

        return (displayItems.get(position) instanceof FoodCategory)
                ? VIEW_TYPE_CATEGORY_HEADER
                : VIEW_TYPE_FOOD_ITEM;
    }

    /**
     * ============================================================
     * onCreateViewHolder
     * ============================================================
     *
     * مسؤولة عن إنشاء شكل العنصر
     * داخل RecyclerView.
     *
     * إذا كان العنصر Category:
     * يتم استخدام list_item_category_header.
     *
     * إذا كان Food Item:
     * يتم استخدام list_item_food.
     *
     * @param parent الحاوية الأب.
     * @param viewType نوع العنصر.
     *
     * @return ViewHolder مناسب لنوع العنصر.
     */
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {

        /**
         * LayoutInflater:
         * يحول XML إلى View حقيقي.
         */
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        /**
         * إذا كان العنصر Category Header
         * نستخدم layout خاص بالفئات.
         */
        if (viewType == VIEW_TYPE_CATEGORY_HEADER) {

            View view = inflater.inflate(
                    R.layout.list_item_category_header,
                    parent,
                    false
            );

            return new CategoryViewHolder(view);

        } else {

            /**
             * إذا كان Food Item
             * نستخدم layout خاص بالطعام.
             */
            View view = inflater.inflate(
                    R.layout.list_item_food,
                    parent,
                    false
            );

            return new FoodItemViewHolder(view);
        }
    }

    /**
     * ============================================================
     * onBindViewHolder
     * ============================================================
     *
     * مسؤولة عن ربط البيانات
     * مع عناصر الواجهة.
     *
     * تعمل لكل عنصر داخل RecyclerView.
     *
     * إذا كان العنصر Category:
     * يتم عرض عنوان الفئة.
     *
     * إذا كان Food Item:
     * يتم عرض بيانات الطعام.
     *
     * @param holder يحتوي عناصر الواجهة.
     * @param position مكان العنصر داخل القائمة.
     */
    @Override
    public void onBindViewHolder(
            @NonNull RecyclerView.ViewHolder holder,
            int position) {

        /**
         * إذا كان العنصر CategoryViewHolder.
         */
        if (holder instanceof CategoryViewHolder) {

            /**
             * تحويل العنصر إلى FoodCategory.
             */
            FoodCategory category =
                    (FoodCategory) displayItems.get(position);

            /**
             * ربط بيانات الفئة مع الواجهة.
             */
            ((CategoryViewHolder) holder).bind(

                    category,

                    /**
                     * عند الضغط على Category Header
                     * يتم إرسال الحدث إلى الشاشة.
                     */
                    () -> listener.onCategoryHeaderClicked(
                            holder.getAdapterPosition()
                    )
            );

        } else {

            /**
             * إذا كان العنصر Food Item.
             */
            UserFood food =
                    (UserFood) displayItems.get(position);

            /**
             * التحقق إذا القائمة الحالية Explore.
             */
            boolean isExplore =
                    listType == FoodListFragment.TYPE_EXPLORE;

            /**
             * ربط بيانات الطعام مع الواجهة.
             */
            ((FoodItemViewHolder) holder).bind(
                    food,
                    isExplore,
                    listener
            );
        }
    }

    /**
     * ============================================================
     * getItemCount
     * ============================================================
     *
     * ترجع عدد العناصر
     * الموجودة داخل RecyclerView.
     *
     * @return عدد العناصر داخل القائمة.
     */
    @Override
    public int getItemCount() {
        return displayItems.size();
    }

    // ============================================================
    // CategoryViewHolder
    // ============================================================

    /**
     * ============================================================
     * CategoryViewHolder
     * ============================================================
     *
     * ViewHolder خاص بعرض Category Header.
     *
     * وظيفته:
     * - عرض اسم الفئة.
     * - التعامل مع الضغط على الفئة.
     */
    static class CategoryViewHolder extends RecyclerView.ViewHolder {

        /**
         * TextView يعرض اسم الفئة.
         */
        private final TextView categoryName;

        /**
         * Constructor
         *
         * @param itemView يمثل شكل العنصر الواحد.
         */
        CategoryViewHolder(View itemView) {
            super(itemView);

            /**
             * ربط TextView الخاص باسم الفئة.
             */
            categoryName =
                    itemView.findViewById(R.id.text_view_category_name);
        }

        /**
         * ============================================================
         * bind
         * ============================================================
         *
         * تربط بيانات الفئة مع الواجهة.
         *
         * @param category الفئة الحالية.
         * @param clickListener حدث الضغط على الفئة.
         */
        void bind(FoodCategory category, Runnable clickListener) {

            /**
             * عرض اسم الفئة داخل TextView.
             */
            categoryName.setText(category.getTitle());

            /**
             * التعامل مع الضغط على Category Header.
             */
            itemView.setOnClickListener(v -> clickListener.run());
        }
    }

    // ============================================================
    // FoodItemViewHolder
    // ============================================================

    /**
     * ============================================================
     * FoodItemViewHolder
     * ============================================================
     *
     * ViewHolder خاص بعرض الطعام الفردي.
     *
     * وظيفته:
     * - عرض اسم الطعام.
     * - عرض السعرات والمغذيات.
     * - التعامل مع زر Add/Delete.
     */
    static class FoodItemViewHolder extends RecyclerView.ViewHolder {

        /**
         * TextView لعرض اسم الطعام.
         */
        private final TextView foodName;

        /**
         * TextView لعرض المعلومات الغذائية.
         */
        private final TextView nutritionalInfo;

        /**
         * MaterialButton للإضافة أو الحذف.
         */
        private final MaterialButton actionButton;

        /**
         * Constructor
         *
         * @param itemView يمثل شكل عنصر الطعام.
         */
        FoodItemViewHolder(View itemView) {
            super(itemView);

            /**
             * ربط TextView الخاص باسم الطعام.
             */
            foodName =
                    itemView.findViewById(R.id.text_view_food_name);

            /**
             * ربط TextView الخاص بالمعلومات الغذائية.
             */
            nutritionalInfo =
                    itemView.findViewById(R.id.text_view_nutritional_info);

            /**
             * ربط زر الإضافة أو الحذف.
             */
            actionButton =
                    itemView.findViewById(R.id.buttonAddToLog);
        }

        /**
         * ============================================================
         * bind
         * ============================================================
         *
         * مسؤولة عن ربط بيانات الطعام
         * مع عناصر الواجهة.
         *
         * @param food كائن الطعام.
         * @param isExplore هل القائمة Explore.
         * @param listener مستمع التفاعل.
         */
        void bind(UserFood food,
                  boolean isExplore,
                  AdapterInteractionListener listener) {

            /**
             * عرض اسم الطعام.
             */
            foodName.setText(food.getFoodName());

            /**
             * إذا كان العنصر Placeholder فارغ.
             */
            if (food.getId() == -1) {

                /**
                 * إخفاء المعلومات الغذائية.
                 */
                nutritionalInfo.setVisibility(View.GONE);

                /**
                 * إخفاء الزر.
                 */
                actionButton.setVisibility(View.GONE);

                /**
                 * إزالة الضغط عن العنصر.
                 */
                itemView.setOnClickListener(null);

                return;
            }

            /**
             * إظهار المعلومات الغذائية.
             */
            nutritionalInfo.setVisibility(View.VISIBLE);

            /**
             * إظهار الزر.
             */
            actionButton.setVisibility(View.VISIBLE);

            /**
             * تنسيق القيم الغذائية.
             */
            String details = String.format(
                    Locale.US,
                    "%d Kcal • P: %.1fg, C: %.1fg, F: %.1fg",
                    food.getCalories(),
                    food.getProtein(),
                    food.getCarbs(),
                    food.getFat()
            );

            /**
             * عرض التفاصيل داخل TextView.
             */
            nutritionalInfo.setText(details);

            /**
             * إذا كانت القائمة Explore
             * يظهر زر Add.
             */
            if (isExplore) {

                /**
                 * وضع أيقونة الإضافة.
                 */
                actionButton.setIconResource(R.drawable.ic_add);

                /**
                 * عند الضغط يتم إضافة الطعام.
                 */
                actionButton.setOnClickListener(v ->
                        listener.onAddFoodClicked(food));

                /**
                 * الضغط على العنصر كامل أيضًا يضيف الطعام.
                 */
                itemView.setOnClickListener(v ->
                        listener.onAddFoodClicked(food));

            } else {

                /**
                 * إذا كانت القائمة User Log
                 * يظهر زر Delete.
                 */
                actionButton.setIconResource(R.drawable.ic_delete);

                /**
                 * عند الضغط يتم حذف الطعام.
                 */
                actionButton.setOnClickListener(v ->
                        listener.onDeleteFoodClicked(food));

                /**
                 * الضغط على العنصر كامل أيضًا يحذف الطعام.
                 */
                itemView.setOnClickListener(v ->
                        listener.onDeleteFoodClicked(food));
            }
        }
    }
}