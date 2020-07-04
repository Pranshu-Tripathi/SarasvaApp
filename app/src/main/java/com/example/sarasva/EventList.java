package com.example.sarasva;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

public class EventList extends AppCompatActivity {



    private int[] images = {R.drawable.tedx,
                            R.drawable.mun,
                            R.drawable.kavi,
                            R.drawable.cognoscentia,
                            R.drawable.litathon,
                            R.drawable.brainbrawl};
    private int[] icons = {R.drawable.tedxicon,
            R.drawable.municon,
            R.drawable.feathersicon,
            R.drawable.openmicicon,
            R.drawable.cognoscentiaicon,
            R.drawable.brainbrawlicon,
            R.drawable.damsharadsicon,
            R.drawable.pictionaryicon};
    private String[] names = {"TedX", "MUN", "Feather","Open Mic","Cognoscentia","Brain Brawl","Dum-charades","Pictionary"};

    private GridView gridView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        CarouselView carouselView = findViewById(R.id.cv);
        carouselView.setPageCount(images.length);
        carouselView.setImageListener(imageListener);

        gridView = findViewById(R.id.gridview);
        CustomAdaptor customAdaptor = new CustomAdaptor();
        gridView.setAdapter(customAdaptor);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(icons[position] == R.drawable.tedxicon){
                    tedx();
                }
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


    private class CustomAdaptor extends BaseAdapter {
        @Override
        public int getCount() {
            return icons.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = getLayoutInflater().inflate(R.layout.row_data,null);

            TextView name = view.findViewById(R.id.eventName);
            ImageView imageView = view.findViewById(R.id.imagesIconEvent);

            name.setText(names[position]);
            imageView.setImageResource(icons[position]);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return view;
        }
    }

    public void tedx()
    {
        Intent intent = new Intent(EventList.this,TedxActivity.class);
        startActivity(intent);
    }
}
