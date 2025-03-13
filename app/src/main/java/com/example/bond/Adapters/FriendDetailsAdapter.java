package com.example.bond.Adapters;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bond.Models.Detail;
import com.example.bond.R;

import java.util.List;

public class FriendDetailsAdapter extends RecyclerView.Adapter<FriendDetailsAdapter.DetailViewHolder> {

    private Context context;
    private List<Detail> detailList;

    public FriendDetailsAdapter(Context context, List<Detail> detailList) {
        this.context = context;
        this.detailList = detailList;
    }

    @NonNull
    @Override
    public DetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.friend_detail_item, parent, false);
        return new DetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailViewHolder holder, int position) {
        Detail currentDetail = detailList.get(position);
        holder.nameTextView.setText(currentDetail.getName());
        holder.descriptionTextView.setText(currentDetail.getDescription());
    }

    @Override
    public int getItemCount() {
        return detailList .size();
    }


    public static class DetailViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView descriptionTextView;

        public DetailViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.friend_detail_name_text_view);
            descriptionTextView = itemView.findViewById(R.id.friend_detail_description_text_view);
        }
    }

    public void updateDetailsList(List<Detail> newList) {
        this.detailList = newList;
        notifyDataSetChanged();
    }
}
