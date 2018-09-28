package com.example.trancaoviet.NoodleDrug.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trancaoviet.NoodleDrug.Activities.ServiceDetailActivity;
import com.example.trancaoviet.NoodleDrug.Model.Service;
import com.example.trancaoviet.NoodleDrug.R;
import com.example.trancaoviet.NoodleDrug.Utils;

import java.util.ArrayList;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.MyViewHolder> {

    private ArrayList<Service> mListService;
    Context mContext;
    private int screenWidth;

    public ServiceAdapter(Context context, ArrayList<Service> ListService) {
        super();
        this.mListService = ListService;
        this.mContext = context;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity)mContext).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView ServiceName, ServicePrice;
        ImageView ServiceImage;

        public MyViewHolder(View view) {
            super(view);
            ServiceName = view.findViewById(R.id.txt_service_name);
            ServicePrice = view.findViewById(R.id.txt_service_price);
            ServiceImage = view.findViewById(R.id.img_service_thumb);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("SERVICE", mListService.get(getAdapterPosition() ) );
                    Intent intent = new Intent(mContext, ServiceDetailActivity.class);
                    intent.putExtra("BUNDLE", bundle);
                    mContext.startActivity(intent);
                }
            });
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_service, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Service service = mListService.get(position);
        holder.ServiceName.setText(service.getName());
        holder.ServicePrice.setText(Utils.formatServicePrice(service.getPrice()));

        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.service_image_default);
        holder.ServiceImage.setImageBitmap(Bitmap.createScaledBitmap(bitmap, (int) (screenWidth*0.4), (int) (bitmap.getHeight()* screenWidth*0.4 / bitmap.getWidth()),false));
    }

    @Override
    public int getItemCount() {
        return mListService.size();
    }

}
