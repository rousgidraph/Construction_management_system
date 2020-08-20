package com.example.third_year_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.third_year_project.logic.Item;
import com.example.third_year_project.logic.Stock;
import com.example.third_year_project.storage.Datamanager;
import com.example.third_year_project.storage.databaseGuy;

import java.util.ArrayList;

public class search_activity extends AppCompatActivity implements View.OnClickListener {
    TextView title,result_count, no_result;
    EditText searchbar;
    ListView display_items;
    ArrayList<String> search_results;
    Context context;
    com.example.third_year_project.storage.databaseGuy databaseGuy;
    Datamanager datamanager;
    ArrayAdapter adapter;
    Button btn_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_activity);
        //declarations
        search_results = new ArrayList();
        adapter = new ArrayAdapter<String>(this,R.layout.listview,search_results);
        context = search_activity.this;
        databaseGuy = new databaseGuy(context);
        datamanager = new Datamanager(databaseGuy);
        title = findViewById(R.id.search_title);
        searchbar = findViewById(R.id.input_search_item);
        no_result = findViewById(R.id.no_results_found);

        display_items = findViewById(R.id.search_results_item);
        btn_search = findViewById(R.id.btn_search_items);btn_search.setOnClickListener(this::onClick);
        result_count = findViewById(R.id.search_result_count);
        display_items.setAdapter(adapter);
        display_items.setEmptyView(findViewById(R.id.emptyView));
        display_items.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                char read_id = search_results.get(pos).charAt(0);

                Stock clicked_item = datamanager.fetch_item_stock_data(Integer.parseInt(String.valueOf(read_id)));
                Intent view_inventory = new Intent(context,view_inventory.class);
                view_inventory.putExtra("selected stock",clicked_item);
                context.startActivity(view_inventory);

            }
        });

    }

    public void search() {
        String searchable = searchbar.getText().toString();
        result_count.setText("results");
        if (searchbar.getText().length() < 2) {
            //searchbar.setError("Can't be empty");
            searchbar.requestFocus();
        } else {
            search_results.removeAll(search_results);
            search_results.addAll(datamanager.search_by_item_name(searchable));
            empty_data_checker();
            result_count.setText( String.valueOf(search_results.size()));
            adapter.notifyDataSetChanged();
        }
    }

    public void empty_data_checker(){
        if(search_results.size()<=0){
          display_items.setVisibility(View.INVISIBLE);
          no_result.setVisibility(View.VISIBLE);

        }else{
            display_items.setVisibility(View.VISIBLE);
            no_result.setVisibility(View.INVISIBLE);

        }
    }

    @Override
    public void onClick(View view) {
        if(view == btn_search){
            search();
        }
    }
}