package com.example.sarasva;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
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
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFragment extends Fragment {

    private ImageView imageView;
    private ExpandableListView expandableListView;
    private DatabaseReference reference;
    private String url;
    private ProgressBar progressBar;
    private List<String> listDataHeader;
    private HashMap<String,List<String>> listHashMap;

    public AboutFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_about, container, false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        expandableListView = view.findViewById(R.id.exp_lst_about);
        progressBar = view.findViewById(R.id.progress_bar_about);
        ExpandableListViewAdaptor viewAdaptor = new ExpandableListViewAdaptor(getContext(),listDataHeader,listHashMap);
        expandableListView.setAdapter(viewAdaptor);
        imageView = view.findViewById(R.id.eventImageOnAbout);
        reference = FirebaseDatabase.getInstance().getReference()
                .child("Sarasva")
                .child("Events")
                .child("TedX")
                .child("About")
                .child("Images");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    if(dataSnapshot1.exists())
                    {
                        url = dataSnapshot1.getValue(String.class);
                    }
                    else
                    {
                        Toast.makeText(getContext(), "Issue in Image", Toast.LENGTH_SHORT).show();
                    }
                }

                Picasso.with(getContext()).load(url).placeholder(R.drawable.sarasvalogowhite).into(imageView);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void init()
    {
        listDataHeader = new ArrayList<>();
        listHashMap = new HashMap<>();

        listDataHeader.add("What is Ted?");
        listDataHeader.add("How Is TedX different ?");
        listDataHeader.add("Goal Of TedX IIITA");
        listDataHeader.add("Past Speakers : ");
        listDataHeader.add("What is Aparoksha?");

        List<String> ans1 = new ArrayList<>();
        ans1.add("TED Conferences LLC (Technology, Entertainment, Design) is an American media organization that posts talks online for free distribution under the slogan \"ideas worth spreading\". TED was conceived by Richard Saul Wurman in February 1984 as a conference; it has been held annually since 1990. The main TED conference is held annually in Vancouver, British Columbia, Canada at the Vancouver Convention Centre. Prior to 2014, the conference was held in Long Beach, California, United States.TED events are also held throughout North America and in Europe, Asia, and Africa, offering live streaming of the talks");

        List<String> ans2 = new ArrayList<>();
        ans2.add("The difference between TED and TEDx events are that the former takes more of a global approach while the latter typically focuses on a local community that concentrates on local voices. “Officially, the ‘x’ in TEDx stands for independently organized TED event – but it’s more of a TED multiplied.");

        List<String> ans3 = new ArrayList<>();
        ans3.add("TEDxIIITA builds a TED like experience at Indian Institute of Information Technology, Allahabad in order to engage the students, leading professors and researchers, area leaders and visionaries in thoughtful discussions. Our goal is to bring together bright minds to give talks that are idea focused, and on a wide range of subjects, to foster learning, inspiration and wonder, and provoke conversations that matter");

        List<String> ans4 = new ArrayList<>();
        ans4.add("Yogesh Sasini");
        ans4.add("Mallakhamb Artist India");
        ans4.add("Lt. Col. Pankajashan K");
        ans4.add("Neeraj Narayanan");
        ans4.add("Yugm");

        List<String> ans5 = new ArrayList<>();
        ans5.add("Aparoksha is the Annual Technical Festival of IIIT Allahabad (A Centre of Excellence in Information Technology established by Ministry of HRD, Govt. of India).\n" +
                "\n" +
                "A collaboration of, for and by tech enthusiasts, Aparoksha is a platform for technocrats to code, design and build innovative solutions to transform India into a digitally empowered society and a knowledge based economy, all while providing a venue for self-expression and creativity.");


        listHashMap.put(listDataHeader.get(0),ans1);
        listHashMap.put(listDataHeader.get(1),ans2);
        listHashMap.put(listDataHeader.get(2),ans3);
        listHashMap.put(listDataHeader.get(3),ans4);
        listHashMap.put(listDataHeader.get(4),ans5);
    }
}
