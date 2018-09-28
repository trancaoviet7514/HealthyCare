package com.example.trancaoviet.NoodleDrug.Adapters;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.trancaoviet.NoodleDrug.Model.News;
import com.example.trancaoviet.NoodleDrug.R;
import com.example.trancaoviet.NoodleDrug.Utils;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> {

    private ArrayList<News> arrNewsList;
    Context mContext;
    private int screenWidth;

    public NewsAdapter(Context context, ArrayList<News> arrNewsList) {
        super();
        this.arrNewsList = arrNewsList;
        this.mContext = context;
        screenWidth = Utils.getScreenWidth(mContext);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView txtNewsTitle;
        public ImageView imgNewsImage;

        public MyViewHolder(View view) {
            super(view);
            txtNewsTitle = view.findViewById(R.id.txt_news_title);
            imgNewsImage = view.findViewById(R.id.img_news_thumb);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String url = arrNewsList.get(getAdapterPosition() ).getContentUrl();
                    try {
                        Intent i = new Intent("android.intent.action.MAIN");
                        i.setComponent(ComponentName.unflattenFromString("com.android.chrome/com.android.chrome.Main"));
                        i.addCategory("android.intent.category.LAUNCHER");
                        i.setData(Uri.parse(url));
                        mContext.startActivity(i);
                    }
                    catch(ActivityNotFoundException e) {
                        // Chrome is not installed
                        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        mContext.startActivity(i);
                    }
                }
            });
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_news, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        News nSelectedNews = arrNewsList.get( position );
        holder.txtNewsTitle.setText( nSelectedNews.getTitle() );
        Glide.with(mContext).load(nSelectedNews.getImageUrl()).apply(new RequestOptions().override((int) (screenWidth*0.4))).into(holder.imgNewsImage);
    }

    @Override
    public int getItemCount() {
        return arrNewsList.size();
    }

}
