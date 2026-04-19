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

        foodAdapter.notifyDataSetChanged();
    }

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

    private List<FoodCategory> getExploreFoodCategories() {

        List<FoodCategory> categories = new ArrayList<>();

        // Protein
        List<UserFood> proteinList = new ArrayList<>();

        proteinList.add(new UserFood("Chicken Breast",165,31,0,3.6));
        proteinList.add(new UserFood("Egg",78,6,1,5));
        proteinList.add(new UserFood("Tuna",132,28,0,1));
        proteinList.add(new UserFood("Greek Yogurt",100,10,4,0));

        categories.add(new FoodCategory("Protein", proteinList, true));


        // Carbs
        List<UserFood> carbsList = new ArrayList<>();

        carbsList.add(new UserFood("Rice",206,4,45,0));
        carbsList.add(new UserFood("Potato",161,4,37,0));
        carbsList.add(new UserFood("Oats",389,17,66,7));

        categories.add(new FoodCategory("Carbs", carbsList, false));


        // Fats
        List<UserFood> fatsList = new ArrayList<>();

        fatsList.add(new UserFood("Avocado",160,2,9,15));
        fatsList.add(new UserFood("Olive Oil",119,0,0,14));
        fatsList.add(new UserFood("Almonds",164,6,6,14));

        categories.add(new FoodCategory("Fats", fatsList, false));


        // Fruits
        List<UserFood> fruitsList = new ArrayList<>();

        fruitsList.add(new UserFood("Banana",105,1,27,0));
        fruitsList.add(new UserFood("Apple",95,0,25,0));
        fruitsList.add(new UserFood("Orange",62,1,15,0));

        categories.add(new FoodCategory("Fruits", fruitsList, false));


        // Vegetables
        List<UserFood> vegetablesList = new ArrayList<>();

        vegetablesList.add(new UserFood("Broccoli",55,4,11,0));
        vegetablesList.add(new UserFood("Carrot",41,1,10,0));
        vegetablesList.add(new UserFood("Spinach",23,3,4,0));

        categories.add(new FoodCategory("Vegetables", vegetablesList, false));


        return categories;
    }

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