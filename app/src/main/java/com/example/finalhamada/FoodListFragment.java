package com.example.finalhamada;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalhamada.data.AppDataBase.AppDataBase1;
import com.example.finalhamada.data.MyTaskTable.FoodAdapter;
import com.example.finalhamada.data.MyTaskTable.FoodCategory;
import com.example.finalhamada.data.MyTaskTable.UserFood;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FoodListFragment extends Fragment implements FoodAdapter.AdapterInteractionListener {

    public static final int TYPE_USER_LOG = 0;
    public static final int TYPE_EXPLORE = 1;
    private static final String ARG_TYPE = "fragment_type";

    private RecyclerView recyclerView;
    private FoodAdapter foodAdapter;
    private int fragmentType;
    private OnDataUpdateListener dataUpdateListener;

    private final List<Object> displayItems = new ArrayList<>();
    private final List<FoodCategory> originalCategories = new ArrayList<>();

    public interface OnDataUpdateListener {
        void onDataUpdated();
    }

    public static FoodListFragment newInstance(int type) {
        FoodListFragment fragment = new FoodListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnDataUpdateListener) {
            dataUpdateListener = (OnDataUpdateListener) context;
        } else {
            throw new RuntimeException(context + " must implement OnDataUpdateListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            fragmentType = getArguments().getInt(ARG_TYPE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food_list, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        setupRecyclerView();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        foodAdapter = new FoodAdapter(displayItems, this, fragmentType);
        recyclerView.setAdapter(foodAdapter);
    }

    private void loadData() {
        originalCategories.clear();
        if (fragmentType == TYPE_USER_LOG) {
            originalCategories.addAll(getUserLogGroup());
        } else {
            originalCategories.addAll(getExploreFoodCategories());
        }
        buildDisplayList();
    }

    private void buildDisplayList() {
        displayItems.clear();
        for (FoodCategory category : originalCategories) {
            displayItems.add(category);
            if (category.isExpanded() && category.getFoodItems() != null) {
                displayItems.addAll(category.getFoodItems());
            }
        }
        if (foodAdapter != null) {
            foodAdapter.notifyDataSetChanged();
        }
    }

    private List<FoodCategory> getUserLogGroup() {
        List<FoodCategory> categoryList = new ArrayList<>();
        AppDataBase1 db = AppDataBase1.getDatabase(getContext());
        List<UserFood> userFoods = db.userFoodQuery().getAll();

        if (userFoods.isEmpty()) {
            UserFood exampleFood = new UserFood("Log is empty. Add food from Explore!", 0, 0, 0, 0);
            exampleFood.setId(-1);
            userFoods.add(exampleFood);
        }

        categoryList.add(new FoodCategory("Today's Log", userFoods, true));
        return categoryList;
    }

    private List<FoodCategory> getExploreFoodCategories() {
        List<FoodCategory> categories = new ArrayList<>();

        // 1️⃣ بروتين
        List<UserFood> proteinList = new ArrayList<>();
        proteinList.add(new UserFood("Chicken Breast (100g)", 165, 31, 0, 3.6));
        proteinList.add(new UserFood("Salmon (100g)", 208, 20, 0, 13));
        proteinList.add(new UserFood("Egg (1 large)", 70, 6, 0, 5));
        proteinList.add(new UserFood("Beef Steak (100g)", 250, 26, 0, 15));
        proteinList.add(new UserFood("Tofu (100g)", 90, 8, 2, 5));
        proteinList.add(new UserFood("Greek Yogurt (200g)", 120, 10, 5, 4));
        proteinList.add(new UserFood("Cottage Cheese (100g)", 98, 11, 3, 4));
        categories.add(new FoodCategory("Protein", proteinList, false));

        // 2️⃣ كاربوهيدرات
        List<UserFood> carbsList = new ArrayList<>();
        carbsList.add(new UserFood("White Rice (1 cup)", 204, 4.2, 45, 0.4));
        carbsList.add(new UserFood("Brown Rice (1 cup)", 215, 5, 45, 1.8));
        carbsList.add(new UserFood("Oats (50g)", 190, 6, 33, 3.5));
        carbsList.add(new UserFood("Quinoa (100g)", 120, 4, 21, 2));
        carbsList.add(new UserFood("Sweet Potato (100g)", 86, 1.6, 20, 0.1));
        carbsList.add(new UserFood("Whole Wheat Bread (1 slice)", 70, 3, 12, 1));
        categories.add(new FoodCategory("Carbohydrates", carbsList, false));

        // 3️⃣ دهون صحية
        List<UserFood> fatList = new ArrayList<>();
        fatList.add(new UserFood("Avocado (100g)", 160, 2, 9, 15));
        fatList.add(new UserFood("Almonds (30g)", 173, 6, 6, 15));
        fatList.add(new UserFood("Olive Oil (1 tbsp)", 119, 0, 0, 14));
        fatList.add(new UserFood("Peanut Butter (2 tbsp)", 188, 8, 6, 16));
        fatList.add(new UserFood("Walnuts (30g)", 200, 5, 4, 20));
        fatList.add(new UserFood("Chia Seeds (30g)", 138, 5, 12, 7));
        categories.add(new FoodCategory("Healthy Fats", fatList, false));

        // 4️⃣ خضار
        List<UserFood> vegList = new ArrayList<>();
        vegList.add(new UserFood("Broccoli (100g)", 34, 2.8, 7, 0.4));
        vegList.add(new UserFood("Spinach (100g)", 23, 2.9, 3.6, 0.4));
        vegList.add(new UserFood("Carrot (100g)", 41, 0.9, 10, 0.2));
        vegList.add(new UserFood("Bell Pepper (100g)", 31, 1, 6, 0.3));
        vegList.add(new UserFood("Cucumber (100g)", 16, 0.7, 3.6, 0.1));
        vegList.add(new UserFood("Tomato (100g)", 18, 0.9, 3.9, 0.2));
        categories.add(new FoodCategory("Vegetables", vegList, false));

        // 5️⃣ فواكه
        List<UserFood> fruitList = new ArrayList<>();
        fruitList.add(new UserFood("Apple (1 medium)", 95, 0.5, 25, 0.3));
        fruitList.add(new UserFood("Banana (1 medium)", 105, 1.3, 27, 0.3));
        fruitList.add(new UserFood("Orange (1 medium)", 62, 1.2, 15, 0.2));
        fruitList.add(new UserFood("Strawberries (100g)", 32, 0.7, 7.7, 0.3));
        fruitList.add(new UserFood("Blueberries (100g)", 57, 0.7, 14, 0.3));
        fruitList.add(new UserFood("Grapes (100g)", 69, 0.7, 18, 0.2));
        categories.add(new FoodCategory("Fruits", fruitList, false));

        return categories;
    }

    @Override
    public void onAddFoodClicked(UserFood food) {
        AppDataBase1 db = AppDataBase1.getDatabase(getContext());
        UserFood newFoodEntry = new UserFood(food.getFoodName(), food.getCalories(), food.getProtein(), food.getCarbs(), food.getFat());
        newFoodEntry.setDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date()));
        db.userFoodQuery().insert(newFoodEntry);

        Toast.makeText(getContext(), food.getFoodName() + " added!", Toast.LENGTH_SHORT).show();

        if (dataUpdateListener != null) {
            dataUpdateListener.onDataUpdated();
        }
    }

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
                    Toast.makeText(getContext(), "Food deleted", Toast.LENGTH_SHORT).show();
                    loadData();
                })
                .show();
    }

    @Override
    public void onCategoryHeaderClicked(int position) {
        if (displayItems.get(position) instanceof FoodCategory) {
            FoodCategory category = (FoodCategory) displayItems.get(position);
            category.setExpanded(!category.isExpanded());
            buildDisplayList();
        }
    }
}
