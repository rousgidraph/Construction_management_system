package com.example.third_year_project;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.third_year_project.R;
import com.example.third_year_project.logic.Labourer;
import com.example.third_year_project.storage.databaseGuy;

public class edit_labourer extends AppCompatActivity implements View.OnClickListener {
    ImageView indicator ;
    EditText input_firstName,input_last_name,input_specialty,input_wage,input_phone;
    Button btn_done,btn_cancel;
    TextView title;
    int worker_id;
    Context context;
    String TAG = "edit_labourer";
    databaseGuy dguy;
    color_engine colorengine ;
    String First,others,specilaty, phone;
    int wages;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_labourer);
        indicator = findViewById(R.id.indicator);
        context = edit_labourer.this;
        input_firstName = findViewById(R.id.input_firstName);
        input_last_name = findViewById(R.id.input_last_name);
        input_phone = findViewById(R.id.input_phone);
        input_specialty = findViewById(R.id.input_specialty);
        input_wage = findViewById(R.id.input_wage);
        colorengine = new color_engine(context);
        title = findViewById(R.id.title_new_labourer);
        btn_cancel = findViewById(R.id.btn_cancel); btn_cancel.setOnClickListener(this);
        btn_done = findViewById(R.id.btn_done);btn_done.setOnClickListener(this);

        dguy = new databaseGuy(context);

        title.setText("Labourer Details");
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            First = extras.getString("Name");
            others = extras.getString("Other");
            specilaty = extras.getString("Specialty");
            phone = extras.getString("Phone");
            wages = extras.getInt("Wages");
            worker_id = extras.getInt("ID");
            input_firstName.setText(First);
            input_last_name.setText(others);
            input_phone.setText(phone);
            input_specialty.setText(specilaty);
            input_wage.setText(String.valueOf(wages));
            indicator.setColorFilter(colorengine.specialty_to_color(specilaty));
        }

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
                // TODO: 8/2/20 theres is an error
                dguy.updateWorker(new Labourer(worker_id,firstname,othernames,specialty,wages,phone));
                setResult(RESULT_OK, new Intent());
                Toast.makeText(this, "Changes saved successfully", Toast.LENGTH_SHORT).show();
                finish();
            }catch(NumberFormatException e){
                input_wage.requestFocus();
                input_wage.setError("Enter numbers ");
                Log.i(TAG, "saveDetails: wrong wage format ");
            }

        }

    }


    @Override
    public void onClick(View view) {
        if(view == btn_cancel){
            finish();
        }else if (view == btn_done){
            saveDetails();
        }

    }
}