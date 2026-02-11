package com.example.finalhamada.data.MyTaskTable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalhamada.FoodListFragment;
import com.example.finalhamada.R;
import com.google.android.material.button.MaterialButton;

import java.util.List;
import java.util.Locale;

/**
 * ============================================================
 * FoodAdapter
 * ============================================================
 * Adapter مخصص لعرض قائمة الأطعمة والفئات في RecyclerView.
 *
 * الاستخدام:
 * - يدعم نوعين من العناصر:
 *   1️⃣ FoodCategory: رأس الفئة (Category Header)
 *   2️⃣ UserFood: عنصر الطعام الفردي
 * - يدعم نوعين من القوائم عبر listType:
 *   - TYPE_USER_LOG: الأطعمة التي أضافها المستخدم
 *   - TYPE_EXPLORE: قائمة الاستكشاف (Explore)
 *
 * التعامل مع الأحداث:
 * - onAddFoodClicked: إضافة الطعام لسجل المستخدم
 * - onDeleteFoodClicked: حذف الطعام من السجل
 * - onCategoryHeaderClicked: الضغط على رأس الفئة
 *
 * ViewHolders:
 * - CategoryViewHolder: لإظهار الفئة (Category)
 * - FoodItemViewHolder: لإظهار الطعام الفردي مع السعرات والمغذيات
 *
 * مزايا تقنية:
 * - RecyclerView.Adapter متعدد ViewTypes
 * - دعم MaterialButton للأيقونات (Add/Delete)
 * - يدعم التنسيق الديناميكي للقيم الغذائية
 * ============================================================
 */
public class FoodAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    /** نوع عرض رأس الفئة */
    private static final int VIEW_TYPE_CATEGORY_HEADER = 1;

    /** نوع عرض عنصر الطعام */
    private static final int VIEW_TYPE_FOOD_ITEM = 2;

    /** القائمة التي تعرض العناصر (FoodCategory أو UserFood) */
    private final List<Object> displayItems;

    /** Listener للتفاعل مع الأزرار */
    private final AdapterInteractionListener listener;

    /** نوع القائمة: TYPE_USER_LOG أو TYPE_EXPLORE */
    private final int listType;

    /**
     * واجهة للتعامل مع التفاعل مع المستخدم
     */
    public interface AdapterInteractionListener {
        /** عند الضغط على إضافة طعام */
        void onAddFoodClicked(UserFood food);

        /** عند الضغط على حذف طعام */
        void onDeleteFoodClicked(UserFood food);

        /** عند الضغط على رأس الفئة */
        void onCategoryHeaderClicked(int position);
    }

    /**
     * Constructor
     * @param displayItems قائمة العناصر التي سيتم عرضها
     * @param listener المستمع للتفاعل مع العناصر
     * @param listType نوع القائمة (User Log أو Explore)
     */
    public FoodAdapter(List<Object> displayItems,
                       AdapterInteractionListener listener,
                       int listType) {
        this.displayItems = displayItems;
        this.listener = listener;
        this.listType = listType;
    }

    @Override
    public int getItemViewType(int position) {
        return (displayItems.get(position) instanceof FoodCategory)
                ? VIEW_TYPE_CATEGORY_HEADER
                : VIEW_TYPE_FOOD_ITEM;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if (viewType == VIEW_TYPE_CATEGORY_HEADER) {
            View view = inflater.inflate(
                    R.layout.list_item_category_header, parent, false);
            return new CategoryViewHolder(view);
        } else {
            View view = inflater.inflate(
                    R.layout.list_item_food, parent, false);
            return new FoodItemViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(
            @NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof CategoryViewHolder) {
            FoodCategory category = (FoodCategory) displayItems.get(position);
            ((CategoryViewHolder) holder).bind(
                    category,
                    () -> listener.onCategoryHeaderClicked(holder.getAdapterPosition())
            );
        } else {
            UserFood food = (UserFood) displayItems.get(position);
            boolean isExplore = listType == FoodListFragment.TYPE_EXPLORE;
            ((FoodItemViewHolder) holder).bind(food, isExplore, listener);
        }
    }

    @Override
    public int getItemCount() {
        return displayItems.size();
    }

    // ---------------- ViewHolders ----------------

    /**
     * ============================================================
     * CategoryViewHolder
     * ============================================================
     * ViewHolder لرأس الفئة (Category Header)
     * - يعرض عنوان الفئة
     * - يدعم الضغط على الرأس لتنفيذ حدث
     */
    static class CategoryViewHolder extends RecyclerView.ViewHolder {

        private final TextView categoryName;

        CategoryViewHolder(View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.text_view_category_name);
        }

        void bind(FoodCategory category, Runnable clickListener) {
            categoryName.setText(category.getTitle());
            itemView.setOnClickListener(v -> clickListener.run());
        }
    }

    /**
     * ============================================================
     * FoodItemViewHolder
     * ============================================================
     * ViewHolder للطعام الفردي
     * - يعرض اسم الطعام
     * - يعرض السعرات والمغذيات
     * - زر لإضافة أو حذف الطعام حسب نوع القائمة
     */
    static class FoodItemViewHolder extends RecyclerView.ViewHolder {

        private final TextView foodName;
        private final TextView nutritionalInfo;
        private final MaterialButton actionButton;

        FoodItemViewHolder(View itemView) {
            super(itemView);
            foodName = itemView.findViewById(R.id.text_view_food_name);
            nutritionalInfo = itemView.findViewById(R.id.text_view_nutritional_info);

            // زر MaterialButton لإضافة أو حذف الطعام
            actionButton = itemView.findViewById(R.id.buttonAddToLog);
        }

        /**
         * ربط بيانات الطعام بالواجهة
         *
         * @param food كائن UserFood
         * @param isExplore true إذا كانت قائمة الاستكشاف
         * @param listener مستمع التفاعل مع الأزرار
         */
        void bind(UserFood food,
                  boolean isExplore,
                  AdapterInteractionListener listener) {

            foodName.setText(food.getFoodName());

            // حالة العنصر الفارغ
            if (food.getId() == -1) {
                nutritionalInfo.setVisibility(View.GONE);
                actionButton.setVisibility(View.GONE);
                itemView.setOnClickListener(null);
                return;
            }

            nutritionalInfo.setVisibility(View.VISIBLE);
            actionButton.setVisibility(View.VISIBLE);

            // تنسيق المعلومات الغذائية
            String details = String.format(
                    Locale.US,
                    "%d Kcal • P: %.1fg, C: %.1fg, F: %.1fg",
                    food.getCalories(),
                    food.getProtein(),
                    food.getCarbs(),
                    food.getFat()
            );
            nutritionalInfo.setText(details);

            // زر الإضافة أو الحذف
            if (isExplore) {
                actionButton.setIconResource(R.drawable.ic_add);
                actionButton.setOnClickListener(v ->
                        listener.onAddFoodClicked(food));
                itemView.setOnClickListener(v ->
                        listener.onAddFoodClicked(food));
            } else {
                actionButton.setIconResource(R.drawable.ic_delete);
                actionButton.setOnClickListener(v ->
                        listener.onDeleteFoodClicked(food));
                itemView.setOnClickListener(v ->
                        listener.onDeleteFoodClicked(food));
            }
        }
    }
}
