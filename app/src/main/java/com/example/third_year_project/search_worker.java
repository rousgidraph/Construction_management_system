package com.example.third_year_project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.third_year_project.logic.Labourer;
import com.example.third_year_project.storage.Datamanager;
import com.example.third_year_project.storage.databaseGuy;

import java.util.ArrayList;

public class search_worker extends AppCompatActivity implements View.OnClickListener {
    TextView title,result_count,no_result;
    EditText searchbar;
    ListView display_workers;
    ArrayList<String> search_results;
    Context context;
    databaseGuy databaseGuy;
    Datamanager datamanager;
    ArrayAdapter adapter;
    boolean search_by_name = true;
    Button btn_search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_search_worker);
        setContentView(R.layout.activity_search_activity);
        //declarations
        search_results = new ArrayList();
        adapter = new ArrayAdapter<String>(this,R.layout.listview,search_results);
        context = search_worker.this;
        databaseGuy = new databaseGuy(context);
        datamanager = new Datamanager(databaseGuy);
        title = findViewById(R.id.search_title);
        searchbar = findViewById(R.id.input_search_item);
        no_result = findViewById(R.id.no_results_found);
        searchbar.setHint("Search worker");
        display_workers = findViewById(R.id.search_results_item);
        btn_search = findViewById(R.id.btn_search_items);btn_search.setOnClickListener(this::onClick);
        result_count = findViewById(R.id.search_result_count);
        display_workers.setAdapter(adapter);
        display_workers.setEmptyView(findViewById(R.id.emptyView));
        btn_search.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                set_up_pop_up();
                return false;
            }
        });

        display_workers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                char read_id = search_results.get(pos).charAt(0);
                int derived = Integer.parseInt(String.valueOf(read_id));
                 Labourer edit_me ;

                        edit_me = datamanager.fetch_specific_labourer(derived);

                    Intent edit_labourer = new Intent(view.getContext(),edit_labourer.class);
                    edit_labourer.putExtra("ID",edit_me.getID());
                    edit_labourer.putExtra("Name",edit_me.getFIRST_NAME() );
                    edit_labourer.putExtra("Other",edit_me.getOTHER_NAME() );
                    edit_labourer.putExtra("Phone",edit_me.getPHONE() );
                    edit_labourer.putExtra("Specialty",edit_me.getSPECIALTY() );
                    edit_labourer.putExtra("Wages",edit_me.getWEEKLY_WAGE() );
                    view.getContext().startActivity(edit_labourer);
                }

                //Toast.makeText(context, " clicked number "+edit_me.getSPECIALTY(), Toast.LENGTH_SHORT).show();

        });
    }

    public void set_up_pop_up(){
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.pop_up_search_type);
        Button cancel,done;
        RadioGroup choice = dialog.findViewById(R.id.radio_group_choices);

        done = dialog.findViewById(R.id.pop_up_search_by_btn_done);done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(choice.getCheckedRadioButtonId() == R.id.radioButton_specialty){
                   search_by_name=false;
               }else{
                   search_by_name = true;
               }
                dialog.dismiss();
            }
        });
        cancel = dialog.findViewById(R.id.pop_up_search_by_btn_cancel);cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        dialog.show();
    }

    public void empty_data_checker(){
        if(search_results.size()<=0){
            display_workers.setVisibility(View.INVISIBLE);
            no_result.setVisibility(View.VISIBLE);

        }else{
            display_workers.setVisibility(View.VISIBLE);
            no_result.setVisibility(View.INVISIBLE);

        }
    }

    public void search(){
        String searchable = searchbar.getText().toString();
        result_count.setText( "results");
        if(searchbar.getText().length()<2){
            //searchbar.setError("Can't be empty");
            searchbar.requestFocus();
            empty_data_checker();
        }else if(search_by_name){

            search_results.removeAll(search_results);
            search_results.addAll(datamanager.search_by_name(searchable));
            result_count.setText( String.valueOf(search_results.size()));
            adapter.notifyDataSetChanged();
           // adapter.notify();


        }else{
            search_results.removeAll(search_results);
            search_results.addAll(datamanager.search_by_specialty(searchable));
            result_count.setText( String.valueOf(search_results.size()));
            adapter.notifyDataSetChanged();


        }
    }

    @Override
    public void onClick(View view) {
        if(view == btn_search){
            search();
        }

    }
}