package com.example.trancaoviet.NoodleDrug.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.trancaoviet.NoodleDrug.R;

import java.util.Locale;

public class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    void initNavigation(String title){
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
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

            startActivity(new Intent(this, LoginActivity.class));

        } else if(id == R.id.nav_add_drug) {
            startActivity(new Intent (this, AddDrugActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return false;
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
