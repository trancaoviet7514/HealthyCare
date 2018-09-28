package com.example.trancaoviet.NoodleDrug.Fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.trancaoviet.NoodleDrug.Activities.BookServiceActivity;
import com.example.trancaoviet.NoodleDrug.Adapters.ClinicAdapter;
import com.example.trancaoviet.NoodleDrug.DataIO.Provider;
import com.example.trancaoviet.NoodleDrug.Model.Clinic;
import com.example.trancaoviet.NoodleDrug.R;

import java.util.ArrayList;

public class ListClinicFragment extends Fragment {

    public Provider provider;
    public RecyclerView rcvClinicList;
    public ClinicAdapter clinicAdapter;
    public static ArrayList<Clinic> listClinic = new ArrayList<>();
    private View mRootView;
    private Context mContext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        provider = Provider.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_list_clinic,container,false);
        mRootView = view;
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        clinicAdapter = new ClinicAdapter(mContext, listClinic);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        rcvClinicList = mRootView.findViewById(R.id.rcv_clinic);
        rcvClinicList.setLayoutManager(linearLayoutManager);
        rcvClinicList.setAdapter(clinicAdapter);
    }
}
