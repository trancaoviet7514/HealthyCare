package com.example.trancaoviet.NoodleDrug.Activities;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.trancaoviet.NoodleDrug.Fragment.ChatFragment;
import com.example.trancaoviet.NoodleDrug.Fragment.HomeFragment;
import com.example.trancaoviet.NoodleDrug.Fragment.ProfileFragment;
import com.example.trancaoviet.NoodleDrug.Fragment.ServiceFragment;
import com.example.trancaoviet.NoodleDrug.Fragment.SocialFragment;
import com.example.trancaoviet.NoodleDrug.Helper.BottomNavigationViewHelper;
import com.example.trancaoviet.NoodleDrug.R;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView bottomNavigationView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

//        Map bottom navigation view and add Listener
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        setBottomNavigationViewListener();

        bottomNavigationView.setSelectedItemId(R.id.action_home);
    }

    private void setBottomNavigationViewListener(){
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int idItem = item.getItemId();
                Fragment fragment = null;
                switch (idItem) {
                    case R.id.action_chat:
                        toolbar.setTitle("Chat");
                        fragment = new ChatFragment();
                        break;
                    case R.id.action_social:
                        toolbar.setTitle("Social");
                        fragment = new SocialFragment();
                        break;
                    case R.id.action_home:
                        toolbar.setTitle("Home");
                        fragment = new HomeFragment();
                        break;
                    case R.id.action_service:
                        toolbar.setTitle("Service");
                        fragment = new ServiceFragment();
                        break;
                    case R.id.action_profile:
                        toolbar.setTitle("Profile");
                        fragment = new ProfileFragment();
                        break;
                }
                return loadFragment(fragment);
            }
        });
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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_search_drug) {
        } else if (id == R.id.nav_search_sick) {
        } else if (id == R.id.nav_find_doctor) {
        } else if (id == R.id.nav_Language) {

            final Dialog ThemeDialog = new Dialog(this);
            ThemeDialog.setContentView(R.layout.dialog_language);
            TextView btnSubmit, btnCancel;
            RadioGroup rgLanguage;
            rgLanguage = ThemeDialog.findViewById(R.id.rgLanguage);
            btnSubmit = ThemeDialog.findViewById(R.id.btnOk);
            btnCancel = ThemeDialog.findViewById(R.id.btnCancel);
            final String[] languageChoose = new String[1];
            languageChoose[0] = "en-US";
            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    restartInLocale(new Locale(languageChoose[0]));
                    ThemeDialog.dismiss();
                }
            });

            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ThemeDialog.dismiss();
                }
            });

            rgLanguage.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    switch (i){
                        case R.id.english:
                            languageChoose[0] = "en-US";
                            break;
                        case R.id.vietnamese:
                            languageChoose[0] = "vi-VN";
                            break;
                    }
                }
            });
            ThemeDialog.show();

        } else if (id == R.id.nav_theme) {

            final Dialog ThemeDialog = new Dialog(this);
            ThemeDialog.setContentView(R.layout.dialog_theme);
            TextView btnSubmit, btnCancel;
            RadioGroup rgColor;
            rgColor = ThemeDialog.findViewById(R.id.rgLanguage);
            btnSubmit = ThemeDialog.findViewById(R.id.btnOk);
            btnCancel = ThemeDialog.findViewById(R.id.btnCancel);
            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    ThemeDialog.dismiss();
                }
            });

            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ThemeDialog.dismiss();
                }
            });

            rgColor.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    switch (i){
                        case R.id.Red:
                           // themeColor = getResources().getColor(R.color.colorAccent);
                            break;
                        case R.id.Green:
                           // themeColor = getResources().getColor(R.color.colorPrimary);
                            break;
                        case R.id.Yellow:
                           // themeColor = getResources().getColor(R.color.colorAccent);
                            break;
                    }
                }
            });
            ThemeDialog.show();


        } else if (id == R.id.nav_logOut) {

            startActivity(new Intent (this, LoginActivity.class));

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void restartInLocale(Locale locale) {
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        Resources resources = getResources();
        resources.updateConfiguration(config, null);
        recreate();
    }


}
