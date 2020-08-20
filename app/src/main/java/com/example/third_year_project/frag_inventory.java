package com.example.third_year_project;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.third_year_project.logic.Stock;
import com.example.third_year_project.storage.Datamanager;
import com.example.third_year_project.storage.databaseGuy;

import java.util.ArrayList;

public class frag_inventory extends Fragment {
    RecyclerView inventory_datalist;

    SwipeRefreshLayout refreshLayout;
    ArrayList<Stock> stock_list;
    Context context ;
    inventory_adapter adapter;
    TextView no_data;
    String TAG = "frag inventory";
    databaseGuy databaseGuy;
    Datamanager datamanager ;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View returnable_view = inflater.inflate(R.layout.frag_inventory,container,false);
        context = getActivity();
        databaseGuy = new databaseGuy(context);
        no_data = returnable_view.findViewById(R.id.text_no_data);
        datamanager = new Datamanager(databaseGuy);
        inventory_datalist = returnable_view.findViewById(R.id.inventory_datalist);
        //stock_list = datamanager.get_stock_list();
        stock_list = datamanager.fetch_stock_data();
        adapter = new inventory_adapter(stock_list,context);
        no_data_checker();
        inventory_datalist.setLayoutManager(new LinearLayoutManager(getContext()));
        inventory_datalist.setAdapter(adapter);
        refreshLayout = returnable_view.findViewById(R.id.refreshlayout1);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //update_recycler();
                fetch_data();
                no_data_checker();
                refreshLayout.setRefreshing(false);
            }
        });
        return returnable_view;
    }


    public void fetch_data(){
      //  Runnable data_fetch = new Runnable() {
        //    @Override
          //  public void run() {
                //stock_list = datamanager.get_stock_list();
        stock_list = datamanager.fetch_stock_data();


            //}
        //};
        //Thread fetcher_thread = new Thread(data_fetch);
        //fetcher_thread.start();


    }
    public void no_data_checker(){
        if (stock_list == null){
            no_data.setVisibility(View.VISIBLE);
            inventory_datalist.setVisibility(View.GONE);
            Log.i(TAG, " fetched_stocks : THe data didnt get to frag inventory ");
        }else{
            no_data.setVisibility(View.GONE);
            adapter.setStock_list(stock_list);
            adapter.notifyDataSetChanged();
            inventory_datalist.setVisibility(View.VISIBLE);
            //Log.i(TAG, "fetch_data: the stock claims it has data: error is on next line");
          //  Log.i(TAG, "fetch_data: "+stock_list.get(0).toString()+stock_list.size());
        }
    }
}
