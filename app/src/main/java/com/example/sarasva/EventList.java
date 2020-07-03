package com.example.sarasva;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

public class EventList extends AppCompatActivity {

    private Button tedX, mun,kavi,cognoscentia,litathon,brainbrawl,feathers;

    private int[] images = {R.drawable.tedx,
                            R.drawable.mun,
                            R.drawable.kavi,
                            R.drawable.cognoscentia,
                            R.drawable.litathon,
                            R.drawable.brainbrawl};




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        CarouselView carouselView = findViewById(R.id.cv);
        carouselView.setPageCount(images.length);
        carouselView.setImageListener(imageListener);

        setupButtons();

        tedX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EventList.this,TedxActivity.class);
                startActivity(intent);
            }
        });

    }

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(images[position]);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        }
    };

    public void setupButtons()
    {
        tedX = findViewById(R.id.btntedX);
        mun = findViewById(R.id.btnmun);
        kavi = findViewById(R.id.btnkavi);
        cognoscentia = findViewById(R.id.btncognosentia);
        litathon = findViewById(R.id.btnlitathon);
        brainbrawl = findViewById(R.id.btnbrainbrawl);
        feathers = findViewById(R.id.btnfeathers);
    }
}
