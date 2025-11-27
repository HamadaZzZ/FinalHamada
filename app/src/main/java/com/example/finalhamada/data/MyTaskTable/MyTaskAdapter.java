package com.example.finalhamada.data.MyTaskTable;

import android.content.Context;
import android.net.Uri;
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

public class MyTaskAdapter extends RecyclerView.Adapter<MyTaskAdapter.TaskViewHolder> {

    private final Context context;
    private final List<MyTask> taskList;
    private final OnTaskClickListener listener;

    public interface OnTaskClickListener {
        void onSendClick(MyTask task, int position);
        void onCallClick(MyTask task, int position);
        void onEditClick(MyTask task, int position);
        void onDeleteClick(MyTask task, int position);
        void onItemClick(MyTask task, int position); // لو بدك تضغط على الكارد نفسه
    }

    public MyTaskAdapter(Context context, List<MyTask> taskList, OnTaskClickListener listener) {
        this.context = context;
        this.taskList = taskList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.task_item, parent, false);
        return new TaskViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        MyTask task = taskList.get(position);

        // ربط النصوص — أنا استخدمت نفس الحقول كما بالـ adapter السابق:
        // title <- fullName , text <- email , importance <- phone
        holder.tvTitle.setText(task.getFullName() != null ? task.getFullName() : "No name");
        holder.tvText.setText(task.getEmail() != null ? task.getEmail() : "");
        holder.tvImportance.setText(task.getPhone() != null ? task.getPhone() : "");

        // تحميل الصورة: عندك في MyTask حقل profileImage (String)
        // لو هو مسار URI أو URL استخدم Glide/Picasso. لو مش موجود استعمل صورة افتراضية.
        // مثال بدون مكتبة خارجية:
        String pic = task.getProfileImage();
        if (pic != null && !pic.trim().isEmpty()) {
            try {
                // جرّب تحميل من URI المحلي
                holder.imgVitm.setImageURI(Uri.parse(pic));
            } catch (Exception e) {
                // فشل التحميل من URI -> استعمل placeholder
                holder.imgVitm.setImageResource(R.drawable.ic_person);
            }

            // **ملاحظة**: لو تبي تحميل من URL خارجي ركّب Glide وأستخدم:
            // Glide.with(context).load(pic).placeholder(R.drawable.ic_person).into(holder.imgVitm);
        } else {
            holder.imgVitm.setImageResource(R.drawable.ic_person);
        }

        // أزرار الإجراء
        holder.btnSend.setOnClickListener(v -> listener.onSendClick(task, position));
        holder.btnCall.setOnClickListener(v -> listener.onCallClick(task, position));
        holder.btnEdit.setOnClickListener(v -> listener.onEditClick(task, position));
        holder.btnDelete.setOnClickListener(v -> listener.onDeleteClick(task, position));

        // ضغط على العنصر نفسه
        holder.itemView.setOnClickListener(v -> listener.onItemClick(task, position));
    }

    @Override
    public int getItemCount() {
        return taskList != null ? taskList.size() : 0;
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder {
        ImageView imgVitm;
        TextView tvTitle, tvText, tvImportance;
        ImageButton btnSend, btnCall, btnEdit, btnDelete;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            imgVitm = itemView.findViewById(R.id.imgVitm);
            tvTitle = itemView.findViewById(R.id.tvItmTitle);
            tvText = itemView.findViewById(R.id.tvItmText);
            tvImportance = itemView.findViewById(R.id.tvItmImportance);

            btnSend = itemView.findViewById(R.id.imgBtnSendSmitm);
            btnCall = itemView.findViewById(R.id.imgBtnFavoriteitm);
            btnEdit = itemView.findViewById(R.id.imgBtnEdititm);
            btnDelete = itemView.findViewById(R.id.imgBtnDeleteitm);
        }
    }
}
