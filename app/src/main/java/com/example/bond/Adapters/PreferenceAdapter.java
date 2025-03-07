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

public class PreferenceAdapter extends RecyclerView.Adapter<PreferenceAdapter.PreferenceViewHolder> {

    private Context context;
    private List<Preference> preferenceList;
    private OnPreferenceClickListener listener;

    public PreferenceAdapter(Context context, List<Preference> preferenceList, OnPreferenceClickListener listener) {
        this.context = context;
        this.preferenceList = preferenceList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PreferenceViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.preference_item, parent, false);
        return new PreferenceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PreferenceViewHolder holder, int position) {
        Preference currentPreference = preferenceList.get(position);
        holder.preferenceNameTextView.setText(currentPreference.getName());
        holder.preferenceDescriptionTextView.setText(currentPreference.getDescription());

        //set click listener on the item view
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION && listener != null) {
                    listener.onPreferenceClick(pos, preferenceList.get(pos));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return preferenceList != null ? preferenceList.size() : 0;

    }

    public static class PreferenceViewHolder extends RecyclerView.ViewHolder {
        TextView preferenceNameTextView;
        TextView preferenceDescriptionTextView;

        public PreferenceViewHolder(@NonNull View itemView) {
            super(itemView);
            preferenceNameTextView = itemView.findViewById(R.id.preference_name_text_view);
            preferenceDescriptionTextView = itemView.findViewById(R.id.preference_description_text_view);
        }
    }

    public void updatePreferenceList(List<Preference> newList) {
        this.preferenceList = newList;
        notifyDataSetChanged();
    }

    public interface OnPreferenceClickListener {
        void onPreferenceClick(int position, Preference preference);
    }
}
