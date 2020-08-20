package com.example.third_year_project;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.ColorFilter;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.third_year_project.storage.databaseGuy;

import java.util.HashMap;
import java.util.Map;

import petrov.kristiyan.colorpicker.ColorPicker;

public class color_engine {
    String TAG = "Color engine";
    Context context;
    databaseGuy databaseGuy ;
    Map<String , Integer > keys  = new HashMap<>();

    public int inventory_color_limit(int units){
        if(units < 10){
            return R.color.dark_red;
        }else if(units < 30){
            return R.color.yellow;
        }else if(units < 100){
            return R.color.dull_green;
        }else if(units > 100){
            return R.color.vibrant_green;
        }
        return R.color.grey_80;
    }

    public color_engine(Context context) {
        this.context = context;
        databaseGuy = new databaseGuy(context);

        refresh_colors();

    }


    public void refresh_colors(){
        keys = databaseGuy.get_colors();
    }

    public int specialty_to_color(String Specialty){
        final int[] returnable_color_code = new int [] {R.color.vibrant_green};


        if (keys.containsKey(Specialty.toLowerCase())){
            try {
                returnable_color_code[0] = keys.get(Specialty);
            }catch (Exception e ){
                Log.i(TAG, "specialty_to_color_safe: Errro n setting the color "+e.getCause()+" message "+e.getMessage());
            }
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

    public int specialty_to_color_safe(String Specialty){
        final int[] returnable_color_code = {R.color.vibrant_green};
        if(keys.isEmpty() || keys == null){
            refresh_colors();
            return returnable_color_code[0];
        }
        if (keys.containsKey(Specialty.toLowerCase())){
            try {
                returnable_color_code[0]  = (int ) keys.get(Specialty);
            }catch (Exception e){
                Log.i(TAG, "specialty_to_color_safe: Errro n setting the color "+e.getCause()+" message "+e.getMessage());
            }


            //ColorFilter colorFilter = new ColorFilter();
            // TODO: 8/8/20 theres a color problem here
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


        databaseGuy.close();
        return returnable_color_code[0];
    }


}
