package com.example.sarasva;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class TedxActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private EventPagerAdaptor eventPagerAdaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tedx);


        // setting up tab layout
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        eventPagerAdaptor = new EventPagerAdaptor(getSupportFragmentManager());
        eventPagerAdaptor.AddFragment(new AboutFragment(),"About");
        eventPagerAdaptor.AddFragment(new GalleryFragment(),"Gallery");
        eventPagerAdaptor.AddFragment(new RegisterFragment(),"Register");
        viewPager.setAdapter(eventPagerAdaptor);
        tabLayout.setupWithViewPager(viewPager);

        // now setting up the elements in it
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_info_outline_black_24dp);
        tabLayout.getTabAt(0).setText("About");
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_browser);
        tabLayout.getTabAt(1).setText("Gallery");
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_cash_register);
        tabLayout.getTabAt(2).setText("Register");

    }
}
