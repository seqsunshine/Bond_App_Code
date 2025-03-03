package com.example.bond.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bond.Models.FriendPreference;
import com.example.bond.R;

import java.util.List;

public class FriendPreferenceAdapter extends RecyclerView.Adapter<FriendPreferenceAdapter.FriendPreferenceViewHolder> {

    private Context context;
    private List<FriendPreference> friendPreferenceList;

    public FriendPreferenceAdapter(Context context, List<FriendPreference> friendPreferenceList) {
        this.context = context;
        this.friendPreferenceList = friendPreferenceList;
    }

    @NonNull
    @Override
    public FriendPreferenceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.friend_preference_item, parent, false);
        return new FriendPreferenceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendPreferenceViewHolder holder, int position) {
        FriendPreference currentPreference = friendPreferenceList.get(position);

        holder.preferenceNameTextView.setText(currentPreference.getName());
        holder.preferenceDescriptionTextView.setText(currentPreference.getDescription());
    }

    @Override
    public int getItemCount() {
        return friendPreferenceList != null ? friendPreferenceList.size() : 0;
    }

    public static class FriendPreferenceViewHolder extends RecyclerView.ViewHolder {

        TextView preferenceNameTextView;
        TextView preferenceDescriptionTextView;

        public FriendPreferenceViewHolder(@NonNull View itemView) {
            super(itemView);
            preferenceNameTextView = itemView.findViewById(R.id.friend_preference_name_text_view);
            preferenceDescriptionTextView = itemView.findViewById(R.id.friend_preference_description_text_view);
        }
    }

    public void updateFriendPreferenceList(List<FriendPreference> newList) {
        this.friendPreferenceList = newList;
        notifyDataSetChanged();
    }

}
