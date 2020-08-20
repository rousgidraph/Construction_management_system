package com.example.third_year_project.Graphs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.listener.Event;
import com.anychart.chart.common.listener.ListenersInterface;
import com.anychart.charts.Pie;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;
import com.example.third_year_project.R;
import com.example.third_year_project.storage.Datamanager;
import com.example.third_year_project.storage.databaseGuy;

import java.util.ArrayList;
import java.util.List;

public class Pie_graph extends AppCompatActivity {

    TextView title;
    AnyChartView anyChartView;
    Datamanager datamanager;
    com.example.third_year_project.storage.databaseGuy databaseGuy;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graphs_common);
        title = findViewById(R.id.chart_title);
        context = Pie_graph.this;
        databaseGuy = new databaseGuy(context);
        datamanager = new Datamanager(databaseGuy);
        anyChartView = findViewById(R.id.chart_view);
        anyChartView.setProgressBar(findViewById(R.id.progress_bar));

        int  choice = getIntent().getExtras().getInt("Key");
        if(choice == 2){
            set_up_specilaty_vs_money();
        }else if(choice == 1){
            set_up_Vendor_vs_money();
        }

        //set_up_specilaty_vs_money();
    }

    public void set_up_specilaty_vs_money(){
        Pie pie = AnyChart.pie();
        /**
        pie.setOnClickListener(new ListenersInterface.OnClickListener(new String[]{"x", "value"}) {//handle the touch
            @Override
            public void onClick(Event event) {
                //Toast.makeText(context, event.getData().get("x") + ":" + event.getData().get("value"), Toast.LENGTH_SHORT).show();
            }
        });
        **/
        List<DataEntry> data = new ArrayList<>();
        data.addAll(datamanager.specialty_vs_money());
        pie.data(data);
        pie.labels().position("outside");
        title.setText("Specialty vs Money");
        pie.legend().title().enabled(true);
        pie.legend().title()
                .text("Specialties ")
                .padding(0d, 0d, 10d, 0d);
        pie.legend()
                .position("center-bottom")
                .itemsLayout(LegendLayout.HORIZONTAL)
                .align(Align.CENTER);

        anyChartView.setChart(pie);


    }

    public void set_up_Vendor_vs_money(){
        Pie pie = AnyChart.pie();
        /**
         pie.setOnClickListener(new ListenersInterface.OnClickListener(new String[]{"x", "value"}) {//handle the touch
         @Override
         public void onClick(Event event) {
         //Toast.makeText(context, event.getData().get("x") + ":" + event.getData().get("value"), Toast.LENGTH_SHORT).show();
         }
         });
         **/
        List<DataEntry> data = new ArrayList<>();
        data.addAll(datamanager.Vendor_vs_money());
        pie.data(data);
        pie.labels().position("outside");
        title.setText("Vendor vs money");
        pie.legend().title().enabled(true);
        pie.legend().title().text("Vendors")
                .padding(0d, 0d, 10d, 0d);
        pie.legend()
                .position("center-bottom")
                .itemsLayout(LegendLayout.HORIZONTAL)
                .align(Align.CENTER);

        anyChartView.setChart(pie);


    }

}