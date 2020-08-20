package com.example.third_year_project;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.third_year_project.logic.Stock;
import com.example.third_year_project.storage.databaseGuy;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class inventory_adapter extends RecyclerView.Adapter<inventory_adapter.viewHolder> {

    ArrayList<Stock> stock_list;
    LayoutInflater inflater;
    Context context;
    color_engine color_engine;
    String TAG= "inventory_adapter";
    databaseGuy databaseGuy;
    Calendar calendar = Calendar.getInstance();


    public inventory_adapter(ArrayList<Stock> stock_list, Context context) {
        this.stock_list = stock_list;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        databaseGuy=  new databaseGuy(context);
    }

    public ArrayList<Stock> getStock_list() {
        return stock_list;
    }

    public void setStock_list(ArrayList<Stock> stock_list) {
        this.stock_list = stock_list;
    }

    public void set_up_pop_up(String item_name_to_show,int position){
        Dialog reduce = new Dialog(context);
        reduce.setContentView(R.layout.pop_up_reduction_units);
        TextView item_name = reduce.findViewById(R.id.pop_up_reduce_item_name);
        EditText reduction_rate = reduce.findViewById(R.id.pop_up_reduce_item_units);
        item_name.setText(item_name_to_show);
        long time_in_millis = calendar.getTimeInMillis();
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
                    Toast.makeText(context, "No changes made", Toast.LENGTH_SHORT).show();
                    reduce.dismiss();
                }else {
                    databaseGuy.reduce_transactions(Integer.parseInt(reduction_rate.getText().toString()),stock_list.get(position).getItem_id());
                    Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
                    reduce.dismiss();
                }
            }
        });

        reduce.show();
    }

    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Log.i(TAG, "onCreateViewHolder: "+stock_list.size());
        View view  = inflater.inflate(R.layout.inventory_card_layout,parent,false);
        color_engine = new color_engine(context);
        return new viewHolder(view);
    }
    public String getFormattedDate(long time_in_millis){
        String result = "dd/mm/yy";
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time_in_millis);
        result = formatter.format(calendar.getTime());


        return result;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        String date = stock_list.get(position).getDate();
        String  item_name = stock_list.get(position).getItem_name();
        int Units = stock_list.get(position).getCurrent_units();

        //String local =getFormattedDate(Long.parseLong(date));


        holder.textdate.setText("last bought on "+date);
        if(Units>999){holder.text_units.setText("999 +");
        }else{
            holder.text_units.setText(String.valueOf(Units));}

        holder.text_item_name.setText(item_name);
        holder.limit_indicator.setColorFilter(context.getColor(color_engine.inventory_color_limit(Units)));



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent view_inventory = new Intent(context,view_inventory.class);
                view_inventory.putExtra("selected stock",stock_list.get(position));
                context.startActivity(view_inventory);

                /**
                new AlertDialog.Builder(context)
                        .setMessage("This will be replaced with a means of editing the particular item ")
                        .setTitle(current)
                        .show();
                **/
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                String  item_name = stock_list.get(position).getItem_name();
                set_up_pop_up(item_name,position);

                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        int size = 0;
        try{
            size = stock_list.size();
        }catch (Exception e){
            Log.i(TAG, "getItemCount: ERROR: "+e.getCause());
        }
        return size;
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        TextView textdate, text_item_name,text_units;
        ImageView limit_indicator;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            textdate = itemView.findViewById(R.id.textdate);
            text_item_name = itemView.findViewById(R.id.text_item_name);
            text_units = itemView.findViewById(R.id.text_units);
            limit_indicator = itemView.findViewById(R.id.limit_indicator);

        }
    }
}
