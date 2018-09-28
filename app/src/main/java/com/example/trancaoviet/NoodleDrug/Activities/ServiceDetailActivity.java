package com.example.trancaoviet.NoodleDrug.Activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trancaoviet.NoodleDrug.Model.Service;
import com.example.trancaoviet.NoodleDrug.R;
import com.example.trancaoviet.NoodleDrug.Utils;

public class ServiceDetailActivity extends AppCompatActivity {

    TextView txtServiceName, txtServicePrice, txtServiceDescription;
    ImageView imgServiceImage;
    Button btnBook;
    ActionBar actionBar;
    protected Service mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_detail);

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        addControl();

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("BUNDLE");
        mService = (Service) bundle.getSerializable("SERVICE");

        txtServiceName.setText(mService.getName());
        txtServicePrice.setText(Utils.formatServicePrice(mService.getPrice() ) );
        txtServiceDescription.setText(String.valueOf(mService.getDescription() ) );

    }

    private void addControl() {
        txtServiceName = findViewById(R.id.txt_service_name);
        txtServicePrice = findViewById(R.id.txt_service_price);
        txtServiceDescription = findViewById(R.id.txt_service_description);
        imgServiceImage = findViewById(R.id.img_service);
        btnBook = findViewById(R.id.btn_service_register);

        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("SERVICE", mService );
                Intent intent = new Intent(ServiceDetailActivity.this, BookServiceActivity.class);
                intent.putExtra("BUNDLE", bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
