package com.example.trancaoviet.NoodleDrug.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.trancaoviet.NoodleDrug.Activities.DrugDetail;
import com.example.trancaoviet.NoodleDrug.Object.DrugStore;
import com.example.trancaoviet.NoodleDrug.Activities.MapActivity;
import com.example.trancaoviet.NoodleDrug.R;

import java.util.ArrayList;

public class DrugStoreAdapter extends RecyclerView.Adapter<DrugStoreAdapter.MyViewHolder>{

    private ArrayList<DrugStore> drugStoreList;
    Context mContext;

    public DrugStoreAdapter(Context context, ArrayList<DrugStore> drugList) {
        super();
        this.drugStoreList = drugList;
        this.mContext = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView drugStoreName, txtDrugStoreAddress;
        public ImageButton btnDrugStoreViewInMap;
        public DrugStore drugStoreSelected;
        int position = 0;

        public MyViewHolder(View view) {
            super(view);

            drugStoreName = (TextView) view.findViewById(R.id.txt_store_name);
            btnDrugStoreViewInMap = (ImageButton) view.findViewById(R.id.btn_show_store_in_map);
            txtDrugStoreAddress = view.findViewById(R.id.txt_store_address);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mContext.startActivity(new Intent(mContext,DrugDetail.class));
                }
            });



            btnDrugStoreViewInMap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mContext.startActivity(new Intent(mContext,MapActivity.class));
                }
            });

        }


        @Override
        public void onClick(View view) {

        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_drug_store, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.drugStoreSelected = drugStoreList.get(position);
        holder.position = position;
        holder.drugStoreName.setText(drugStoreList.get(position).getName());
        holder.txtDrugStoreAddress.setText(drugStoreList.get(position).getAddress());
    }

    @Override
    public int getItemCount() {
        return drugStoreList.size();
    }

}

