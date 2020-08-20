package com.example.third_year_project;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.third_year_project.logic.Labourer;
import com.example.third_year_project.storage.Datamanager;
import com.example.third_year_project.storage.databaseGuy;

import java.util.ArrayList;
import java.util.LinkedList;

public class frag_labourers extends Fragment implements View.OnClickListener {
    RecyclerView people_list;
    databaseGuy databaseGuy;
    Datamanager datamanager;
    labourer_adapter adapter;
    Context context = getActivity();
    SwipeRefreshLayout refreshLayout;
    TextView emptyView;

    public frag_labourers() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View returnable_view = inflater.inflate(R.layout.frag_labourers,container,false);
        ArrayList<String> names = new ArrayList<>();
        ArrayList<Integer> phones = new ArrayList<>();
        ArrayList<Integer> colours = new ArrayList<>();
        emptyView = returnable_view.findViewById(R.id.emptyView);
        emptyView.setVisibility(View.INVISIBLE);
        databaseGuy = new databaseGuy(getActivity());
        datamanager = new Datamanager(databaseGuy);
        people_list = returnable_view.findViewById(R.id.people_data_list);
        refreshLayout = returnable_view.findViewById(R.id.refresh_layout);

        //ArrayList<Labourer> peopleList = databaseGuy.labourerList();
        ArrayList<Labourer> peopleList = datamanager.labourerList();
        if (peopleList == null|| peopleList.size()<1){
            emptyView.setVisibility(View.VISIBLE);
            people_list.setVisibility(View.GONE);
            //refreshLayout.setActivated(false);
            //refreshLayout.setEnabled(false);

        }//else {
            //labourer_adapter adapter = new labourer_adapter(getActivity(),names,phones,colours);
            adapter = new labourer_adapter(getActivity(), peopleList);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
            people_list.setLayoutManager(gridLayoutManager);
            people_list.setAdapter(adapter);
       // }


        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                update_recycler();
                refreshLayout.setRefreshing(false);
            }
        });

        return returnable_view;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.fab_add_people){
            Intent add_person = new Intent(context,New_labourer.class);
            startActivity(add_person);
            //Toast.makeText(this, "Some shorter text", Toast.LENGTH_SHORT).show();
        }
    }

    public  void update_recycler(){
        if(datamanager.labourerList().size()>=1) {
            emptyView.setVisibility(View.GONE);
            people_list.setVisibility(View.VISIBLE);
            adapter.setLabourerArrayList(datamanager.labourerList());
            adapter.notifyDataSetChanged();
        }else{
            emptyView.setVisibility(View.VISIBLE);
            people_list.setVisibility(View.GONE);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Activity.RESULT_OK){

        }
    }
}
