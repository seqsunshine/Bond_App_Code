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
import com.example.bond.Entities.User;
import com.example.bond.R;

import java.util.List;
//you will need an adapter for EACH recycler view... and list item xml pages for each recycler view
// as well
public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.ViewHolder> {

    private Context context;
    private List<User> userList;

    public FriendAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public FriendAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.friend_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendAdapter.ViewHolder holder, int position) {
        User user = userList.get(position);
        holder.friendNameTextView.setText(user.getName());
        holder.friendUserNameTextView.setText(user.getUserName());
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
       TextView friendNameTextView;
       TextView friendUserNameTextView;
       public ViewHolder(@NonNull View itemView) {
           super(itemView);
           friendNameTextView = itemView.findViewById(R.id.friend_name_text_view);
           friendUserNameTextView = itemView.findViewById(R.id.friend_user_name_text_view);
       }
    }
}
