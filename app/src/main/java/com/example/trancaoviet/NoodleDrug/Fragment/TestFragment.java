package com.example.trancaoviet.NoodleDrug.Fragment;


import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.trancaoviet.NoodleDrug.R;

//Class test cho bottom navigaton view
@SuppressLint("ValidFragment")
public class TestFragment extends Fragment{
    private String mString;

    public TestFragment(String s) {
        this.mString = s;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_test,container,false);
        TextView textView = v.findViewById(R.id.txt_test_fragment);
        textView.setText(textView.getText() + mString);
        return v;
    }
}
