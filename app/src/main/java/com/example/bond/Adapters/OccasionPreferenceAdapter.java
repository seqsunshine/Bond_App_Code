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

public class OccasionPreferenceAdapter extends RecyclerView.Adapter<OccasionPreferenceAdapter.OccasionPreferenceViewHolder> {

    private Context context;
    private List<Preference> preferenceList;

    public OccasionPreferenceAdapter(Context context, List<Preference> preferenceList) {
        this.context = context;
        this.preferenceList = preferenceList;
    }

    @NonNull
    @Override
    public OccasionPreferenceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.occasion_preference_item, parent, false);
        return new OccasionPreferenceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OccasionPreferenceViewHolder holder, int position) {
        Preference currentPreference = preferenceList.get(position);
        holder.preferenceTitleTextView.setText(currentPreference.getName());
        holder.preferenceDescriptionTextView.setText(currentPreference.getDescription());
    }

    @Override
    public int getItemCount() {
        return preferenceList != null ? preferenceList.size() : 0;
    }

    public static class OccasionPreferenceViewHolder extends RecyclerView.ViewHolder {
        TextView preferenceTitleTextView;
        TextView preferenceDescriptionTextView;

        public OccasionPreferenceViewHolder(@NonNull View itemView) {
            super(itemView);
            preferenceTitleTextView = itemView.findViewById(R.id.occasion_preference_title_text_view);
            preferenceDescriptionTextView = itemView.findViewById(R.id.occasion_preference_description_text_view);
        }
    }

    public void updatePreferenceList(List<Preference> newList) {
        this.preferenceList = newList;
        notifyDataSetChanged();
    }
}
