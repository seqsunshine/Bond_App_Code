package com.example.bond.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bond.Models.Preference;
import com.example.bond.R;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OccasionPreferenceAdapter extends RecyclerView.Adapter<OccasionPreferenceAdapter.OccasionPreferenceViewHolder> {

    private Context context;
    private List<Preference> preferenceList;
    private OnPreferenceAddListener onPreferenceAddListener;
    private Set<String> addedPreferenceNames = new HashSet<>();

    public interface OnPreferenceAddListener {
        void onPreferenceAdd(Preference preference);
    }

    public OccasionPreferenceAdapter(Context context, List<Preference> preferenceList, OnPreferenceAddListener onPreferenceAddListener) {
        this.context = context;
        this.preferenceList = preferenceList;
        this.onPreferenceAddListener = onPreferenceAddListener;
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

        if (addedPreferenceNames.contains(currentPreference.getName())) {
            holder.addButton.setText("Added");
            holder.addButton.setEnabled(false);
        } else {
            holder.addButton.setText("Add");
            holder.addButton.setEnabled(true);
        }

        holder.addButton.setOnClickListener(v -> {
            if (onPreferenceAddListener != null) {
                onPreferenceAddListener.onPreferenceAdd(currentPreference);
            }

            addedPreferenceNames.add(currentPreference.getName());
            notifyItemChanged(position);
        });

    }

    @Override
    public int getItemCount() {
        return preferenceList != null ? preferenceList.size() : 0;
    }

    public static class OccasionPreferenceViewHolder extends RecyclerView.ViewHolder {
        TextView preferenceTitleTextView;
        Button addButton;

        public OccasionPreferenceViewHolder(@NonNull View itemView) {
            super(itemView);
            preferenceTitleTextView = itemView.findViewById(R.id.occasion_preference_name_text_view);
            addButton = itemView.findViewById(R.id.occasion_add_preference_button);
        }
    }

    public void updatePreferenceList(List<Preference> newList) {
        this.preferenceList = newList;
        notifyDataSetChanged();
    }
}
