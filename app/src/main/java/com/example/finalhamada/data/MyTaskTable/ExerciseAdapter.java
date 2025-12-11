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

import java.util.List;

/**
 * ExerciseAdapter:
 * ----------------------------------------------
 * Adapter لشاشة التمارين (Exercises)
 * مسؤولة عن:
 * - عرض قائمة التمارين في RecyclerView
 * - ربط بيانات UserExercise بكل عنصر في الواجهة
 * - التعامل مع أزرار التعديل والحذف لكل تمرين
 */
public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ViewHolder> {

    /** قائمة التمارين */
    private List<UserExercise> exerciseList;

    /** مستمع لأزرار التعديل والحذف */
    private OnItemClickListener listener;

    /**
     * واجهة للتعامل مع ضغط أزرار التعديل والحذف
     */
    public interface OnItemClickListener {
        void onEditClick(int position);
        void onDeleteClick(int position);
    }

    /** Constructor */
    public ExerciseAdapter(List<UserExercise> exerciseList, OnItemClickListener listener) {
        this.exerciseList = exerciseList;
        this.listener = listener;
    }

    /** إنشاء عنصر جديد في RecyclerView */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_item, parent, false);
        return new ViewHolder(view);
    }

    /** ربط البيانات بعنصر RecyclerView */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserExercise exercise = exerciseList.get(position);

        // عرض اسم التمرين والتفاصيل
        holder.tvName.setText(exercise.getName());
        String details = "Category: " + exercise.getCategory() +
                ", Reps: " + exercise.getReps() +
                ", Sets: " + exercise.getSets();
        holder.tvDetails.setText(details);

        // عرض صورة التمرين
        holder.ivImage.setImageResource(exercise.getImageRes());

        // التعامل مع أزرار التعديل والحذف
        holder.btnEdit.setOnClickListener(v -> listener.onEditClick(position));
        holder.btnDelete.setOnClickListener(v -> listener.onDeleteClick(position));
    }

    /** عدد العناصر في القائمة */
    @Override
    public int getItemCount() {
        return exerciseList.size();
    }

    /** تحديث قائمة التمارين */
    public void setExercises(List<UserExercise> exercises) {
        this.exerciseList = exercises;
        notifyDataSetChanged();
    }

    /**
     * ViewHolder:
     * ----------------------------------------------
     * يحتوي على كل العناصر الخاصة بكل عنصر في RecyclerView
     * - الصورة
     * - اسم التمرين
     * - التفاصيل
     * - أزرار التعديل والحذف
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
