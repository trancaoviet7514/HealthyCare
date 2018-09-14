package com.example.trancaoviet.NoodleDrug.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.example.trancaoviet.NoodleDrug.R;

public class SplashActivity extends AppCompatActivity {

    private ProgressBar mProgessBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mProgessBar = findViewById(R.id.pgbar);
        mProgessBar.setOnClickListener(new View.OnClickListener() {

            Intent intent = new Intent(SplashActivity.this,MainActivity.class);
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });


    }
}
