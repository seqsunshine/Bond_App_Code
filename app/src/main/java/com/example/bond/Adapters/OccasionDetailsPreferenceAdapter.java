package com.example.bond.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bond.Models.Preference;
import com.example.bond.R;


import java.util.List;

public class OccasionDetailsPreferenceAdapter extends RecyclerView.Adapter<OccasionDetailsPreferenceAdapter.OccasionDetailsPreferenceViewHolder> {

    Context context;
    private List<Preference> preferenceList;

    public OccasionDetailsPreferenceAdapter(Context context, List<Preference> preferenceList) {
        this.context = context;
        this.preferenceList = preferenceList;
    }

    @NonNull
    @Override
    public OccasionDetailsPreferenceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.occasion_details_preference_item, parent, false);
        return new OccasionDetailsPreferenceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OccasionDetailsPreferenceViewHolder holder, int position) {
        Preference currentPreference = preferenceList.get(position);
        holder.preferenceTitleTextView.setText(currentPreference.getName());
        holder.preferenceDescriptionTextView.setText(currentPreference.getDescription());
    }

    @Override
    public int getItemCount() {
        return preferenceList != null ? preferenceList.size() : 0;
    }

    public static class OccasionDetailsPreferenceViewHolder extends RecyclerView.ViewHolder {
        TextView preferenceTitleTextView;
        TextView preferenceDescriptionTextView;

        public OccasionDetailsPreferenceViewHolder(@NonNull View itemView) {
            super(itemView);
            preferenceTitleTextView = itemView.findViewById(R.id.occasion_details_preference_title_text_view);
            preferenceDescriptionTextView = itemView.findViewById(R.id.occasion_details_preference_description_text_view);
        }
    }
}
