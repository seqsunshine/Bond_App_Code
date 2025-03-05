package com.example.bond.UI;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bond.Adapters.OccasionAdapter;
import com.example.bond.Entities.Occasion;
import com.example.bond.R;

import java.util.ArrayList;
import java.util.List;

public class OccasionPage extends AppCompatActivity {

    private RecyclerView occasionsRecycler;
    private OccasionAdapter occasionAdapter;
    private List<Occasion> occasionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_occasion_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.occasion_page), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //set up backwards navigation
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        //initialize recycler
        occasionsRecycler = findViewById(R.id.my_occasion_recycler);
        occasionsRecycler.setLayoutManager(new LinearLayoutManager(this));

        //initialize occasion list
        occasionList = new ArrayList<>();
        //need to add logic to import occasion data from database NOT DONE

        //initialize adapter
        occasionAdapter = new OccasionAdapter(this, occasionList);
        occasionsRecycler.setAdapter(occasionAdapter);
    }
}