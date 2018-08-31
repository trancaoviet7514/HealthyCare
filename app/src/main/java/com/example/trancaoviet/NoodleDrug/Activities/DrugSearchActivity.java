package com.example.trancaoviet.NoodleDrug.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.trancaoviet.NoodleDrug.Object.Drug;
import com.example.trancaoviet.NoodleDrug.Adapters.DrugAdapter;
import com.example.trancaoviet.NoodleDrug.DataIO.Provider;
import com.example.trancaoviet.NoodleDrug.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;

public class DrugSearchActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static Provider provider;
    public static boolean isLogin = false;

    public static RecyclerView rcvDrug;
    public static ArrayList<Drug> listDrug;
    public static DrugAdapter drugAdapter;

    private FloatingActionButton fab;

    private EditText edtSearchDrug;
    private ImageButton btnSearchDrug;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(!isLogin){
            Provider.UserName = "LOCAL_USER";
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        provider = new Provider();


        addControls();
        addEvents();


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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

        } else if(id == R.id.nav_add_drug) {
            startActivity(new Intent (this, AddDrugActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void addControls() {

        edtSearchDrug = findViewById(R.id.edtSearch_drug);
        btnSearchDrug = findViewById(R.id.btnSearh_drug);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,linearLayoutManager.getOrientation());

        listDrug = new ArrayList<>();
        drugAdapter = new DrugAdapter(DrugSearchActivity.this,listDrug);

        rcvDrug = (RecyclerView) findViewById(R.id.rcvTask);
        rcvDrug.addItemDecoration(dividerItemDecoration);
        rcvDrug.setLayoutManager(linearLayoutManager);
        rcvDrug.setAdapter(drugAdapter);
    }

    private void addEvents() {

        setEvent_fabButtonClick();
        setEvent_edtSearchDrug();
        setEvent_rcvItemClick();
    }

    private void setEvent_rcvItemClick() {
        rcvDrug.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
    }

    private void setEvent_edtSearchDrug() {
        edtSearchDrug.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                final String drugNameSearch = edtSearchDrug.getText().toString().toLowerCase();
                provider.getAllDrug(new Provider.DrugChangeCallBack() {
                    @Override
                    public void onSuccess(final ArrayList<Drug> _listDrug) {
                        listDrug.clear();
                        for(int i = 0; i < _listDrug.size();i++){
                            int j = 0;
                            String drug_name = _listDrug.get(i).getName().toLowerCase();
                            for(; j < drugNameSearch.length(); j++){
                                if(drug_name.indexOf( drugNameSearch.charAt(j) ) == -1 )
                                break;
                            }
                            if(j == drugNameSearch.length()){
                                final Drug drug = _listDrug.get(i);
                                provider.getAllDrugImage(drug.getId(), new Provider.DrugImageCallBack() {
                                    @Override
                                    public void onSuccess(File file) {
                                        drug.setImage(BitmapFactory.decodeFile(file.getPath()));
                                        listDrug.add( drug );
                                        drugAdapter.notifyDataSetChanged();
                                    }

                                    @Override
                                    public void onFailure() {
                                        listDrug.add( drug );
                                        drugAdapter.notifyDataSetChanged();
                                    }
                                });
                            }
                        }

                    }

                    @Override
                    public void onFailure() {

                    }
                });
            }
        });
    }

    private void setEvent_fabButtonClick(){

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
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
