package com.example.finalhamada.data.MyTaskTable; // مكان الكلاس داخل المشروع

import android.view.LayoutInflater; // يستخدم لتحويل ملف XML إلى View حقيقي
import android.view.View; // يمثل عنصر واجهة عام داخل Android
import android.view.ViewGroup; // يمثل الحاوية الأب لعناصر RecyclerView
import android.widget.ImageButton; // زر يحتوي على صورة مثل زر التعديل والحذف
import android.widget.ImageView; // يستخدم لعرض صورة التمرين
import android.widget.TextView; // يستخدم لعرض النصوص مثل اسم التمرين والتفاصيل

import androidx.annotation.NonNull; // تعني أن القيمة لا يجب أن تكون null
import androidx.recyclerview.widget.RecyclerView; // يستخدم لعرض قائمة عناصر بشكل منظم وفعّال

import com.example.finalhamada.R; // للوصول إلى ملفات المشروع مثل layout و id

import java.util.ArrayList; // قائمة قابلة للتعديل
import java.util.List; // نوع عام لتخزين قائمة عناصر

/**
 * ============================================================
 * ExerciseAdapter
 * ============================================================
 *
 * هذا الكلاس هو Adapter خاص بعرض التمارين داخل RecyclerView.
 *
 * وظيفته الأساسية:
 * - أخذ قائمة التمارين من نوع UserExercise.
 * - ربط كل تمرين بتصميم XML خاص به.
 * - عرض اسم التمرين، تفاصيله، وصورته.
 * - التعامل مع زر التعديل.
 * - التعامل مع زر الحذف.
 *
 * أهمية هذا الكلاس:
 * RecyclerView لا يستطيع عرض البيانات وحده.
 * لذلك يحتاج Adapter يكون وسيط بين:
 * - البيانات الموجودة في List
 * - وبين شكل العنصر الموجود في XML
 *
 * استخدمت هذا الكلاس حتى أعرض تمارين المستخدم
 * داخل شاشة التمارين بطريقة مرتبة وقابلة للتعديل والحذف.
 */
public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ViewHolder> {

    /**
     * ============================================================
     * exerciseList
     * ============================================================
     *
     * هذه القائمة تحتوي على جميع التمارين
     * التي سيتم عرضها داخل RecyclerView.
     *
     * كل عنصر داخلها هو UserExercise
     * ويمثل تمرين واحد.
     *
     * استخدمت ArrayList كقيمة ابتدائية
     * حتى لا يحدث Crash إذا كانت القائمة الأصلية null.
     */
    private List<UserExercise> exerciseList = new ArrayList<>();

    /**
     * ============================================================
     * listener
     * ============================================================
     *
     * هذا المتغير يمثل المستمع للأحداث
     * مثل الضغط على زر Edit أو Delete.
     *
     * استخدمته حتى لا يكون الـ Adapter مسؤولًا مباشرة
     * عن فتح شاشة التعديل أو حذف البيانات من قاعدة البيانات.
     *
     * الـ Adapter فقط يخبر الشاشة:
     * "المستخدم ضغط تعديل"
     * أو:
     * "المستخدم ضغط حذف"
     */
    private OnItemClickListener listener;

    /**
     * ============================================================
     * OnItemClickListener
     * ============================================================
     *
     * هذه Interface تستخدم للتواصل
     * بين الـ Adapter والشاشة التي تستخدمه.
     *
     * أهميتها:
     * عندما يضغط المستخدم على زر تعديل أو حذف
     * داخل عنصر من RecyclerView،
     * يتم إرسال التمرين ومكانه إلى الشاشة.
     *
     * بهذا الشكل يبقى الكود منظم:
     * - Adapter مسؤول عن العرض.
     * - Activity أو Fragment مسؤولة عن تنفيذ التعديل أو الحذف.
     */
    public interface OnItemClickListener {

        /**
         * onEditClick
         * ------------------------------------------------------------
         * تعمل عند ضغط المستخدم على زر التعديل.
         *
         * @param exercise التمرين الذي ضغط المستخدم على تعديله.
         * @param position مكان التمرين داخل القائمة.
         */
        void onEditClick(UserExercise exercise, int position);

        /**
         * onDeleteClick
         * ------------------------------------------------------------
         * تعمل عند ضغط المستخدم على زر الحذف.
         *
         * @param exercise التمرين الذي ضغط المستخدم على حذفه.
         * @param position مكان التمرين داخل القائمة.
         */
        void onDeleteClick(UserExercise exercise, int position);
    }

    /**
     * ============================================================
     * Constructor
     * ============================================================
     *
     * هذه الدالة تعمل عند إنشاء ExerciseAdapter.
     *
     * تستقبل:
     * - قائمة التمارين التي سيتم عرضها.
     * - listener للتعامل مع أزرار التعديل والحذف.
     *
     * استخدمته حتى أعطي الـ Adapter البيانات
     * وأعطيه طريقة يرجع فيها الأحداث للشاشة.
     *
     * @param exerciseList قائمة التمارين الأولية.
     * @param listener مستمع لأحداث التعديل والحذف.
     */
    public ExerciseAdapter(List<UserExercise> exerciseList, OnItemClickListener listener) {

        /**
         * إذا كانت القائمة ليست null
         * نستخدمها كقائمة العرض.
         *
         * أما إذا كانت null
         * تبقى القائمة الفارغة الموجودة بالأعلى
         * حتى لا يحدث Crash.
         */
        if (exerciseList != null) this.exerciseList = exerciseList;

        /**
         * حفظ الـ listener
         * حتى نستخدمه عند الضغط على Edit أو Delete.
         */
        this.listener = listener;
    }

    /**
     * ============================================================
     * onCreateViewHolder
     * ============================================================
     *
     * هذه الدالة مسؤولة عن إنشاء شكل العنصر الواحد
     * داخل RecyclerView.
     *
     * تقوم بأخذ ملف XML اسمه exercise_item
     * وتحويله إلى View حقيقي يمكن عرضه على الشاشة.
     *
     * لا تضع البيانات هنا.
     * فقط تنشئ شكل العنصر.
     *
     * @param parent الحاوية الأب الخاصة بـ RecyclerView.
     * @param viewType نوع العنصر إذا كان عندنا أكثر من شكل.
     *
     * @return ViewHolder يحتوي على عناصر الواجهة الخاصة بالتمرين.
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        /**
         * LayoutInflater:
         * يحول ملف XML إلى View.
         *
         * استخدمته لتحويل exercise_item.xml
         * إلى عنصر حقيقي داخل RecyclerView.
         */
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.exercise_item,
                parent,
                false
        );

        /**
         * نرجع ViewHolder جديد
         * يحتوي على هذا الـ View.
         */
        return new ViewHolder(view);
    }

    /**
     * ============================================================
     * onBindViewHolder
     * ============================================================
     *
     * هذه الدالة مسؤولة عن ربط البيانات
     * مع عناصر الواجهة.
     *
     * يعني:
     * تأخذ تمرين من exerciseList
     * وتضع بياناته داخل TextView وImageView.
     *
     * تعمل لكل عنصر يظهر داخل RecyclerView.
     *
     * @param holder يحتوي على عناصر الواجهة الخاصة بالعنصر.
     * @param position مكان العنصر داخل القائمة.
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        /**
         * نحصل على التمرين حسب موقعه داخل القائمة.
         */
        UserExercise exercise = exerciseList.get(position);

        /**
         * عرض اسم التمرين داخل TextView.
         */
        holder.tvName.setText(exercise.getName());

        /**
         * إنشاء نص يحتوي على تفاصيل التمرين:
         * - Category
         * - Reps
         * - Sets
         *
         * الهدف:
         * عرض معلومات التمرين بشكل مختصر وواضح للمستخدم.
         */
        String details = "Category: " + exercise.getCategory() +
                ", Reps: " + exercise.getReps() +
                ", Sets: " + exercise.getSets();

        /**
         * عرض التفاصيل داخل TextView.
         */
        holder.tvDetails.setText(details);

        /**
         * عرض صورة التمرين.
         *
         * imageRes هو رقم Resource
         * يشير إلى صورة موجودة داخل drawable.
         */
        holder.ivImage.setImageResource(exercise.getImageRes());

        /**
         * زر التعديل.
         *
         * عند ضغط المستخدم عليه
         * نرسل التمرين ومكانه إلى الشاشة
         * حتى تقوم الشاشة بفتح التعديل.
         */
        holder.btnEdit.setOnClickListener(v -> {

            /**
             * نتحقق أن listener ليس null
             * حتى لا يحدث Crash.
             */
            if (listener != null) {
                listener.onEditClick(exercise, holder.getAdapterPosition());
            }
        });

        /**
         * زر الحذف.
         *
         * عند ضغط المستخدم عليه
         * نرسل التمرين ومكانه إلى الشاشة
         * حتى تقوم الشاشة بحذفه من القائمة أو قاعدة البيانات.
         */
        holder.btnDelete.setOnClickListener(v -> {

            /**
             * نتحقق أن listener ليس null
             * حتى لا يحدث Crash.
             */
            if (listener != null) {
                listener.onDeleteClick(exercise, holder.getAdapterPosition());
            }
        });
    }

    /**
     * ============================================================
     * getItemCount
     * ============================================================
     *
     * هذه الدالة ترجع عدد العناصر
     * الموجودة داخل RecyclerView.
     *
     * RecyclerView يستخدمها حتى يعرف
     * كم عنصر يجب أن يعرض.
     *
     * @return عدد التمارين داخل القائمة.
     */
    @Override
    public int getItemCount() {
        return exerciseList.size();
    }

    /**
     * ============================================================
     * setExercises
     * ============================================================
     *
     * هذه الدالة تستبدل القائمة الحالية
     * بقائمة جديدة من التمارين.
     *
     * أستخدمها غالبًا عندما:
     * - أحمل البيانات من قاعدة البيانات.
     * - أعمل فلترة للتمارين.
     * - أريد تحديث كل القائمة مرة واحدة.
     *
     * @param exercises القائمة الجديدة للتمارين.
     */
    public void setExercises(List<UserExercise> exercises) {

        /**
         * إذا القائمة الجديدة ليست null
         * نستخدمها.
         *
         * إذا كانت null
         * نضع قائمة فارغة حتى لا يحدث Crash.
         */
        this.exerciseList = exercises != null ? exercises : new ArrayList<>();

        /**
         * notifyDataSetChanged:
         * يخبر RecyclerView أن كل البيانات تغيرت
         * ويجب إعادة رسم القائمة كاملة.
         */
        notifyDataSetChanged();
    }

    /**
     * ============================================================
     * addExercise
     * ============================================================
     *
     * هذه الدالة تضيف تمرين جديد
     * في آخر القائمة.
     *
     * استخدمت notifyItemInserted
     * بدل notifyDataSetChanged
     * لأنه أفضل للأداء
     * ويحدث فقط العنصر الجديد.
     *
     * @param exercise التمرين المراد إضافته.
     */
    public void addExercise(UserExercise exercise) {

        /**
         * إضافة التمرين إلى نهاية القائمة.
         */
        exerciseList.add(exercise);

        /**
         * إخبار RecyclerView أن عنصر جديد
         * تم إضافته في آخر القائمة.
         */
        notifyItemInserted(exerciseList.size() - 1);
    }

    /**
     * ============================================================
     * insertExerciseAt
     * ============================================================
     *
     * هذه الدالة تضيف تمرين
     * في مكان محدد داخل القائمة.
     *
     * مثال:
     * إذا حذف المستخدم تمرين ثم ضغط Undo
     * نستطيع إرجاع التمرين لنفس مكانه.
     *
     * @param exercise التمرين الذي سيتم إدخاله.
     * @param index المكان الذي سيتم إدخال التمرين فيه.
     */
    public void insertExerciseAt(UserExercise exercise, int index) {

        /**
         * إذا كان المكان غير صحيح
         * نجعل الإضافة في آخر القائمة
         * حتى لا يحدث Crash.
         */
        if (index < 0 || index > exerciseList.size()) index = exerciseList.size();

        /**
         * إضافة التمرين في المكان المحدد.
         */
        exerciseList.add(index, exercise);

        /**
         * إخبار RecyclerView أنه تم إدخال عنصر جديد
         * في هذا المكان.
         */
        notifyItemInserted(index);
    }

    /**
     * ============================================================
     * removeAt
     * ============================================================
     *
     * هذه الدالة تحذف تمرين
     * من مكان محدد داخل القائمة.
     *
     * وترجع التمرين المحذوف.
     *
     * لماذا ترجع التمرين؟
     * حتى يمكن استخدامه لاحقًا
     * في ميزة Undo إذا أردنا إرجاع العنصر.
     *
     * @param position مكان التمرين الذي سيتم حذفه.
     *
     * @return التمرين المحذوف أو null إذا كان المكان غير صالح.
     */
    public UserExercise removeAt(int position) {

        /**
         * إذا كان المكان غير صالح
         * نرجع null ونوقف الدالة.
         */
        if (position < 0 || position >= exerciseList.size()) return null;

        /**
         * حذف التمرين من القائمة
         * وحفظه داخل متغير حتى نرجعه.
         */
        UserExercise removed = exerciseList.remove(position);

        /**
         * إخبار RecyclerView أن عنصرًا تم حذفه
         * من هذا المكان.
         */
        notifyItemRemoved(position);

        /**
         * إرجاع التمرين المحذوف.
         */
        return removed;
    }

    /**
     * ============================================================
     * updateExercise
     * ============================================================
     *
     * هذه الدالة تحدث تمرين موجود
     * داخل مكان محدد في القائمة.
     *
     * أستخدمها عندما يقوم المستخدم
     * بتعديل تمرين موجود.
     *
     * @param exercise التمرين بعد التعديل.
     * @param position مكان التمرين داخل القائمة.
     */
    public void updateExercise(UserExercise exercise, int position) {

        /**
         * إذا كان المكان غير صالح
         * نوقف الدالة حتى لا يحدث Crash.
         */
        if (position < 0 || position >= exerciseList.size()) return;

        /**
         * استبدال التمرين القديم
         * بالتمرين الجديد.
         */
        exerciseList.set(position, exercise);

        /**
         * إخبار RecyclerView أن هذا العنصر تغير
         * ويجب تحديثه فقط.
         */
        notifyItemChanged(position);
    }

    /**
     * ============================================================
     * getExerciseAt
     * ============================================================
     *
     * هذه الدالة ترجع التمرين
     * الموجود في مكان معين داخل القائمة.
     *
     * أستخدمها عندما أحتاج معرفة
     * أي تمرين موجود في position معين.
     *
     * @param position مكان التمرين داخل القائمة.
     *
     * @return التمرين المطلوب أو null إذا كان المكان غير صالح.
     */
    public UserExercise getExerciseAt(int position) {

        /**
         * إذا كان المكان غير صحيح
         * نرجع null.
         */
        if (position < 0 || position >= exerciseList.size()) return null;

        /**
         * إرجاع التمرين من القائمة.
         */
        return exerciseList.get(position);
    }

    /**
     * ============================================================
     * ViewHolder
     * ============================================================
     *
     * ViewHolder هو كلاس داخلي
     * يحتفظ بمراجع عناصر الواجهة
     * الخاصة بكل عنصر داخل RecyclerView.
     *
     * أهميته:
     * بدل ما نستخدم findViewById كل مرة
     * عند عرض كل عنصر،
     * ViewHolder يخزن المراجع مرة واحدة
     * وهذا يحسن الأداء.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

        /**
         * ImageView لعرض صورة التمرين.
         */
        ImageView ivImage;

        /**
         * TextViews لعرض:
         * - اسم التمرين.
         * - تفاصيل التمرين.
         */
        TextView tvName, tvDetails;

        /**
         * ImageButtons:
         * - زر تعديل التمرين.
         * - زر حذف التمرين.
         */
        ImageButton btnEdit, btnDelete;

        /**
         * ============================================================
         * ViewHolder Constructor
         * ============================================================
         *
         * يعمل عند إنشاء ViewHolder جديد.
         *
         * وظيفته:
         * ربط عناصر XML مع متغيرات Java
         * حتى نستطيع استخدامها داخل Adapter.
         *
         * @param itemView يمثل شكل العنصر الواحد exercise_item.
         */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            /**
             * ربط صورة التمرين من XML.
             */
            ivImage = itemView.findViewById(R.id.ivExerciseItemImage);

            /**
             * ربط TextView الخاص باسم التمرين.
             */
            tvName = itemView.findViewById(R.id.tvExerciseNameItem);

            /**
             * ربط TextView الخاص بتفاصيل التمرين.
             */
            tvDetails = itemView.findViewById(R.id.tvExerciseDetailsItem);

            /**
             * ربط زر التعديل.
             */
            btnEdit = itemView.findViewById(R.id.imgBtnEditItem);

            /**
             * ربط زر الحذف.
             */
            btnDelete = itemView.findViewById(R.id.imgBtnDeleteItem);
        }
    }
}