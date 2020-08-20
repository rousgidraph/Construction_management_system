package com.example.third_year_project;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.third_year_project.logic.Stock;
import com.example.third_year_project.storage.Datamanager;
import com.example.third_year_project.storage.databaseGuy;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class view_inventory extends AppCompatActivity {
    Button btn_reduce;
    TextView txt_curr_units, txt_back_button, txt_units_of_measure;//,view_date ;
    ListView view_item_history;
    Datamanager datamanager;
    databaseGuy databaseGuy;
    Context context;
    Stock stock;
    ArrayAdapter<String> adapter;
    ArrayList<String> item_history;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_inventory);
        context = view_inventory.this;
        txt_curr_units = findViewById(R.id.txt_curr_units);
        txt_back_button = findViewById(R.id.txt_back_button);
        txt_units_of_measure =  findViewById(R.id.txt_units_of_measure);
        view_item_history = findViewById(R.id.view_item_history);
       // view_date = findViewById(R.id.view_date);
        btn_reduce = findViewById(R.id.btn_reduce);
        set_up_text();
        databaseGuy = new databaseGuy(context);
        datamanager = new Datamanager(databaseGuy);
        item_history = new ArrayList<>();
        adapter = new ArrayAdapter<>(context,R.layout.listview,item_history);
        view_item_history.setAdapter(adapter);
        fetch_data();
        txt_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btn_reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              set_up_pop_up(stock.getItem_name());
            }
        });

    }

    public void fetch_data(){
        item_history.removeAll(item_history);
        item_history.addAll(datamanager.fetch_item_history(stock.getItem_id()));
        adapter.notifyDataSetChanged();
    }

    public String getFormattedDate(long time_in_millis){
        String result = "dd/mm/yy";
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time_in_millis);
        result = formatter.format(calendar.getTime());


        return result;
    }

    public void set_up_pop_up(String item_name_to_show){
        Dialog reduce = new Dialog(context);
        reduce.setContentView(R.layout.pop_up_reduction_units);
        TextView item_name = reduce.findViewById(R.id.pop_up_reduce_item_name);
        EditText reduction_rate = reduce.findViewById(R.id.pop_up_reduce_item_units);
        item_name.setText(item_name_to_show);
        Button btn_cancel = reduce.findViewById(R.id.pop_up_reduce_btn_cancel);btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reduce.dismiss();
            }
        });
        Button btn_reduce = reduce.findViewById(R.id.pop_up_reduce_btn_reduce);btn_reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(reduction_rate.getText().length()<1){
                    Toast.makeText(view_inventory.this, "No changes made", Toast.LENGTH_SHORT).show();
                    reduce.dismiss();
                }else {
                    databaseGuy.reduce_transactions(Integer.parseInt(reduction_rate.getText().toString()),stock.getItem_id());
                    Toast.makeText(view_inventory.this, "", Toast.LENGTH_SHORT).show();
                    reduce.dismiss();
                }
            }
        });

        reduce.show();
    }

    public void set_up_text(){
        Intent intent = getIntent();
      stock = (Stock) intent.getExtras().getSerializable("selected stock");
      txt_curr_units.setText(String.valueOf(stock.getCurrent_units()));
      txt_units_of_measure.setText(stock.getItem_name()+" "+stock.getUnit_of_measure());
      //view_date.setText(stock.getDate());
        //view_item_description.setText(stock.get);


    }
}