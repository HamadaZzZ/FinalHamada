package com.example.finalhamada.data.MyUserTable;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalhamada.R;

import java.util.List;

public class MyUserAdapter extends RecyclerView.Adapter<MyUserAdapter.MyUserViewHolder> {

    private Context context;
    private List<MyUser> userList;
    private OnUserClickListener listener;

    public MyUserAdapter(Context context, List<MyUser> userList, OnUserClickListener listener) {
        this.context = context;
        this.userList = userList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.useritems, parent, false);
        return new MyUserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyUserViewHolder holder, int position) {
        MyUser user = userList.get(position);
        holder.tvName.setText(user.getFullName());
        holder.tvEmail.setText(user.getEmail());
        holder.tvPhone.setText(user.getPhone());

        // عند الضغط على أي عنصر
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onUserClick(user);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class MyUserViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvEmail, tvPhone;

        public MyUserViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            tvPhone = itemView.findViewById(R.id.tvPhone);
        }
    }

    // واجهة للتعامل مع الضغط على أي مستخدم
    public interface OnUserClickListener {
        void onUserClick(MyUser user);
    }
}
