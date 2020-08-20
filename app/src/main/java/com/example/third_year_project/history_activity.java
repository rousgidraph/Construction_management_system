package com.example.third_year_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.third_year_project.storage.Datamanager;
import com.example.third_year_project.storage.databaseGuy;

import java.util.ArrayList;

public class history_activity extends AppCompatActivity {


    ListView overall_history;
    ArrayAdapter<String> adapter;
    ArrayList<String> the_data;
    Context context;
    databaseGuy databaseGuy;
    Datamanager datamanager;
    Button btn_filter ;
    EditText filter_text;
    TextView text_no_data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_activity);
        overall_history = findViewById(R.id.overall_history);
        the_data = new ArrayList<>();
        text_no_data = findViewById(R.id.text_no_data);
        btn_filter = findViewById(R.id.btn_filter);
        filter_text = findViewById(R.id.filter_text);
        btn_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetch_specific_data();
            }
        });
        context = history_activity.this;
        databaseGuy = new databaseGuy(context);
        datamanager = new Datamanager(databaseGuy);
        adapter = new ArrayAdapter<>(context,R.layout.listview,the_data);
        overall_history.setAdapter(adapter);
        fetch_data();
    }

    public void fetch_data(){

        the_data.removeAll(the_data);
        the_data.addAll(datamanager.fetch_all_item_history());
        adapter.notifyDataSetChanged();


    }


    public void check_empty_data(){
        if(the_data == null || the_data.size() < 1){
            text_no_data.setVisibility(View.VISIBLE);
        }else{
            text_no_data.setVisibility(View.INVISIBLE);
        }

    }
    public void fetch_specific_data() {
        if(filter_text.getText().length()<2){
            fetch_data();
            check_empty_data();
            return;
        }else{
            String name = filter_text.getText().toString();
            the_data.removeAll(the_data);
            the_data.addAll(datamanager.fetch_item_history_via_name(name));
            adapter.notifyDataSetChanged();
            check_empty_data();
        }
    }
}