package com.example.trancaoviet.NoodleDrug.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.trancaoviet.NoodleDrug.Object.News;
import com.example.trancaoviet.NoodleDrug.R;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> {

    private ArrayList<News> arrNewsList;
    Context mContext;

    public NewsAdapter(Context context, ArrayList<News> arrNewsList) {
        super();
        this.arrNewsList = arrNewsList;
        this.mContext = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView txtNewsTitle;
        public ImageView imgNewsImage;

        public MyViewHolder(View view) {
            super(view);
            txtNewsTitle = view.findViewById(R.id.txt_news_title);
            imgNewsImage = view.findViewById(R.id.img_news_thumb);
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
        holder.imgNewsImage.setImageBitmap( nSelectedNews.getImage() );
    }

    @Override
    public int getItemCount() {
        return arrNewsList.size();
    }

}
