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
 * Adapter لعرض الأطعمة والفئات في RecyclerView.
 */
public class FoodAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_CATEGORY_HEADER = 1;
    private static final int VIEW_TYPE_FOOD_ITEM = 2;

    private final List<Object> displayItems;
    private final AdapterInteractionListener listener;
    private final int listType;

    public interface AdapterInteractionListener {
        void onAddFoodClicked(UserFood food);
        void onDeleteFoodClicked(UserFood food);
        void onCategoryHeaderClicked(int position);
    }.

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

    static class FoodItemViewHolder extends RecyclerView.ViewHolder {

        private final TextView foodName;
        private final TextView nutritionalInfo;
        private final MaterialButton actionButton;

        FoodItemViewHolder(View itemView) {
            super(itemView);
            foodName = itemView.findViewById(R.id.text_view_food_name);
            nutritionalInfo = itemView.findViewById(R.id.text_view_nutritional_info);

            // ✅ السطر اللي كان ناقص وسبب كل البلاوي
            actionButton = itemView.findViewById(R.id.buttonAddToLog);
        }

        void bind(UserFood food,
                  boolean isExplore,
                  AdapterInteractionListener listener) {

            foodName.setText(food.getFoodName());

            // حالة "القائمة فاضية"
            if (food.getId() == -1) {
                nutritionalInfo.setVisibility(View.GONE);
                actionButton.setVisibility(View.GONE);
                itemView.setOnClickListener(null);
                return;
            }

            nutritionalInfo.setVisibility(View.VISIBLE);
            actionButton.setVisibility(View.VISIBLE);

            String details = String.format(
                    Locale.US,
                    "%d Kcal • P: %.1fg, C: %.1fg, F: %.1fg",
                    food.getCalories(),
                    food.getProtein(),
                    food.getCarbs(),
                    food.getFat()
            );
            nutritionalInfo.setText(details);

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
