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
//you will need an adapter for EACH recycler view... and list item xml pages for each recycler view
// as well
public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.FriendViewHolder> {

    private Context context;
    private List<Friend> friendList;

    public FriendAdapter(Context context, List<Friend> friendList) {
        this.context = context;
        this.friendList = friendList;
    }

    @NonNull
    @Override
    public FriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.friend_list_item, parent, false);
        return new FriendViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendViewHolder holder, int position) {
        Friend currentFriend = friendList.get(position);
        holder.freindNameTextView.setText(currentFriend.getFriendName());
        holder.friendUserNameTextView.setText(currentFriend.getFriendUserName());
    }

    @Override
    public int getItemCount() {
        return friendList != null ? friendList.size() : 0;
    }

    public class FriendViewHolder extends RecyclerView.ViewHolder {
        //add more text views here as needed
        TextView freindNameTextView;
        TextView friendUserNameTextView;

        public FriendViewHolder(@NonNull View itemView) {
            super(itemView);
            //add more here for each text view as well
            freindNameTextView = itemView.findViewById(R.id.friend_name_text_view);
            friendUserNameTextView = itemView.findViewById(R.id.friend_user_name_text_view);
        }

    }

    public void updateFriendList(List<Friend> newList) {
        friendList = newList;
        notifyDataSetChanged();
    }
}
