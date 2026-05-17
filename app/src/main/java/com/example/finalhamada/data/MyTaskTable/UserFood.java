package com.example.finalhamada.data.MyTaskTable; // مكان الكلاس داخل المشروع

import android.os.Parcel; // يستخدم لكتابة وقراءة البيانات عند تمرير الكائنات بين Activities
import android.os.Parcelable; // واجهة تسمح بتمرير الكائنات عبر Intent

import androidx.annotation.NonNull; // تعني أن القيمة لا يجب أن تكون null
import androidx.room.Entity; // يحول الكلاس إلى جدول داخل Room Database
import androidx.room.Ignore; // يجعل Room يتجاهل Constructor معين
import androidx.room.PrimaryKey; // يحدد المفتاح الأساسي داخل الجدول

/**
 * ============================================================
 * UserFood
 * ============================================================
 *
 * هذا الكلاس يمثل طعام المستخدم
 * داخل التطبيق.
 *
 * وظيفته الأساسية:
 * - حفظ بيانات الطعام داخل Room Database.
 * - تمرير بيانات الطعام بين Activities.
 *
 * يحتوي على:
 * - اسم الطعام
 * - السعرات الحرارية
 * - البروتين
 * - الكربوهيدرات
 * - الدهون
 * - تاريخ الإضافة
 *
 * أهمية هذا الكلاس:
 * استخدمته كـ Entity
 * حتى يتم تخزين الطعام
 * داخل قاعدة البيانات بشكل منظم.
 *
 * كما استخدمت Parcelable
 * حتى أستطيع إرسال كائن UserFood
 * بين Activities باستخدام Intent.
 */
@Entity(tableName = "user_food_table")
public class UserFood implements Parcelable {

    /**
     * ============================================================
     * id
     * ============================================================
     *
     * هذا المتغير يمثل المعرف الفريد
     * لكل طعام داخل قاعدة البيانات.
     *
     * PrimaryKey:
     * يعني أن هذا الحقل هو المفتاح الأساسي.
     *
     * autoGenerate = true:
     * يعني أن Room يقوم بإنشاء id تلقائيًا
     * لكل عنصر جديد.
     */
    @PrimaryKey(autoGenerate = true)
    private int id;

    /**
     * ============================================================
     * foodName
     * ============================================================
     *
     * يخزن اسم الطعام.
     *
     * أمثلة:
     * - Chicken Breast
     * - Rice
     * - Banana
     */
    private String foodName;

    /**
     * ============================================================
     * calories
     * ============================================================
     *
     * يخزن عدد السعرات الحرارية
     * الخاصة بالطعام.
     */
    private int calories;

    /**
     * ============================================================
     * protein
     * ============================================================
     *
     * يخزن كمية البروتين
     * بالجرام.
     */
    private double protein;

    /**
     * ============================================================
     * carbs
     * ============================================================
     *
     * يخزن كمية الكربوهيدرات
     * بالجرام.
     */
    private double carbs;

    /**
     * ============================================================
     * fat
     * ============================================================
     *
     * يخزن كمية الدهون
     * بالجرام.
     */
    private double fat;

    /**
     * ============================================================
     * date
     * ============================================================
     *
     * يخزن تاريخ إضافة الطعام.
     *
     * استخدمت String
     * بصيغة yyyy-MM-dd
     * لتسهيل الحفظ والعرض.
     */
    private String date;

    /**
     * ============================================================
     * Constructor الكامل
     * ============================================================
     *
     * هذا Constructor يستقبل
     * جميع بيانات الطعام.
     *
     * استخدمته عند:
     * - قراءة البيانات من قاعدة البيانات.
     * - إنشاء كائن كامل يحتوي جميع القيم.
     *
     * @param id المعرف الفريد للطعام
     * @param foodName اسم الطعام
     * @param calories السعرات الحرارية
     * @param protein البروتين
     * @param carbs الكربوهيدرات
     * @param fat الدهون
     * @param date تاريخ الإضافة
     */
    public UserFood(
            int id,
            String foodName,
            int calories,
            double protein,
            double carbs,
            double fat,
            String date
    ) {

        /**
         * حفظ id الطعام.
         */
        this.id = id;

        /**
         * حفظ اسم الطعام.
         */
        this.foodName = foodName;

        /**
         * حفظ السعرات الحرارية.
         */
        this.calories = calories;

        /**
         * حفظ البروتين.
         */
        this.protein = protein;

        /**
         * حفظ الكربوهيدرات.
         */
        this.carbs = carbs;

        /**
         * حفظ الدهون.
         */
        this.fat = fat;

        /**
         * حفظ تاريخ الإضافة.
         */
        this.date = date;
    }

    /**
     * ============================================================
     * Constructor المبسط
     * ============================================================
     *
     * هذا Constructor يستخدم
     * لإنشاء UserFood
     * بدون id وبدون date.
     *
     * @Ignore:
     * يجعل Room يتجاهل هذا Constructor
     * حتى لا يحدث تعارض.
     *
     * استخدمته لتسهيل إنشاء
     * عنصر طعام جديد داخل التطبيق.
     *
     * @param foodName اسم الطعام
     * @param calories السعرات الحرارية
     * @param protein البروتين
     * @param carbs الكربوهيدرات
     * @param fat الدهون
     */
    @Ignore
    public UserFood(
            String foodName,
            int calories,
            double protein,
            double carbs,
            double fat
    ) {

        /**
         * حفظ اسم الطعام.
         */
        this.foodName = foodName;

        /**
         * حفظ السعرات الحرارية.
         */
        this.calories = calories;

        /**
         * حفظ البروتين.
         */
        this.protein = protein;

        /**
         * حفظ الكربوهيدرات.
         */
        this.carbs = carbs;

        /**
         * حفظ الدهون.
         */
        this.fat = fat;
    }

    // ============================================================
    // Getter & Setter
    // ============================================================

    /**
     * ترجع id الطعام.
     */
    public int getId() {
        return id;
    }

    /**
     * تستخدم لتعديل id الطعام.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * ترجع اسم الطعام.
     */
    public String getFoodName() {
        return foodName;
    }

    /**
     * تستخدم لتعديل اسم الطعام.
     */
    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    /**
     * ترجع السعرات الحرارية.
     */
    public int getCalories() {
        return calories;
    }

    /**
     * تستخدم لتعديل السعرات الحرارية.
     */
    public void setCalories(int calories) {
        this.calories = calories;
    }

    /**
     * ترجع كمية البروتين.
     */
    public double getProtein() {
        return protein;
    }

    /**
     * تستخدم لتعديل كمية البروتين.
     */
    public void setProtein(double protein) {
        this.protein = protein;
    }

    /**
     * ترجع كمية الكربوهيدرات.
     */
    public double getCarbs() {
        return carbs;
    }

    /**
     * تستخدم لتعديل كمية الكربوهيدرات.
     */
    public void setCarbs(double carbs) {
        this.carbs = carbs;
    }

    /**
     * ترجع كمية الدهون.
     */
    public double getFat() {
        return fat;
    }

    /**
     * تستخدم لتعديل كمية الدهون.
     */
    public void setFat(double fat) {
        this.fat = fat;
    }

    /**
     * ترجع تاريخ الإضافة.
     */
    public String getDate() {
        return date;
    }

    /**
     * تستخدم لتعديل تاريخ الإضافة.
     */
    public void setDate(String date) {
        this.date = date;
    }

    // ============================================================
    // Parcelable
    // ============================================================

    /**
     * ============================================================
     * UserFood(Parcel in)
     * ============================================================
     *
     * هذا Constructor يستخدم
     * لإنشاء UserFood
     * من Parcel.
     *
     * Parcel يحتوي البيانات
     * التي تم إرسالها بين Activities.
     *
     * استخدمته مع Parcelable
     * لتمرير الكائن بين الشاشات.
     *
     * @param in يحتوي البيانات القادمة
     */
    protected UserFood(Parcel in) {

        /**
         * قراءة id من Parcel.
         */
        id = in.readInt();

        /**
         * قراءة اسم الطعام.
         */
        foodName = in.readString();

        /**
         * قراءة السعرات الحرارية.
         */
        calories = in.readInt();

        /**
         * قراءة البروتين.
         */
        protein = in.readDouble();

        /**
         * قراءة الكربوهيدرات.
         */
        carbs = in.readDouble();

        /**
         * قراءة الدهون.
         */
        fat = in.readDouble();

        /**
         * قراءة التاريخ.
         */
        date = in.readString();
    }

    /**
     * ============================================================
     * writeToParcel
     * ============================================================
     *
     * هذه الدالة تكتب بيانات الكائن
     * داخل Parcel.
     *
     * استخدمتها حتى يمكن إرسال
     * UserFood عبر Intent.
     *
     * @param dest الوجهة التي سيتم الكتابة إليها
     * @param flags خيارات إضافية
     */
    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {

        /**
         * كتابة id.
         */
        dest.writeInt(id);

        /**
         * كتابة اسم الطعام.
         */
        dest.writeString(foodName);

        /**
         * كتابة السعرات الحرارية.
         */
        dest.writeInt(calories);

        /**
         * كتابة البروتين.
         */
        dest.writeDouble(protein);

        /**
         * كتابة الكربوهيدرات.
         */
        dest.writeDouble(carbs);

        /**
         * كتابة الدهون.
         */
        dest.writeDouble(fat);

        /**
         * كتابة التاريخ.
         */
        dest.writeString(date);
    }

    /**
     * ============================================================
     * describeContents
     * ============================================================
     *
     * دالة خاصة بـ Parcelable.
     *
     * غالبًا ترجع 0
     * إذا لم توجد أنواع خاصة من الملفات.
     *
     * @return 0
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * ============================================================
     * CREATOR
     * ============================================================
     *
     * يستخدم لإنشاء كائن UserFood
     * من Parcel.
     *
     * ضروري لأي كلاس
     * يطبق Parcelable.
     */
    public static final Creator<UserFood> CREATOR =
            new Creator<UserFood>() {

                /**
                 * إنشاء UserFood من Parcel.
                 */
                @Override
                public UserFood createFromParcel(Parcel in) {
                    return new UserFood(in);
                }

                /**
                 * إنشاء Array من UserFood.
                 */
                @Override
                public UserFood[] newArray(int size) {
                    return new UserFood[size];
                }
            };
}