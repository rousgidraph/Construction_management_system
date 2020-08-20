package com.example.third_year_project;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.third_year_project.storage.databaseGuy;

import java.util.Map;

import petrov.kristiyan.colorpicker.ColorPicker;

public class New_labourer extends AppCompatActivity  implements View.OnClickListener  {
    ImageView indicator ;
    EditText input_firstName,input_last_name,input_specialty,input_wage,input_phone;
    Button btn_done,btn_cancel;
    databaseGuy dguy ;
    String TAG = "New_labourer";
    Context context ;
    String MyPREFERENCES = "myprefs";
    String stringset = "colours";
    color_engine colorengine ;
    databaseGuy databaseGuy ;
    Map<String , Integer > keys ;

    @Override
    protected void onCreate(Bundle savedInstanceState)   {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_labourer);
        context = New_labourer.this;
        colorengine = new color_engine(context);
        indicator = findViewById(R.id.indicator);
        input_firstName = findViewById(R.id.input_firstName);
        input_last_name = findViewById(R.id.input_last_name);
        input_phone = findViewById(R.id.input_phone);
        input_specialty = findViewById(R.id.input_specialty);
        input_specialty.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if( input_specialty.getText().length() <=1){
                    //isvalid = false;

                    //input_specialty.requestFocus();
                   // input_specialty.setError("Required field");
                }else{
                    String Specialty = input_specialty.getText().toString();
                    indicator.setColorFilter(colorengine.specialty_to_color(Specialty));
                }
            }
        });
        input_wage = findViewById(R.id.input_wage);
        btn_cancel = findViewById(R.id.btn_cancel); btn_cancel.setOnClickListener(this);
        btn_done = findViewById(R.id.btn_done);btn_done.setOnClickListener(this);
        dguy = new databaseGuy(this);






    }

    public int specialty_to_color(String Specialty){
        final int[] returnable_color_code = {R.color.vibrant_green};


        if (keys.containsKey(Specialty.toLowerCase())){
            returnable_color_code[0] = keys.get(Specialty);
        }else{
            ColorPicker colorPicker = new ColorPicker((Activity) context);
            colorPicker.setRoundColorButton(true);
            colorPicker.show();
            colorPicker.setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
                @Override
                public void onChooseColor(int position, int color) {
                    databaseGuy.save_color(Specialty,color);
                    returnable_color_code[0] = color;
                }

                @Override
                public void onCancel() {

                }
            });

        }



        return returnable_color_code[0];
    }


    void saveDetails(){
        boolean isvalid = true ;
        if( input_firstName.getText().length() <=1){
            isvalid = false;
            input_firstName.requestFocus();
            input_firstName.setError("Required field");
        }else if( input_last_name.getText().length() <=1){
            isvalid = false;
            input_last_name.requestFocus();
            input_last_name.setError("Required field");
        } else  if( input_specialty.getText().length() <=1){
            isvalid = false;

            input_specialty.requestFocus();
            input_specialty.setError("Required field");
        } else if( input_wage.getText().length() <=1){
            isvalid = false;
            input_wage.requestFocus();
            input_wage.setError("Required field");
        }else if( input_phone.getText().length() <=1){
            isvalid = false;
            input_phone.requestFocus();
            input_phone.setError("Required field");
        }





        if(isvalid){
            try {
            String firstname = input_firstName.getText().toString();
            String othernames = input_last_name.getText().toString();
            String specialty = input_specialty.getText().toString();
            //specialty_analyzer(specialty);
            int wages = Integer.parseInt(input_wage.getText().toString());
            String phone = input_phone.getText().toString();
            dguy.saveworker(firstname,othernames,phone,wages,specialty);
            setResult(RESULT_OK, new Intent());
            Toast.makeText(this, "Saved successfully", Toast.LENGTH_SHORT).show();
                finish();
            }catch(NumberFormatException e){
                input_wage.requestFocus();
                input_wage.setError("Enter numbers ");
                Log.i(TAG, "saveDetails: wrong wage format ");
            }
            
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void specialty_analyzer() {
        // TODO: 7/28/20 get the colors working
        ColorPicker colorPicker = new ColorPicker((Activity) context);
        colorPicker.show();

        }




    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View view) {
        if(view == btn_done){
            //Toast.makeText(this, "Well well my work here is done ", Toast.LENGTH_SHORT).show();
            //indicator.setColorFilter(getColor(R.color.dull_green));//this is the code you will use to changethe colors accordingly
          //      specialty_analyzer();
           saveDetails();
        }else if(view == btn_cancel){
            finish();
            //indicator.setColorFilter(getColor(R.color.dark_red));
        }else if(view == input_specialty){

        }

    }
}