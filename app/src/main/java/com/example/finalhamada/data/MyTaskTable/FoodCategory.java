package com.example.finalhamada.data.MyTaskTable;

import java.util.List;

public class FoodCategory {
    private String title;
    private List<UserFood> foodItems;
    private boolean isExpanded;

    public FoodCategory(String title, List<UserFood> foodItems, boolean isExpanded) {
        this.title = title;
        this.foodItems = foodItems;
        this.isExpanded = isExpanded;
    }

    public String getTitle() { return title; }
    public List<UserFood> getFoodItems() { return foodItems; }
    public boolean isExpanded() { return isExpanded; }
    public void setExpanded(boolean expanded) { isExpanded = expanded; }
}
