package com.example.third_year_project;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class frag_graphs extends Fragment implements View.OnClickListener {
    CardView card_reports,card_graphs,card_search_item,card_search_worker,card_history,card_help;
    Context context;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_graphs,container,false);
        card_reports =view.findViewById(R.id.Card_reports);card_reports.setOnClickListener(this);
        card_graphs = view.findViewById(R.id.card_graphs);card_graphs.setOnClickListener(this::onClick);
        card_help = view.findViewById(R.id.card_help);card_help.setOnClickListener(this::onClick);
        card_history = view.findViewById(R.id.card_history);card_history.setOnClickListener(this::onClick);
        card_search_item = view.findViewById(R.id.card_search_item);card_search_item.setOnClickListener(this::onClick);
        card_search_worker = view.findViewById(R.id.card_search_worker);card_search_worker.setOnClickListener(this::onClick);
        context = getActivity();
        return view;
    }

    @Override
    public void onClick(View view) {
        if(view == card_reports){
            /***
            new AlertDialog.Builder(context)
                    .setMessage("This will be replaced with a means of viewing the particular item ")
                    .setTitle("Card clicked")
                    .show();
             **/

            Intent reports = new Intent(context , reports_activity.class);
            context.startActivity(reports);
        }else if(view == card_graphs){
            Intent graphs = new Intent(context , Grraph_activity.class);
            context.startActivity(graphs);

        }else if(view == card_help){
            Intent help = new Intent (context , help_activity.class);
            context.startActivity(help);


        }else if(view == card_history){
            Intent history = new Intent(context, history_activity.class );
            context.startActivity(history);

        }else if(view == card_search_item){
            Intent search_item = new Intent (context , search_activity.class);
            context.startActivity(search_item);

        }else if(view == card_search_worker){
            Intent search_worker = new Intent (context , search_worker.class);
            context.startActivity(search_worker);

        }
    }
}
