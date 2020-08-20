package com.example.third_year_project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.third_year_project.logic.Item;
import com.example.third_year_project.storage.databaseGuy;

public class new_item extends AppCompatActivity implements View.OnClickListener {
    TextView text_back;
    Button btn_cancel,btn_save;
    EditText input_item_name,input_units_of_measure,input_description;
    databaseGuy databaseGuy;
    String TAG ="new item";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item);
        text_back = findViewById(R.id.text_back); text_back.setOnClickListener(this);
        btn_cancel = findViewById(R.id.btn_cancel);btn_cancel.setOnClickListener(this);
        btn_save = findViewById(R.id.btn_save_item);btn_save.setOnClickListener(this);
        input_description = findViewById(R.id.input_description);
        input_item_name = findViewById(R.id.input_item_name);
        databaseGuy = new databaseGuy(this);
        input_units_of_measure = findViewById(R.id.input_units);



    }

    public void save_Item(){
        boolean isvalid = true;
        String item_name, item_description = "Description",units_of_measure = "Units";
        if(input_item_name.getText().length() <= 1){
            isvalid = false;
            input_item_name.setError("This field is required ");
        }
        if(input_description.getText().length()>1){
            item_description = input_description.getText().toString();
        }
        if(input_units_of_measure.getText().length()>1){
            units_of_measure = input_units_of_measure.getText().toString();
        }

        if(isvalid){
            item_name = input_item_name.getText().toString();
            Item savable_item = new Item(item_name,units_of_measure,item_description);
            databaseGuy.saveitem(savable_item);

            Toast.makeText(this, "Record Saved", Toast.LENGTH_SHORT).show();
            setResult(Activity.RESULT_OK);
            finish();
        }
    }

    @Override
    public void onClick(View view) {
        if(view == text_back){
            finish();
        }else if(view == btn_cancel){
            finish();
        }else  if(view == btn_save){
        save_Item();
        }

    }

    /***
     *    new AlertDialog.Builder(this)
     *                     .setPositiveButton("yes",null)
     *                     .setNegativeButton("Done", null)
     *                     .setView(LayoutInflater.from(this).inflate(R.layout.scroll_dialog_layout,null))
     *                     .setTitle("Select an item")
     *                     .show(); */
}