package com.example.sarasva;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Adapter;
import android.widget.LinearLayout;

public class EventList extends AppCompatActivity {

    private RecyclerView recyclerView;

    private int[] images = {R.drawable.tedx,
                            R.drawable.mun,
                            R.drawable.kavi,
                            R.drawable.cognoscentia,
                            R.drawable.litathon,
                            R.drawable.brainbrawl};

    LinearLayoutManager linearLayoutManager;

    private EventAdaptor adaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        recyclerView = findViewById(R.id.recyclerEvents);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        adaptor = new EventAdaptor(images);
        recyclerView.setAdapter(adaptor);
        adaptor.notifyDataSetChanged();

    }
}
