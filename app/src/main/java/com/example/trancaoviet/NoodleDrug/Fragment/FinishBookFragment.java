package com.example.trancaoviet.NoodleDrug.Fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.trancaoviet.NoodleDrug.Activities.BookServiceActivity;
import com.example.trancaoviet.NoodleDrug.Activities.MainActivity;
import com.example.trancaoviet.NoodleDrug.R;

public class FinishBookFragment extends Fragment {

    Button btnFinish;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_finish,container,false);
        btnFinish = view.findViewById(R.id.btn_finish);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra("FRAGMENT", "SERVICE");
                startActivity(intent);
            }
        });
    }
}
