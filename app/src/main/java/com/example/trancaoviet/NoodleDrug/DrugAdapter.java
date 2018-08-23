package com.example.trancaoviet.NoodleDrug;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class DrugAdapter extends RecyclerView.Adapter<DrugAdapter.MyViewHolder>{

    private ArrayList<Drug> drugList;
    Context mContext;

    public DrugAdapter(Context context, ArrayList<Drug> drugList) {
        super();
        this.drugList = drugList;
        this.mContext = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView drugName;
        public ImageButton btnDrugBox, btnDrugStore;
        public Drug drugSelected;
        int position = 0;

        public MyViewHolder(View view) {
            super(view);

            drugName = (TextView) view.findViewById(R.id.drug_name);
            btnDrugBox = (ImageButton) view.findViewById(R.id.btnDrugBox);
            btnDrugStore = (ImageButton) view.findViewById(R.id.btnDrugStore);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mContext.startActivity(new Intent(mContext,DrugDetail.class));
                }
            });

            btnDrugBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //MainActivity.taskProvider.deleteTask( drugStoreSelected.getId() );
                    DrugAdapter.this.notifyItemRemoved(position);

                    MainActivity.listDrug.remove(position);
                    DrugAdapter.this.notifyItemRangeChanged(position,drugList.size()+1);

                }
            });

            btnDrugStore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //MainActivity.taskProvider.deleteTask( drugStoreSelected.getId() );
                    DrugAdapter.this.notifyItemRemoved(position);

                    MainActivity.listDrug.remove(position);
                    DrugAdapter.this.notifyItemRangeChanged(position,drugList.size()+1);
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
                .inflate(R.layout.drug_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.drugSelected = drugList.get(position);
        holder.position = position;
        holder.drugName.setText(drugList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return drugList.size();
    }

}
