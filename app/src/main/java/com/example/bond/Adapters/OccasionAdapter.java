package com.example.bond.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bond.Entities.Occasion;
import com.example.bond.R;

import java.util.List;

public class OccasionAdapter extends RecyclerView.Adapter<OccasionAdapter.OccasionViewHolder> {

    private Context context;
    private List<Occasion> occasionList;
    private OnOccasionClickListener clickListener;

    public interface OnOccasionClickListener {
        void onOccasionClick(Occasion occasion);
    }

    public OccasionAdapter(Context context, List<Occasion> occasionList, OnOccasionClickListener clickListener) {
        this.context = context;
        this.occasionList = occasionList;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public OccasionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.occasion_item, parent, false);
        return new OccasionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OccasionViewHolder holder, int position) {
        Occasion currentOccasion = occasionList.get(position);
        holder.titleTextView.setText(currentOccasion.getOccasionTitle());
        holder.dateTextView.setText(currentOccasion.getOccasionDate());

        holder.itemView.setOnClickListener(v -> {
            if (clickListener != null) {
                clickListener.onOccasionClick(currentOccasion);
            }
        });
    }

    @Override
    public int getItemCount() {
        return occasionList != null ? occasionList.size() : 0;
    }

    public static class OccasionViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView dateTextView;

        public OccasionViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.occasion_title_text_view);
            dateTextView = itemView.findViewById(R.id.occasion_date_text_view);
        }
    }

    public void updateOccasionList(List<Occasion> newList) {
        this.occasionList = newList;
        notifyDataSetChanged();
    }
}
