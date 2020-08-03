package com.example.sarasva;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class TeamActivity extends AppCompatActivity {

    private ExpandableHeightGridView gridViewCoordinators,gridViewMembers,gridViewSeniors;
    private DatabaseReference cordiReference;
    private DatabaseReference memberReference;
    private DatabaseReference seniorReference;
    private List<String> cordi_names;
    private List<String> cordi_imgUrl;
    private List<String> cordi_enroll;
    private Dialog dialog;
    private List<String> members_names;
    private List<String> members_imgUrl;
    private List<String> members_enroll;

    private List<String> senior_members_names;
    private List<String> senior_members_imgUrl;
    private List<String> senior_members_enroll;



    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);

        gridViewCoordinators = (ExpandableHeightGridView) findViewById(R.id.coordinatorGridView);
        gridViewMembers = (ExpandableHeightGridView) findViewById(R.id.membersGridView);
        gridViewSeniors = (ExpandableHeightGridView) findViewById(R.id.membersGridViewSenior);

        progressBar = (ProgressBar) findViewById(R.id.progress);

        cordi_names = new ArrayList<>();
        cordi_imgUrl = new ArrayList<>();
        cordi_enroll = new ArrayList<>();


        members_names = new ArrayList<>();
        members_imgUrl = new ArrayList<>();
        members_enroll = new ArrayList<>();

        senior_members_names = new ArrayList<>();
        senior_members_imgUrl = new ArrayList<>();
        senior_members_enroll = new ArrayList<>();

        dialog = new Dialog(this);

        // retrieving coordinators data
        cordiReference = FirebaseDatabase.getInstance().getReference()
                .child("Sarasva")
                .child("Team")
                .child("Cordi");
        cordiReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                cordi_names.clear();
                cordi_imgUrl.clear();
                cordi_enroll.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    if(dataSnapshot1.exists())
                    {
                        cordi_names.add(dataSnapshot1.child("Name").getValue().toString().trim());
                        cordi_enroll.add(dataSnapshot1.child("enroll").getValue().toString().trim());
                        cordi_imgUrl.add(dataSnapshot1.child("imgUrl").getValue().toString().trim());
                    }
                }
                CordiTeam cordiTeam = new CordiTeam();
                gridViewCoordinators.setAdapter(cordiTeam);
                cordiTeam.notifyDataSetChanged();
                gridViewCoordinators.setExpanded(true);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(TeamActivity.this, "Something Went Wrong :(", Toast.LENGTH_SHORT).show();
            }
        });

        // on click cordi
        gridViewCoordinators.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ImageView imageView_pop;
                TextView close_pop,name_pop,enroll_pop,status_pop,email_pop;
                dialog.setContentView(R.layout.pop_up_layout);
                close_pop = dialog.findViewById(R.id.close_text);
                name_pop = dialog.findViewById(R.id.nameTextPopUp);
                enroll_pop = dialog.findViewById(R.id.enrollTextPopUp);
                status_pop = dialog.findViewById(R.id.StatusTextPopUp);
                email_pop = dialog.findViewById(R.id.mailTextPopUp);
                imageView_pop = dialog.findViewById(R.id.imageProfilePopUp);

                Picasso.with(getApplicationContext())
                        .load(cordi_imgUrl.get(position))
                        .placeholder(R.drawable.profileimg)
                        .into(imageView_pop);



                name_pop.setText(cordi_names.get(position));
                enroll_pop.setText(cordi_enroll.get(position));
                status_pop.setText("Coordinator");
                email_pop.setText(cordi_enroll.get(position).toLowerCase().trim()+"@iiita.ac.in");

                close_pop.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });


        // retrieving Seniors Data
        seniorReference = FirebaseDatabase.getInstance().getReference()
                .child("Sarasva")
                .child("Team")
                .child("Senior");
        seniorReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                senior_members_names.clear();
                senior_members_enroll.clear();
                senior_members_imgUrl.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    if(dataSnapshot1.exists())
                    {
                        senior_members_names.add(dataSnapshot1.child("Name").getValue().toString().trim());
                        senior_members_enroll.add(dataSnapshot1.child("enroll").getValue().toString().trim());
                        senior_members_imgUrl.add(dataSnapshot1.child("imgUrl").getValue().toString().trim());
                    }
                }

                SeniorMembers seniorMembers = new SeniorMembers();
                gridViewSeniors.setAdapter(seniorMembers);
                seniorMembers.notifyDataSetChanged();
                gridViewSeniors.setExpanded(true);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(TeamActivity.this, "Something Went Wrong :(", Toast.LENGTH_SHORT).show();
            }
        });

        // on click senior members
        gridViewSeniors.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ImageView imageView_pop;
                TextView close_pop,name_pop,enroll_pop,status_pop,email_pop;
                dialog.setContentView(R.layout.pop_up_layout);
                close_pop = dialog.findViewById(R.id.close_text);
                name_pop = dialog.findViewById(R.id.nameTextPopUp);
                enroll_pop = dialog.findViewById(R.id.enrollTextPopUp);
                status_pop = dialog.findViewById(R.id.StatusTextPopUp);
                email_pop = dialog.findViewById(R.id.mailTextPopUp);
                imageView_pop = dialog.findViewById(R.id.imageProfilePopUp);

                Picasso.with(getApplicationContext())
                        .load(senior_members_imgUrl.get(position))
                        .placeholder(R.drawable.profileimg)
                        .into(imageView_pop);



                name_pop.setText(senior_members_names.get(position));
                enroll_pop.setText(senior_members_enroll.get(position));
                status_pop.setText("Senior Member");
                email_pop.setText(senior_members_enroll.get(position).toLowerCase().trim()+"@iiita.ac.in");

                close_pop.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        // retrieving Members Data;
        memberReference = FirebaseDatabase.getInstance().getReference()
                .child("Sarasva")
                .child("Team")
                .child("Members");

        memberReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                members_names.clear();
                members_imgUrl.clear();
                members_enroll.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    if(dataSnapshot1.exists())
                    {
                        members_names.add(dataSnapshot1.child("Name").getValue().toString().trim());
                        members_enroll.add(dataSnapshot1.child("enroll").getValue().toString().trim());
                        members_imgUrl.add(dataSnapshot1.child("imgUrl").getValue().toString().trim());
                    }
                }

                MemberTeam memberTeam = new MemberTeam();
                gridViewMembers.setAdapter(memberTeam);
                memberTeam.notifyDataSetChanged();
                gridViewMembers.setExpanded(true);
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
                Toast.makeText(TeamActivity.this, "Something Went Wrong :(", Toast.LENGTH_SHORT).show();
            }
        });

        // on click members

        gridViewMembers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ImageView imageView_pop;
                TextView close_pop,name_pop,enroll_pop,status_pop,email_pop;
                dialog.setContentView(R.layout.pop_up_layout);
                close_pop = dialog.findViewById(R.id.close_text);
                name_pop = dialog.findViewById(R.id.nameTextPopUp);
                enroll_pop = dialog.findViewById(R.id.enrollTextPopUp);
                status_pop = dialog.findViewById(R.id.StatusTextPopUp);
                email_pop = dialog.findViewById(R.id.mailTextPopUp);
                imageView_pop = dialog.findViewById(R.id.imageProfilePopUp);

                Picasso.with(getApplicationContext())
                        .load(members_imgUrl.get(position))
                        .placeholder(R.drawable.profileimg)
                        .into(imageView_pop);



                name_pop.setText(members_names.get(position));
                enroll_pop.setText(members_enroll.get(position));
                status_pop.setText("Member");
                email_pop.setText(members_enroll.get(position).toLowerCase().trim()+"@iiita.ac.in");

                close_pop.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

    }

    private class CordiTeam extends BaseAdapter{
        @Override
        public int getCount() {
            return cordi_names.size();
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
            View v = getLayoutInflater().inflate(R.layout.row_data_team,null);
            CircleImageView circleImageView = v.findViewById(R.id.profileImage);
            TextView textView = v.findViewById(R.id.NameMember);
            textView.setText(cordi_names.get(position));
            Picasso.with(TeamActivity.this).load(cordi_imgUrl.get(position)).placeholder(R.drawable.profileimg).into(circleImageView);
            return v;
        }
    }

    private class SeniorMembers extends BaseAdapter{
        @Override
        public int getCount() {
            return senior_members_names.size();
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
            View v = getLayoutInflater().inflate(R.layout.row_data_team,null);
            CircleImageView circleImageView = v.findViewById(R.id.profileImage);
            TextView textView = v.findViewById(R.id.NameMember);
            textView.setText(senior_members_names.get(position));
            Picasso.with(TeamActivity.this).load(senior_members_imgUrl.get(position)).placeholder(R.drawable.profileimg).into(circleImageView);
            return v;
        }
    }

    private class MemberTeam extends BaseAdapter{
        @Override
        public int getCount() {
            return members_names.size();
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
            View v = getLayoutInflater().inflate(R.layout.row_data_team,null);
            CircleImageView circleImageView = v.findViewById(R.id.profileImage);
            TextView textView = v.findViewById(R.id.NameMember);
            textView.setText(members_names.get(position));
            Picasso.with(TeamActivity.this).load(members_imgUrl.get(position)).placeholder(R.drawable.profileimg).into(circleImageView);
            return v;
        }
    }
}
