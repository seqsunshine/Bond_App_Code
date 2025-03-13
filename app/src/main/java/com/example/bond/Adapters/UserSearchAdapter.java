package com.example.bond.Adapters;


import static java.lang.reflect.Array.get;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bond.Entities.Friend;
import com.example.bond.Entities.User;
import com.example.bond.R;

import java.util.List;

public class UserSearchAdapter extends RecyclerView.Adapter<UserSearchAdapter.UserViewHolder> {

    private final Context context;
    private final List<User> userList;
    private final OnUserClickListener clickListener;

    public interface OnUserClickListener {
        void onUserClick(User user);
    }

    public UserSearchAdapter(Context context, List<User> userList, OnUserClickListener clickListener){
        this.context = context;
        this.userList = userList;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_search_item, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userList.get(position);
        holder.userNameTextView.setText(user.getUserName());
        holder.addButton.setOnClickListener(v -> {
            if (clickListener != null) {
                clickListener.onUserClick(user);
                Toast.makeText(context, "Friend added", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView userNameTextView;
        Button addButton;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            userNameTextView = itemView.findViewById(R.id.search_friend_username_text_view);
            addButton = itemView.findViewById(R.id.add_friend_button);
        }
    }
}
