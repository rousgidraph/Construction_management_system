package com.example.third_year_project;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import com.example.third_year_project.logic.Item;
import com.example.third_year_project.logic.Reciept;
import com.example.third_year_project.logic.Transaction;
import com.example.third_year_project.storage.Datamanager;
import com.example.third_year_project.storage.databaseGuy;

import java.util.Date;

public class New_purchase extends AppCompatActivity implements DatePickerDialog.OnDateSetListener  {
    Calendar calendar = Calendar.getInstance();
    TextView input_Date;
    DatePickerDialog datepicker;
    Button btn_add_items,btn_done,btn_cancel;
    long curr_date;
    EditText input_receipt_number, input_vendor,receipt_total_costs;
    String TAG = "New_purchase";
    ArrayList<String> already_selected_items;
    ArrayList<String > item_names;
    ArrayList<Item> itemsList;
    ArrayList<Transaction> transactions;
    Datamanager datamanager;
    databaseGuy databaseGuy;
    ListView selected_items;
    ArrayAdapter arrayAdapter;
    Context context =  New_purchase.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_purchase);
        already_selected_items = new ArrayList<>();
        transactions = new ArrayList<>();
        databaseGuy = new databaseGuy(New_purchase.this);
        datamanager = new Datamanager(databaseGuy);
        item_names = new ArrayList<>();
        selected_items = findViewById(R.id.added_items_list);
        btn_add_items= findViewById(R.id.btn_add);
        btn_add_items.requestFocus();
        curr_date =  calendar.getTimeInMillis();
        receipt_total_costs = findViewById(R.id.receipt_total_costs);
        btn_add_items.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setup_popup();
            }
        });
        input_receipt_number = findViewById(R.id.input_reciept_number);
        input_vendor = findViewById(R.id.input_vendor);
        curr_date = calendar.getTimeInMillis();
        input_Date = findViewById(R.id.datePicker);
        input_Date.setText(getFormattedDate(calendar.getTimeInMillis()));
        arrayAdapter = new ArrayAdapter<String>(this,R.layout.listview,already_selected_items);
        selected_items.setAdapter(arrayAdapter);
        //selected_items.setEmptyView();

        input_Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setUpDatepicker();
                datepicker.show();
            }
        });


        selected_items.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int pos, long l) {
                new AlertDialog.Builder(context).setTitle("Confirm removal")
                        .setMessage("This action wll remove the item from the list..")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                already_selected_items.remove(pos);
                                arrayAdapter.notifyDataSetChanged();
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        closeContextMenu();
                    }
                }).show();
                return false;
            }
        });

        btn_done = findViewById(R.id.new_purchase_btn_done);
        btn_done.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                if (transactions.size()<1){
                    new AlertDialog.Builder(context).setTitle("Empty receipt")
                            .setMessage("Try adding some details to you receipt")
                            .show();
                }else{
                   
                    String Vendor;
                    if(input_vendor.getText().length()<1){
                        Vendor = "Unspecified";
                    }else{Vendor = input_vendor.getText().toString();}

                    String reciept_number ;
                    if(input_receipt_number.getText().length()<1){
                        reciept_number = "Rec_no";
                    }else{
                        reciept_number = input_receipt_number.getText().toString();
                    }
                    double total_costs;
                    if(receipt_total_costs.getText().length()<1){
                        receipt_total_costs.setError("Enter total cost");
                    }else {
                        total_costs = Double.parseDouble(receipt_total_costs.getText().toString());

                        Reciept save_this_reciept = new Reciept(curr_date, Vendor, reciept_number, total_costs);
                        save_this_reciept.setTransactions(transactions);
                        databaseGuy.save_transactions(save_this_reciept);
                        Log.i(TAG, "onClick: Bro you did it the record was saved");
                        finish();
                    }
                }

            }
        });
        btn_cancel = findViewById(R.id.new_purchase_btn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(context).setTitle("Proceed to exit")
                        .setMessage("This action will discard any unsaved work")
                        .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finishActivity(Activity.RESULT_OK);
                                finish();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                closeContextMenu();
                            }
                        })
                        .show();
            }
        });

    getitems();
    }

    public void setup_popup(){

        Dialog item_picker = new Dialog(New_purchase.this);
        item_picker.setContentView(R.layout.scroll_dialog_layout);
        Spinner item_option =  item_picker.findViewById(R.id.item_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.spinner_item,item_names);
        item_option.setAdapter(adapter);
        item_option.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                Log.i(TAG, "onItemSelected: "+item_names.get(pos));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        Button  btn_done = item_picker.findViewById(R.id.pop_up_btn_done);
        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText units = item_picker.findViewById(R.id.pop_up_enter_units);
                EditText cost = item_picker.findViewById(R.id.pop_up_enter_cost);
                boolean proceed = false;
                if(units.getText().length()<1){
                    units.setError("This cant be blank");
                    proceed = false;
                }else{
                    proceed = true;
                }
                if (cost.getText().length()<1){
                    cost.setError("This cant be blank");
                    proceed = false;

                }else{
                    proceed = true;
                }
                if(proceed){
                    String curr_item = item_option.getSelectedItem().toString();
                    int curr_units = Integer.parseInt(units.getText().toString());
                    double costs = Double.parseDouble(cost.getText().toString());
                    already_selected_items.add(curr_units+"  :  "+curr_item+"   "+costs);

                    transactions.add(new Transaction(itemsList.get(item_names.indexOf(curr_item)).getItem_id(),curr_units,costs));
                    Log.i(TAG, "onClick: transaction size"+transactions.size());
                    Log.i(TAG, "onClick: "+curr_item+" "+curr_units);
                    refreshList();
                    item_picker.cancel();
                }
            }
        });
        Button btn_cancel = item_picker.findViewById(R.id.pop_up_btn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item_picker.cancel();
            }
        });
        Button btn_new = item_picker.findViewById(R.id.pop_up_btn_newItem);
        btn_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent new_item = new Intent(New_purchase.this,new_item.class);
                New_purchase.this.startActivity(new_item);
               getitems();
                adapter.notifyDataSetChanged();
            }
        });
        item_picker.show();
    }

    public void refreshList(){
        arrayAdapter.notifyDataSetChanged();
    }



    public void setUpDatepicker(){
        datepicker = new DatePickerDialog(this,this,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));

    }



    public String getFormattedDate(long time_in_millis){
        String result = "dd/mm/yy";
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time_in_millis);
        result = formatter.format(calendar.getTime());


        return result;
    }

    public void getitems(){
        Runnable fetch = new Runnable() {
            @Override
            public void run() {
                itemsList = datamanager.itemArrayList();
                item_names = datamanager.item_names();
            }
        };

        Thread thread_data = new Thread(fetch);
        thread_data.start();

    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day_of_month) {
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month);
        calendar.set(Calendar.DAY_OF_MONTH,day_of_month);

        Log.i(TAG, "onDateSet: "+calendar.getTimeInMillis());
        Log.i(TAG, "onDateSet: "+day_of_month+"/"+month+"/"+year);
        input_Date.setText(getFormattedDate(calendar.getTimeInMillis()));
        curr_date =  calendar.getTimeInMillis();

    }
}