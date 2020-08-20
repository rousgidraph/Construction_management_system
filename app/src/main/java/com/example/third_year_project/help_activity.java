package com.example.third_year_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class help_activity extends AppCompatActivity {
    TextView title,construction_txt;
    ImageView construction_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_activity);

        construction_txt = findViewById(R.id.text_construction);
        construction_img = findViewById(R.id.image_construction);
        construction_img.setVisibility(View.VISIBLE);
        construction_txt.setVisibility(View.VISIBLE);
    }
}