package com.example.finalhamada.data.MyTaskTable;

import android.content.Context;
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

public class MyTaskAdapter extends RecyclerView.Adapter<MyTaskAdapter.MyTaskViewHolder> {

    private final Context context;
    private final List<MyTask> taskList;
    private final OnTaskActionListener listener;
    private final String layoutName; // optional, in case you want to switch layouts

    public interface OnTaskActionListener {
        void onFavoriteClick(MyTask task, int position);
        void onEditClick(MyTask task, int position);
        void onDeleteClick(MyTask task, int position);
        void onCompleteClick(MyTask task, int position);
    }

    public MyTaskAdapter(Context context, List<MyTask> taskList, OnTaskActionListener listener) {
        this(context, taskList, listener, "useritem");
    }

    public MyTaskAdapter(Context context, List<MyTask> taskList, OnTaskActionListener listener, String layoutName) {
        this.context = context;
        this.taskList = taskList;
        this.listener = listener;
        this.layoutName = layoutName;
    }

    @NonNull
    @Override
    public MyTaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutId = context.getResources().getIdentifier(layoutName, "layout", context.getPackageName());
        if (layoutId == 0) {
            layoutId = R.layout.useritems;
        }
        View v = LayoutInflater.from(context).inflate(layoutId, parent, false);
        return new MyTaskViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyTaskViewHolder holder, int position) {
        MyTask task = taskList.get(position);

        // --- عرض النصوص (تأكد أن getters في MyTask مطابقة) ---
        holder.tvTitle.setText(safe(task.getTitle()));
        holder.tvText.setText(safe(task.getDescription()));
        holder.tvImportance.setText(safe(task.getImportance()));

        // --- صورة افتراضية / صورة البروفايل لو عندك ---
        try {
            holder.imgTask.setImageResource(R.drawable.ic_person);
        } catch (Exception e) {
            // اذا ما عندك ic_person لن ينهار التطبيق
            holder.imgTask.setImageDrawable(null);
        }

        // --- زر المفضلة: نستخدم نفس أيقونة القلب ونغيّر alpha بدل drawable غير موجود ---
        try {
            holder.btnFavorite.setImageResource(R.drawable.ic_favorite);
        } catch (Exception e) {
            holder.btnFavorite.setImageDrawable(null);
        }

        applyFavoriteVisual(holder, task.isFavorite());

        // --- listeners ---
        holder.btnFavorite.setOnClickListener(v -> {
            boolean newFav = !task.isFavorite();
            task.setFavorite(newFav);

            applyFavoriteVisual(holder, newFav);

            // لو حاب تحفظ في DB، نفّذ هنا (مثال: myDb.myTaskDao().update(task);)
            if (listener != null) listener.onFavoriteClick(task, position);
        });

        holder.btnEdit.setOnClickListener(v -> {
            if (listener != null) listener.onEditClick(task, position);
        });

        holder.btnDelete.setOnClickListener(v -> {
            if (listener != null) listener.onDeleteClick(task, position);
        });

        holder.btnComplete.setOnClickListener(v -> {
            if (listener != null) listener.onCompleteClick(task, position);
        });

        // لو بدّك تأثير للضغط على الـ Card ككل:
        holder.itemView.setOnClickListener(v -> {
            // optional: تقدر تعيد استخدام onEdit أو onComplete هنا
        });
    }

    private void applyFavoriteVisual(@NonNull MyTaskViewHolder holder, boolean isFav) {
        if (isFav) {
            holder.btnFavorite.setImageAlpha(255);
            holder.btnFavorite.setAlpha(1f);
            // لو بدك لون: holder.btnFavorite.setColorFilter(Color.RED);
        } else {
            holder.btnFavorite.setImageAlpha(120);
            holder.btnFavorite.setAlpha(0.6f);
            // holder.btnFavorite.clearColorFilter();
        }
    }

    private String safe(String s) {
        return s == null ? "" : s;
    }

    @Override
    public int getItemCount() {
        return taskList == null ? 0 : taskList.size();
    }

    // ---------------- ViewHolder ----------------
    static class MyTaskViewHolder extends RecyclerView.ViewHolder {

        ImageView imgTask;
        TextView tvTitle, tvText, tvImportance;
        ImageButton btnComplete, btnFavorite, btnEdit, btnDelete;

        public MyTaskViewHolder(@NonNull View itemView) {
            super(itemView);

            // IDs بالضبط زي اللي في الـ XML اللي وريتنا منهم
            imgTask = itemView.findViewById(R.id.imgVitm);
            tvTitle = itemView.findViewById(R.id.tvItmTitle);
            tvText = itemView.findViewById(R.id.tvItmText);
            tvImportance = itemView.findViewById(R.id.tvItmImportance);

            btnComplete = itemView.findViewById(R.id.imgBtnSendSmitm);
            btnFavorite = itemView.findViewById(R.id.imgBtnFavoriteitm);
            btnEdit = itemView.findViewById(R.id.imgBtnEdititm);
            btnDelete = itemView.findViewById(R.id.imgBtnDeleteitm);
        }
    }
}
