package com.example.sarasva;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class GalleryFragment extends Fragment {


    private ProgressBar progressBar;
    private DatabaseReference reference;
    private GridView gridView;
    public static GalleryAdaptor ga;
    public static ArrayList<String> urls;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_gallery, container, false);

        urls = new ArrayList<>();
        progressBar = v.findViewById(R.id.pbaar);
        gridView = v.findViewById(R.id.gridView);
        reference = FirebaseDatabase.getInstance().getReference()
                .child("Sarasva")
                .child("Events")
                .child("TedX")
                .child("Gallery");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    if(dataSnapshot1.exists()){
                        urls.add(dataSnapshot1.getValue(String.class));
                        Log.i("Data URL", Objects.requireNonNull(dataSnapshot1.getValue()).toString());
                    }
                }
                ga = new GalleryAdaptor(getContext(),urls);
                gridView.setAdapter(ga);
                ga.notifyDataSetChanged();
                // 5 Seconds Delay to the Progress Bar
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                },5000);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "Error in Downloading URLS!!!", Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }


}
