package com.example.bond.Adapters;


import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bond.Entities.Friend;
import com.example.bond.R;

import java.util.List;

public class FriendSearchAdapter extends RecyclerView.Adapter<FriendSearchAdapter.FriendSearchViewHolder> {

    private Context context;
    private List<Friend> friendList;

    public FriendSearchAdapter(Context context, List<Friend> friendList) {
        this.context = context;
        this.friendList = friendList;
    }

    @NonNull
    @Override
    public FriendSearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.friend_search_item, parent, false);
        return new FriendSearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendSearchViewHolder holder, int position) {
        Friend currentFriend = friendList.get(position);
        holder.userNameTextView.setText(currentFriend.getFriendUserName());
    }

    @Override
    public int getItemCount(){
        return friendList != null ? friendList.size() : 0;
    }

    public static class FriendSearchViewHolder extends RecyclerView.ViewHolder {
        TextView userNameTextView;

        public FriendSearchViewHolder(@NonNull View itemView) {
            super(itemView);
            userNameTextView = itemView.findViewById(R.id.search_friend_username_text_view);
        }
    }

    public void updateFriendList(List<Friend> newList) {
        this.friendList = newList;
        notifyDataSetChanged();
    }
}
