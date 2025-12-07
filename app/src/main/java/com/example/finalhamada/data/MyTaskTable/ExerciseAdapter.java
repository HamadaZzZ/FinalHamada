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

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ViewHolder> {

    private List<UserExercise> exerciseList;
    private OnItemClickListener listener;

    // واجهة للتعامل مع ضغط الأزرار
    public interface OnItemClickListener {
        void onEditClick(int position);
        void onDeleteClick(int position);
    }

    public ExerciseAdapter(List<UserExercise> exerciseList, OnItemClickListener listener) {
        this.exerciseList = exerciseList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserExercise exercise = exerciseList.get(position);

        // عرض البيانات
        holder.tvName.setText(exercise.getName());
        String details = "Category: " + exercise.getCategory() +
                ", Reps: " + exercise.getReps() +
                ", Sets: " + exercise.getSets();
        holder.tvDetails.setText(details);
        holder.ivImage.setImageResource(exercise.getImageRes());

        // التعامل مع أزرار التعديل والحذف
        holder.btnEdit.setOnClickListener(v -> listener.onEditClick(position));
        holder.btnDelete.setOnClickListener(v -> listener.onDeleteClick(position));
    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
    }

    // ====== طريقة لتحديث قائمة التمارين ======
    public void setExercises(List<UserExercise> exercises) {
        this.exerciseList = exercises;
        notifyDataSetChanged();
    }

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
