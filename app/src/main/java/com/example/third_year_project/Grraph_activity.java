package com.example.third_year_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.third_year_project.Graphs.Pie_graph;
import com.example.third_year_project.Graphs.bar_graph;

import java.util.ArrayList;

public class Grraph_activity extends AppCompatActivity  implements AdapterView.OnItemClickListener {
    TextView title;
    ListView available_graphs;
    ArrayAdapter<String> adapter;
    ArrayList<String > options;
    Context context;

    ArrayList<String> graph_names = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_grraph_activity);
        setContentView(R.layout.activity_reports_activity);
        title = findViewById(R.id.title_report);
        context = Grraph_activity.this;
        title.setText("Graphs");
        available_graphs = findViewById(R.id.list_of_contents);
        options = new ArrayList<>();
        adapter = new ArrayAdapter<>(context,R.layout.listview,options);
        available_graphs.setAdapter(adapter);
        available_graphs.setWillNotDraw(true);
        available_graphs.setOnItemClickListener(this::onItemClick);
        fetch_available_graphs();

    }

    public void fetch_available_graphs(){

        graph_names.add("Total cost per item");
        graph_names.add("Vendor vs Money");
        graph_names.add("Specialty vs Money");



        options.addAll(graph_names);
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
        if(pos == 0){
            Intent bars = new Intent(context, bar_graph.class);
            startActivity(bars);
        }else if(pos == 1){
            Intent pie = new Intent(context, Pie_graph.class);
            pie.putExtra("Key",1);
            startActivity(pie);
        }else if(pos == 2){
            Intent pie = new Intent(context, Pie_graph.class);
            pie.putExtra("Key",2);
            startActivity(pie);

        }else if(pos == 3){}

    }
}