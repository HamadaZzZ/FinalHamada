package com.example.finalhamada.data.MyTaskTable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class MyTask {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;
    private String description;
    private String importance;
    private boolean isFavorite;
    private String createdAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImportance() {
        return importance;
    }

    public void setImportance(String importance) {
        this.importance = importance;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "MyTask{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", importance='" + importance + '\'' +
                ", isFavorite=" + isFavorite +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }
}
