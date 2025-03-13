package com.example.bond.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bond.Entities.Friend;
import com.example.bond.Entities.User;
import com.example.bond.R;

import java.util.List;

public class OccasionFriendAdapter extends RecyclerView.Adapter<OccasionFriendAdapter.OccasionFriendViewHolder> {

    private Context context;
    private List<User> friendList;

    public OccasionFriendAdapter(Context context, List<User> friendList){
        this.context = context;
        this.friendList = friendList;
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
    }

    @Override
    public int getItemCount() {
        return friendList != null ? friendList.size() : 0;
    }

    public static class OccasionFriendViewHolder extends RecyclerView.ViewHolder {
        TextView friendNameTextView; //add checkbox selection??

        public OccasionFriendViewHolder(@NonNull View itemView) {
            super(itemView);
            friendNameTextView = itemView.findViewById(R.id.occasion_friend_name_text_view);
        }
    }

    public void updateFriendList(List<User> newList) {
        this.friendList = newList;
        notifyDataSetChanged();
    }
}

