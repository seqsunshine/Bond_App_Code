package com.example.bond.UI;

import android.media.metrics.Event;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bond.Adapters.EventAdapter;
import com.example.bond.Entities.Occasion;
import com.example.bond.R;

import java.util.ArrayList;
import java.util.List;

public class EventPage extends AppCompatActivity {

    private RecyclerView eventsRecycler;
    private EventAdapter eventAdapter;
    private List<Occasion> eventList;
    //this needs to be fiddled with. in some locations it is titled Occasion, in some Event.. needs
    //to be consistent!!! just pick one and alter everything
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_event_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.event_page), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //initialize recycler
        eventsRecycler = findViewById(R.id.my_events_recycler);
        eventsRecycler.setLayoutManager(new LinearLayoutManager(this));

        //initialize event list
        eventList = new ArrayList<>();
        //need to add logic to import event data from database NOT DONE

        //initialize adapter
        eventAdapter = new EventAdapter(this, eventList);
        eventsRecycler.setAdapter(eventAdapter);
    }
}