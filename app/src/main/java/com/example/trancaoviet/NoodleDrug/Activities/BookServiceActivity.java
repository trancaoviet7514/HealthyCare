package com.example.trancaoviet.NoodleDrug.Activities;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;

import com.example.trancaoviet.NoodleDrug.CallBack.ClinicListCallback;
import com.example.trancaoviet.NoodleDrug.DataIO.Provider;
import com.example.trancaoviet.NoodleDrug.Fragment.CheckInfoBookFragment;
import com.example.trancaoviet.NoodleDrug.Fragment.FinishBookFragment;
import com.example.trancaoviet.NoodleDrug.Fragment.ListClinicFragment;
import com.example.trancaoviet.NoodleDrug.Fragment.PersionBookFragment;
import com.example.trancaoviet.NoodleDrug.Fragment.TimeBookFragment;
import com.example.trancaoviet.NoodleDrug.Model.Clinic;
import com.example.trancaoviet.NoodleDrug.Model.Service;
import com.example.trancaoviet.NoodleDrug.R;

import java.util.ArrayList;

public class BookServiceActivity extends AppCompatActivity {
    ActionBar actionBar;
    Fragment curFragment;
    Service mService;
    Menu mMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_book_service);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("BUNDLE");
        mService = (Service) bundle.getSerializable("SERVICE");
        actionBar.setTitle("Chọn nơi khám");
        curFragment = new ListClinicFragment();

        Provider.getInstance().loadAllClinic(mService, new ClinicListCallback() {
            @Override
            public void onFinish() {
                loadFragment(curFragment);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        mMenu = menu;
        getMenuInflater().inflate(R.menu.book_service_memu, menu);
        return true;
    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_cancel:
                Intent intent = new Intent(BookServiceActivity.this, MainActivity.class);
                intent.putExtra("FRAGMENT", "SERVICE");
                startActivity(intent);
                break;
            case R.id.action_continue:
                switch (curFragment.getClass().toString()) {
                    case "class com.example.trancaoviet.NoodleDrug.Fragment.ListClinicFragment":
                        actionBar.setTitle("Chọn thời gian");
                        curFragment = new TimeBookFragment();
                        loadFragment(curFragment);
                        break;
                    case "class com.example.trancaoviet.NoodleDrug.Fragment.TimeBookFragment":
                        actionBar.setTitle("Chọn người bệnh");
                        curFragment = new PersionBookFragment();
                        loadFragment(curFragment);
                        break;
                    case "class com.example.trancaoviet.NoodleDrug.Fragment.PersionBookFragment":
                        actionBar.setTitle("Kiểm tra lại thông tin");
                        curFragment = new CheckInfoBookFragment();
                        loadFragment(curFragment);
                        break;
                    case "class com.example.trancaoviet.NoodleDrug.Fragment.CheckInfoBookFragment":
                        actionBar.setTitle("Hoàn tất");
                        mMenu.setGroupVisible(0,false);
                        curFragment = new FinishBookFragment();
                        loadFragment(curFragment);
                        break;
                }
                break;
            default:break;
        }

        return true;
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment!=null) {
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container,fragment)
                    .commit();
            return true;
        }
        return false;
    }

    public Service getService() {
        return mService;
    }
}
