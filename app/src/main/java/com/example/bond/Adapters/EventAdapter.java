package com.example.bond.Adapters;

import android.content.Context;
import android.media.metrics.Event;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bond.Entities.Occasion;
import com.example.bond.R;

import org.w3c.dom.Text;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    private Context context;
    private List<Occasion> eventList;

    public EventAdapter(Context context, List<Occasion> eventList) {
        this.context = context;
        this.eventList = eventList;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.event_item, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Occasion currentEvent = eventList.get(position);
        holder.titleTextView.setText(currentEvent.getOccasionTitle());
        holder.dateTextView.setText(currentEvent.getOccasionDate());
        holder.descriptionTextView.setText(currentEvent.getDescription());
    }

    @Override
    public int getItemCount() {
        return eventList != null ? eventList.size() : 0;
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView dateTextView;
        TextView descriptionTextView;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.event_title_text_view);
            dateTextView = itemView.findViewById(R.id.event_date_text_view);
            descriptionTextView = itemView.findViewById(R.id.event_description_text_view);
        }
    }

    public void updateEventList(List<Occasion> newList) {
        this.eventList = newList;
        notifyDataSetChanged();
    }
}
