package com.example.trancaoviet.NoodleDrug.Fragment;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.trancaoviet.NoodleDrug.Adapters.NewsAdapter;
import com.example.trancaoviet.NoodleDrug.DataIO.Provider;
import com.example.trancaoviet.NoodleDrug.Model.News;
import com.example.trancaoviet.NoodleDrug.R;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    public Provider provider;
    public RecyclerView rcvNews;
    public static ArrayList<News> listNews = new ArrayList<>();
    public NewsAdapter newsAdapter ;

    private View mRootView;
    private Context mContext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = getActivity();
        provider = Provider.getInstance();
        addControls();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_home,container,false);
        mRootView = view;
        return view;
    }

    private void addControls() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.avatar_default);
        newsAdapter = new NewsAdapter(mContext, listNews);
    }

    @Override
    public void onStart() {
        super.onStart();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        rcvNews = mRootView.findViewById(R.id.rcv_news);
        rcvNews.setLayoutManager(linearLayoutManager);
        rcvNews.setAdapter(newsAdapter);
    }
}
