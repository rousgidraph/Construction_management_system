package com.example.third_year_project;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;


import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.example.third_year_project.ui.main.SectionsPagerAdapter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

   // ActivityfabBinding bi;
    FloatingActionButton fab_add_item,fab_new_purchase;
    FloatingActionButton fab_add_people;

    boolean isRotate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //bi = DataBindingUtil.setContentView(this,R.layout.activity_main);
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = findViewById(R.id.fab);
       fab_add_item = findViewById(R.id.add_item);
       fab_new_purchase = findViewById(R.id.fab3);
       fab_add_people = findViewById(R.id.fab_add_people);

       viewAnimation.init(fab_new_purchase);
        viewAnimation.init(fab_add_item);
        viewAnimation.init(fab_add_people);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View fab_view) {
                isRotate = viewAnimation.rotateFab(fab_view,!isRotate);
                if(isRotate){
                    viewAnimation.showIn(fab_add_people);
                    viewAnimation.showIn(fab_add_item);
                    viewAnimation.showIn(fab_new_purchase);
                }else {
                    viewAnimation.showOut(fab_add_people);
                    viewAnimation.showOut(fab_add_item);
                    viewAnimation.showOut(fab_new_purchase);
                }
              //  Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();
            }
        });
        fab_add_people.setOnClickListener(this);
        fab_add_item.setOnClickListener(this);
        fab_new_purchase.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Activity.RESULT_OK){

        }
    }

    @Override
    public void onClick(View view) {
        if(view == fab_add_item){
            //Toast.makeText(this, "Some short text", Toast.LENGTH_SHORT).show();
            Intent add_item = new Intent(this, new_item.class);
            startActivity(add_item);

        }else if(view ==fab_add_people){
            Intent add_person = new Intent(this,New_labourer.class);
            startActivity(add_person);
            //Toast.makeText(this, "Some shorter text", Toast.LENGTH_SHORT).show();
        }else if(view == fab_new_purchase){
            //Toast.makeText(this, "Some shorter text", Toast.LENGTH_SHORT).show();
            Intent new_purchase = new Intent(this, New_purchase.class);
            startActivity(new_purchase);
        }

    }
}