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

public class FoodsActivtiy extends AppCompatActivity implements FoodListFragment.OnDataUpdateListener {

    private ViewPager2 viewPager;
    private FoodPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foods_activtiy);

        setSupportActionBar(findViewById(R.id.toolbar));
        viewPager = findViewById(R.id.viewPager);
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        FloatingActionButton fab = findViewById(R.id.fab_add_food);

        adapter = new FoodPagerAdapter(this);
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            tab.setText(position == 0 ? "Today's Log" : "Explore");
        }).attach();

        fab.setOnClickListener(v -> startActivity(new Intent(this, AddFoods.class)));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adapter != null) onDataUpdated();
    }

    @Override
    public void onDataUpdated() {
        // عند الرجوع من إضافة طعام أو إضافة من Explore
        viewPager.setCurrentItem(0, true); // الانتقال لتاب Today's Log
    }

    private static class FoodPagerAdapter extends FragmentStateAdapter {
        public FoodPagerAdapter(@NonNull AppCompatActivity activity) { super(activity); }
        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return position == 0 ? FoodListFragment.newInstance(FoodListFragment.TYPE_USER_LOG)
                    : FoodListFragment.newInstance(FoodListFragment.TYPE_EXPLORE);
        }
        @Override public int getItemCount() { return 2; }
    }
}
