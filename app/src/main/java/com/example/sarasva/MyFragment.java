package com.example.sarasva;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyFragment# newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyFragment extends Fragment {


    private RecyclerView myRecycler;
    private ArrayList<BlogItem> blogItems;
    private BlogAdaptor blogAdaptor;
    private DatabaseReference reference;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private String title,imgUrl,UserUrl,description,id,views;


//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;

    public MyFragment() {
        // Required empty public constructor
    }

//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment MyFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static MyFragment newInstance(String param1, String param2) {
//        MyFragment fragment = new MyFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        myRecycler = view.findViewById(R.id.myRecycler);
        blogItems = new ArrayList<>();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        myRecycler.setLayoutManager(new LinearLayoutManager(getContext()));


        if(firebaseUser != null){
            reference = FirebaseDatabase.getInstance().getReference()
                    .child("Sarasva")
                    .child("Users")
                    .child("Profile")
                    .child(firebaseUser.getUid())
                    .child("Blogs")
                    .child("My");

            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                        if(dataSnapshot1.exists()){
                            title = dataSnapshot1.child("title").getValue().toString().trim();
                            id = dataSnapshot1.child("id").getValue().toString().trim();
                            views = dataSnapshot1.child("views").getValue().toString().trim();
                            imgUrl = dataSnapshot1.child("imgBlog").getValue().toString().trim();
                            description = dataSnapshot1.child("description").getValue().toString().trim();
                            UserUrl = dataSnapshot1.child("imgUser").getValue().toString().trim();

                            Log.i("title",title);
                            Log.i("id",id);
                            Log.i("views",views);
                            Log.i("des",description);
                            Log.i("imgBlog",imgUrl);
                            Log.i("imgUser", UserUrl);
                            BlogItem b = new BlogItem(id,title,description,imgUrl,UserUrl,views);
                            try {
                                blogItems.add(b);
                            }catch (Exception e){
                                Log.i("Error",e.getMessage());
                            }
                        }
                    }

                    blogAdaptor = new BlogAdaptor(getContext(),blogItems);
                    myRecycler.setAdapter(blogAdaptor);
                    blogAdaptor.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }else{
            Toast.makeText(getContext(), "No User", Toast.LENGTH_SHORT).show();
        }



    }
}