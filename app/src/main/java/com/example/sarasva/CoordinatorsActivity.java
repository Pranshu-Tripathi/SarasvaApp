package com.example.sarasva;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CoordinatorsActivity extends AppCompatActivity {

    private ExpandableListView expandableListView;
    private DatabaseReference reference;
    private CordinatorsAdapter cordinatorsAdapter;
    private ProgressBar progressBar;
    private List<String> names;
    private List<String> status;
    private List<String> imgURLF;
    private List<Coordinators> coordinatorsList;
    private HashMap<String,Coordinators> insideDetails;
    private int GroupTracker = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinators);


        names = new ArrayList<>();
        status = new ArrayList<>();
        imgURLF = new ArrayList<>();
        coordinatorsList = new ArrayList<Coordinators>();
        insideDetails = new HashMap<>();

        expandableListView = findViewById(R.id.exp_lst_cordi);
        progressBar = findViewById(R.id.loading_cordi);

        reference = FirebaseDatabase.getInstance().getReference()
                .child("Sarasva")
                .child("Cordi");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                names.clear();
                status.clear();
                coordinatorsList.clear();
                imgURLF.clear();
                insideDetails.clear();
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    if(dataSnapshot1.exists())
                    {
                        names.add(dataSnapshot1.child("name").getValue().toString().trim());
                        status.add(dataSnapshot1.child("status").getValue().toString().trim());
                        Coordinators coordinators = new Coordinators();
                        coordinators.setContact(dataSnapshot1.child("contact").getValue().toString());
                        coordinators.setImgUrl(dataSnapshot1.child("imgUrl").getValue().toString());
                        coordinators.setEnroll(dataSnapshot1.child("enroll").getValue().toString());
                        coordinatorsList.add(coordinators);
                        imgURLF.add(coordinators.getImgUrl());
                    }
                }

                for (int position = 0 ; position < names.size() ; position++)
                {
                    insideDetails.put(names.get(position),coordinatorsList.get(position));
                }

                cordinatorsAdapter = new CordinatorsAdapter(CoordinatorsActivity.this,names,imgURLF,status,insideDetails);
                expandableListView.setAdapter(cordinatorsAdapter);
                cordinatorsAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.INVISIBLE);
            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(CoordinatorsActivity.this, "Issue in Loading", Toast.LENGTH_SHORT).show();
            }
        });

        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                if(GroupTracker != -1 && groupPosition != GroupTracker){
                    expandableListView.collapseGroup(GroupTracker);
                }
                GroupTracker = groupPosition;
            }
        });
    }
}
