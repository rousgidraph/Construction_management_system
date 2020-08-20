package com.example.third_year_project;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.ColorFilter;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.third_year_project.logic.Labourer;
import com.example.third_year_project.storage.databaseGuy;

import java.util.ArrayList;
import java.util.List;

public class labourer_adapter extends RecyclerView.Adapter< labourer_adapter.ViewHolder > {
    List firstName;
    List phones;
    List colours;
    ArrayList<Labourer> labourerArrayList;
    Context context;
    LayoutInflater inflater;
    color_engine color_engine;
    databaseGuy databaseGuy;
    String TAG = "labourer_adapter";
    public void setLabourerArrayList(ArrayList<Labourer> labourerArrayList) {
        this.labourerArrayList = labourerArrayList;
    }

    public labourer_adapter(Context ctx , ArrayList<Labourer> people){
        this.labourerArrayList = people;
        this.context = ctx;
        this.inflater = LayoutInflater.from(ctx);
        this.databaseGuy = new databaseGuy(context);

    }
    public labourer_adapter(Context ctx, List firstnames , List phonenumbers, List CoLors){
        this.firstName = firstnames;
        this.phones = phonenumbers;
        this.context = ctx;
        this.inflater = LayoutInflater.from(ctx);
        this.colours = CoLors;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.people_card_layout,parent,false);
        color_engine = new color_engine(context);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        //holder.text_view_person_name.setText(firstName.get(position).toString());
        //holder.text_view_person_phone.setText(phones.get(position).toString());
        //holder.status_indicator.setColorFilter(context.getColor((Integer) colours.get(position)));
        //holder.status_indicator.setBackgroundColor(context.getColor());
        holder.text_view_person_name.setText(labourerArrayList.get(position).getFIRST_NAME());
        holder.text_view_person_phone.setText(labourerArrayList.get(position).getPHONE());
        ColorFilter colorFilter = new ColorFilter();

        holder.status_indicator.setColorFilter(color_engine.specialty_to_color_safe(labourerArrayList.get(position).getSPECIALTY()));
        Log.i(TAG, "onBindViewHolder: here's what i passed "+ labourerArrayList.get(position).getSPECIALTY());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(context, "Selceted: ", Toast.LENGTH_SHORT).show();
                //this is where you wanna have the code fo handling the individial items on the list
                Intent edit_labourer = new Intent(view.getContext(),edit_labourer.class);
                edit_labourer.putExtra("ID",labourerArrayList.get(position).getID());
                edit_labourer.putExtra("Name",labourerArrayList.get(position).getFIRST_NAME() );
                edit_labourer.putExtra("Other",labourerArrayList.get(position).getOTHER_NAME() );
                edit_labourer.putExtra("Phone",labourerArrayList.get(position).getPHONE() );
                edit_labourer.putExtra("Specialty",labourerArrayList.get(position).getSPECIALTY() );
                edit_labourer.putExtra("Wages",labourerArrayList.get(position).getWEEKLY_WAGE() );
                view.getContext().startActivity(edit_labourer);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                new AlertDialog.Builder(context).setTitle("Are you sure ")
                        .setMessage("This will delete the current worker and can not be undone").setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        boolean result = databaseGuy.deleteWorker(labourerArrayList.get(position).getID());


                    }
                })
                        .setNegativeButton("Cancel", null).show();
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return labourerArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView text_view_person_name;
        TextView text_view_person_phone;
        ImageView status_indicator;
        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            text_view_person_name = itemView.findViewById(R.id.text_view_person_name);
            text_view_person_phone = itemView.findViewById(R.id.text_view_person_phone);
            status_indicator = itemView.findViewById(R.id.status_indicator);


        }
    }

}
