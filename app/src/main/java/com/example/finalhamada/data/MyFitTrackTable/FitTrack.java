package com.example.finalhamada.data.MyFitTrackTable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * فئة لتخزين بيانات تتبّع النشاط الرياضي
 */
@Entity
public class FitTrack {

    /** رقم النشاط (المفتاح الأساسي) */
    @PrimaryKey(autoGenerate = true)
    public long id;

    /** رقم المستخدم المرتبط بالنشاط */
    public long userId;

    /** التاريخ */
    public String date;

    /** عدد الخطوات */
    public int steps;

    /** السعرات الحرارية المحروقة */
    public double calories;

    /** المسافة المقطوعة */
    public double distance;

    /** مدّة النشاط */
    public long duration;

    /** نوع النشاط (جري، مشي، ركض...) */
    public String activityType;

    /** ملاحظات إضافية */
    public String note;


    // Getters and Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "FitTrack{" +
                "id=" + id +
                ", userId=" + userId +
                ", date='" + date + '\'' +
                ", steps=" + steps +
                ", calories=" + calories +
                ", distance=" + distance +
                ", duration=" + duration +
                ", activityType='" + activityType + '\'' +
                ", note='" + note + '\'' +
                '}';
    }
}
