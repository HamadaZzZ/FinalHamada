package com.example.finalhamada.data.MyTaskTable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalhamada.R;

import java.util.ArrayList;
import java.util.List;

/**
 * ExerciseAdapter
 * ----------------------------------------------
 * Adapter لعرض قائمة تمارين المستخدم في RecyclerView.
 *
 * التوثيق داخل الكود يشرح كل دالة وسلوكها:
 * - يدعم إضافة عنصر واحد، حذف عنصر، تحديث عنصر، وإرجاع عنصر حسب الموضع.
 * - يستدعي مستمع (listener) مع كائن UserExercise وposition بدل position فقط.
 *
 * ملاحظة: لا يغيّر هيكل UserExercise أو DAO. هذا Adapter فقط يحسّن التحديثات البصرية
 * باستخدام notifyItemInserted/Removed/Changed بدلاً من notifyDataSetChanged() الكلي.
 */
public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ViewHolder> {

    /** قائمة التمارين المعروضة */
    private List<UserExercise> exerciseList = new ArrayList<>();

    /** مستمع لأحداث التعديل والحذف */
    private OnItemClickListener listener;

    /**
     * واجهة المستمع
     * AR: تُستخدم لتمرير حدث التعديل أو الحذف مع الكائن والموقع.
     */
    public interface OnItemClickListener {
        void onEditClick(UserExercise exercise, int position);
        void onDeleteClick(UserExercise exercise, int position);
    }

    /**
     * Constructor
     * @param exerciseList قائمة التمارين الأولية (يمكن أن تكون null)
     * @param listener مستمع للأحداث (Edit/Delete)
     */
    public ExerciseAdapter(List<UserExercise> exerciseList, OnItemClickListener listener) {
        if (exerciseList != null) this.exerciseList = exerciseList;
        this.listener = listener;
    }

    /**
     * onCreateViewHolder
     * AR: ينشئ ViewHolder جديد عن طريق نفخ layout الخاص بعنصر التمرين.
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_item, parent, false);
        return new ViewHolder(view);
    }

    /**
     * onBindViewHolder
     * AR: يربط بيانات UserExercise بعناصر الواجهة داخل كل ViewHolder.
     * - يعرض الاسم، التفاصيل، والصورة.
     * - يربط أزرار Edit/Delete بالمستمع مع تمرير الكائن والموقع الحالي.
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserExercise exercise = exerciseList.get(position);

        holder.tvName.setText(exercise.getName());
        String details = "Category: " + exercise.getCategory() +
                ", Reps: " + exercise.getReps() +
                ", Sets: " + exercise.getSets();
        holder.tvDetails.setText(details);

        // عرض صورة التمرين (Resource ID)
        holder.ivImage.setImageResource(exercise.getImageRes());

        // أزرار التعديل والحذف تمرر الكائن والموقع للمستمع
        holder.btnEdit.setOnClickListener(v -> {
            if (listener != null) listener.onEditClick(exercise, holder.getAdapterPosition());
        });
        holder.btnDelete.setOnClickListener(v -> {
            if (listener != null) listener.onDeleteClick(exercise, holder.getAdapterPosition());
        });
    }

    /**
     * getItemCount
     * AR: عدد العناصر المعروضة.
     */
    @Override
    public int getItemCount() {
        return exerciseList.size();
    }

    /**
     * setExercises
     * AR: يستبدل القائمة الحالية بقائمة جديدة ويحدّث العرض بالكامل.
     * ملاحظة: استخدم هذا عند تحميل بيانات جديدة من DB أو عند فلترة.
     */
    public void setExercises(List<UserExercise> exercises) {
        this.exerciseList = exercises != null ? exercises : new ArrayList<>();
        notifyDataSetChanged();
    }

    /**
     * addExercise
     * AR: يضيف تمرينًا في نهاية القائمة ويستخدم notifyItemInserted لتحسين الأداء البصري.
     * @param exercise التمرين المراد إضافته
     */
    public void addExercise(UserExercise exercise) {
        exerciseList.add(exercise);
        notifyItemInserted(exerciseList.size() - 1);
    }

    /**
     * insertExerciseAt
     * AR: يدرج تمرينًا في موضع محدد (مثلاً لإرجاع عنصر بعد Undo).
     * @param exercise التمرين
     * @param index الموضع المراد الإدراج عنده
     */
    public void insertExerciseAt(UserExercise exercise, int index) {
        if (index < 0 || index > exerciseList.size()) index = exerciseList.size();
        exerciseList.add(index, exercise);
        notifyItemInserted(index);
    }

    /**
     * removeAt
     * AR: يزيل التمرين عند الموضع المحدد ويعيد الكائن المحذوف (للاستخدام مع Undo).
     * @param position الموضع
     * @return التمرين المحذوف أو null إذا كان الموضع غير صالح
     */
    public UserExercise removeAt(int position) {
        if (position < 0 || position >= exerciseList.size()) return null;
        UserExercise removed = exerciseList.remove(position);
        notifyItemRemoved(position);
        return removed;
    }

    /**
     * updateExercise
     * AR: يحدث بيانات التمرين في الموضع المحدد ويستخدم notifyItemChanged لعرض التحديث.
     * @param exercise التمرين المحدث
     * @param position الموضع الذي سيتم التحديث فيه
     */
    public void updateExercise(UserExercise exercise, int position) {
        if (position < 0 || position >= exerciseList.size()) return;
        exerciseList.set(position, exercise);
        notifyItemChanged(position);
    }

    /**
     * getExerciseAt
     * AR: يعيد التمرين عند الموضع المحدد أو null إذا كان الموضع غير صالح.
     */
    public UserExercise getExerciseAt(int position) {
        if (position < 0 || position >= exerciseList.size()) return null;
        return exerciseList.get(position);
    }

    /**
     * ViewHolder
     * AR: يحوي مراجع لعناصر الواجهة داخل كل عنصر في RecyclerView.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImage;
        TextView tvName, tvDetails;
        ImageButton btnEdit, btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.ivExerciseItemImage);
            tvName = itemView.findViewById(R.id.tvExerciseNameItem);
            tvDetails = itemView.findViewById(R.id.tvExerciseDetailsItem);
            btnEdit = itemView.findViewById(R.id.imgBtnEditItem);
            btnDelete = itemView.findViewById(R.id.imgBtnDeleteItem);
        }
    }
}
