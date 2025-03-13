package com.example.bond.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bond.Entities.User;
import com.example.bond.R;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OccasionFriendAdapter extends RecyclerView.Adapter<OccasionFriendAdapter.OccasionFriendViewHolder> {

    private Context context;
    private List<User> friendList;
    private OnFriendAddListener onFriendAddListener;

    private Set<Integer> addedUserIDs = new HashSet<>();

    public interface OnFriendAddListener {
        void onFriendAdd(User user);
    }

    public OccasionFriendAdapter(Context context, List<User> friendList, OnFriendAddListener onFriendAddListener) {
        this.context = context;
        this.friendList = friendList;
        this.onFriendAddListener = onFriendAddListener;
    }

    @NonNull
    @Override
    public OccasionFriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.occasion_friend_item, parent, false);
        return new OccasionFriendViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OccasionFriendViewHolder holder, int position) {
        User currentFriend = friendList.get(position);
        holder.friendNameTextView.setText(currentFriend.getUserName());

        if (addedUserIDs.contains(currentFriend.getUserID())) {
            holder.addButton.setText("Added");
            holder.addButton.setEnabled(false);
        } else {
            holder.addButton.setText("Add");
            holder.addButton.setEnabled(true);
        }

        holder.addButton.setOnClickListener(v -> {
            if (onFriendAddListener != null) {
                onFriendAddListener.onFriendAdd(currentFriend);
            }

            addedUserIDs.add(currentFriend.getUserID());
            notifyItemChanged(position);
        });

    }

    @Override
    public int getItemCount() {
        return friendList != null ? friendList.size() : 0;
    }

    public static class OccasionFriendViewHolder extends RecyclerView.ViewHolder {
        TextView friendNameTextView;
        Button addButton;

        public OccasionFriendViewHolder(@NonNull View itemView) {
            super(itemView);
            friendNameTextView = itemView.findViewById(R.id.occasion_friend_name_text_view);
            addButton = itemView.findViewById(R.id.occasion_add_friend_button);
        }
    }

    public void updateFriendList(List<User> newList) {
        this.friendList = newList;
        notifyDataSetChanged();
    }
}

