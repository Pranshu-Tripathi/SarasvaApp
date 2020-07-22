package com.example.sarasva;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private Button Events;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Sarasva IIITA");

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        navigationView = findViewById(R.id.nav);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(MainActivity.this);

        Events = findViewById(R.id.eventBtn);
        Events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,EventList.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void aboutSarasva(View view){
        Intent intent = new Intent(MainActivity.this,aboutSarasva.class);
        startActivity(intent);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.home:
                Toast.makeText(this, "Home Selected", Toast.LENGTH_SHORT).show();
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            case R.id.event:
                Toast.makeText(this, "Event Selected", Toast.LENGTH_SHORT).show();
                drawerLayout.closeDrawer(GravityCompat.START);
                Intent intent2 = new Intent(MainActivity.this,EventList.class);
                startActivity(intent2);
                return true;
            case R.id.writeups:
                Toast.makeText(this, "Blog Selected", Toast.LENGTH_SHORT).show();
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            case R.id.cordi:
                Toast.makeText(this, "Coordinators Selected", Toast.LENGTH_SHORT).show();
                drawerLayout.closeDrawer(GravityCompat.START);
                Intent intent3 = new Intent(MainActivity.this,CoordinatorsActivity.class);
                startActivity(intent3);
                return true;
            case R.id.members:
                Toast.makeText(this, "Members Selected", Toast.LENGTH_SHORT).show();
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            case R.id.mail:
                Toast.makeText(this, "Mail Selected", Toast.LENGTH_SHORT).show();
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            case R.id.instagram:
                Toast.makeText(this, "Instagram Selected", Toast.LENGTH_SHORT).show();
                Uri uri = Uri.parse("http://instagram.com/_u/sarasva_iiita");
                Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

                likeIng.setPackage("com.instagram.android");

                try {
                    startActivity(likeIng);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://instagram.com/_u/sarasva_iiita")));
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            case R.id.facebook:
                Toast.makeText(this, "Facebook Selected", Toast.LENGTH_SHORT).show();
                try {
                    MainActivity.this.getPackageManager()
                            .getPackageInfo("com.facebook.katana", 0); //Checks if FB is even installed.
                     startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("fb://profile/420206951351045"))); //Trys to make intent with FB's URI
                } catch (Exception e) {
                     startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://www.facebook.com/sarasva.litclub"))); //catches and opens a url to the desired page
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            case R.id.tweeter:
                Toast.makeText(this, "Twitter Selected", Toast.LENGTH_SHORT).show();
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("twitter://user?screen_name=sarasva_IIITA"));
                    startActivity(intent);
                } catch (Exception e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://twitter.com/#!/sarasva_IIITA")));
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            case R.id.share:
                Toast.makeText(this, "Share Selected", Toast.LENGTH_SHORT).show();
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            case R.id.rating:
                Toast.makeText(this, "Rating Selected", Toast.LENGTH_SHORT).show();
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            default:
                drawerLayout.closeDrawer(GravityCompat.START);
                return false;
        }

    }
}
//