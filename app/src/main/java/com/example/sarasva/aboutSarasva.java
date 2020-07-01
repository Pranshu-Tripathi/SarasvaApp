package com.example.sarasva;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toolbar;

public class aboutSarasva extends AppCompatActivity {

    ImageView imageViewTitle;
    Button backBtn;
    ScrollView scrollView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_sarasva);

        backBtn = (Button) findViewById(R.id.webBtn);
        scrollView = findViewById(R.id.scrollView);
        imageViewTitle = findViewById(R.id.titleImage);

        vanish();
        appear();

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://sarasva.iiita.ac.in"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
    }

    public void vanish(){
        scrollView.setAlpha(0f);
        backBtn.setAlpha(0f);
        imageViewTitle.setAlpha(0f);

    }

    public void appear(){
        scrollView.animate().alpha(1f).setDuration(6000);
        backBtn.animate().alpha(1f).setDuration(5000);
        imageViewTitle.animate().alpha(1f).setDuration(4000);
    }

}
