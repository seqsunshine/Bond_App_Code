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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserSearchAdapter extends RecyclerView.Adapter<UserSearchAdapter.UserViewHolder> {

    private final Context context;
    private final List<User> userList;
    private final OnUserClickListener clickListener;
    private Set<String> addedUserNames = new HashSet<>();

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

        if (addedUserNames.contains(user.getUserName())) {
            holder.addButton.setText("Added");
            holder.addButton.setEnabled(false);
        } else {
            holder.addButton.setText("Add");
            holder.addButton.setEnabled(true);
        }

        holder.addButton.setOnClickListener(v -> {
            if (clickListener != null) {
                clickListener.onUserClick(user);
            }

            addedUserNames.add(user.getUserName());
            notifyItemChanged(position);
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
