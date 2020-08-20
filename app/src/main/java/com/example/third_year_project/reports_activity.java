package com.example.third_year_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class reports_activity extends AppCompatActivity {

    TextView title,construction_txt;
    ListView available_reports;
    ArrayAdapter<String> adapter;
    ArrayList<String > options;
    Context context;
    ImageView construction_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports_activity);
        title = findViewById(R.id.title_report);
        context = reports_activity.this;
        title.setText("Reports");
        construction_txt = findViewById(R.id.text_construction);
        construction_img = findViewById(R.id.image_construction);
        construction_img.setVisibility(View.VISIBLE);
        construction_txt.setVisibility(View.VISIBLE);
        available_reports = findViewById(R.id.list_of_contents);
        options = new ArrayList<>();
        adapter = new ArrayAdapter<>(context,R.layout.listview,options);
        available_reports.setAdapter(adapter);


    }
}