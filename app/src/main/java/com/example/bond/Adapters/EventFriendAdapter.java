package com.example.bond.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bond.Entities.Friend;
import com.example.bond.R;

import java.util.List;

public class EventFriendAdapter extends RecyclerView.Adapter<EventFriendAdapter.EventFriendViewHolder> {

    private Context context;
    private List<Friend> friendList;

    public EventFriendAdapter(Context context, List<Friend> friendList){
        this.context = context;
        this.friendList = friendList;
    }

    @NonNull
    @Override
    public EventFriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.event_friend_item, parent, false);
        return new EventFriendViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventFriendViewHolder holder, int position) {
        Friend currentFriend = friendList.get(position);
        holder.friendNameTextView.setText(currentFriend.getFriendName());
    }

    @Override
    public int getItemCount() {
        return friendList != null ? friendList.size() : 0;
    }

    public static class EventFriendViewHolder extends RecyclerView.ViewHolder {
        TextView friendNameTextView; //add checkbox selection??

        public EventFriendViewHolder(@NonNull View itemView) {
            super(itemView);
            friendNameTextView = itemView.findViewById(R.id.event_friend_name_text_view);
        }
    }

    public void updateFriendList(List<Friend> newList) {
        this.friendList = newList;
        notifyDataSetChanged();
    }
}

