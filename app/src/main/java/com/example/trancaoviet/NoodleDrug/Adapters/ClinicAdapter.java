package com.example.trancaoviet.NoodleDrug.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trancaoviet.NoodleDrug.Activities.DrugDetail;
import com.example.trancaoviet.NoodleDrug.Model.Clinic;
import com.example.trancaoviet.NoodleDrug.Model.Pharmacy;
import com.example.trancaoviet.NoodleDrug.Activities.MapActivity;
import com.example.trancaoviet.NoodleDrug.R;
import com.example.trancaoviet.NoodleDrug.Utils;

import java.util.ArrayList;

public class ClinicAdapter extends RecyclerView.Adapter<ClinicAdapter.MyViewHolder>{

    private ArrayList<Clinic> mClinicList;
    Context mContext;

    public ClinicAdapter(Context context, ArrayList<Clinic> ClinicList) {
        super();
        this.mClinicList = ClinicList;
        this.mContext = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView drugClinicName, txtDrugClinicAddress;
        public ImageButton btnDrugStoreViewInMap;
        ImageView imgClinicAvatar;

        public MyViewHolder(View view) {
            super(view);

            drugClinicName = view.findViewById(R.id.txt_clinic_name);
            btnDrugStoreViewInMap = view.findViewById(R.id.btn_show_clinic_in_map);
            txtDrugClinicAddress = view.findViewById(R.id.txt_clinic_address);
            imgClinicAvatar = view.findViewById(R.id.img_avatar);

            btnDrugStoreViewInMap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mContext.startActivity(new Intent(mContext,MapActivity.class));
                }
            });

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_clinic, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Clinic clinic = mClinicList.get(position);
        holder.drugClinicName.setText(clinic.getName());
        holder.txtDrugClinicAddress.setText(clinic.getAddress());
        holder.imgClinicAvatar.getLayoutParams().width = (int) (Utils.getScreenWidth(mContext) * 0.2);
        holder.imgClinicAvatar.getLayoutParams().height = (int) (Utils.getScreenWidth(mContext) * 0.2);
        holder.imgClinicAvatar.setImageResource(R.drawable.clinic_avatar_default);
    }

    @Override
    public int getItemCount() {
        return mClinicList.size();
    }

}

