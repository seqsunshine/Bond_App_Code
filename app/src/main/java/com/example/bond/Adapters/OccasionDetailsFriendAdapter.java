package com.example.bond.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bond.R;

import java.util.List;

public class OccasionDetailsFriendAdapter extends RecyclerView.Adapter<OccasionDetailsFriendAdapter.OccasionDetailsFriendViewHolder> {

    private Context context;
    private List<String> friendList;

    public OccasionDetailsFriendAdapter(Context context, List<String> friendList) {
        this.context = context;
        this.friendList = friendList;
    }

    @NonNull
    @Override
    public OccasionDetailsFriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.occasion_details_friend_item, parent, false);
        return new OccasionDetailsFriendViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OccasionDetailsFriendViewHolder holder, int position) {
        String friendName = friendList.get(position);
        holder.friendNameTextView.setText(friendName);
    }

    @Override
    public int getItemCount(){
        return friendList != null ? friendList.size() : 0;
    }

    public static class OccasionDetailsFriendViewHolder extends RecyclerView.ViewHolder {
        TextView friendNameTextView;

        public OccasionDetailsFriendViewHolder(@NonNull View itemView) {
            super(itemView);
            friendNameTextView = itemView.findViewById(R.id.occasion_details_friend_name_text_view);
        }
    }

    public void updateFriendList(List<String> newList) {
        this.friendList = newList;
        notifyDataSetChanged();
    }
}
