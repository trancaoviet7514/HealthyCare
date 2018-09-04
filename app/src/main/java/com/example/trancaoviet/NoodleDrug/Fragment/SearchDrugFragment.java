package com.example.trancaoviet.NoodleDrug.Fragment;

import android.app.Fragment;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import com.example.trancaoviet.NoodleDrug.Adapters.DrugAdapter;
import com.example.trancaoviet.NoodleDrug.DataIO.Provider;
import com.example.trancaoviet.NoodleDrug.Object.Drug;
import com.example.trancaoviet.NoodleDrug.R;

import java.io.File;
import java.util.ArrayList;

public class SearchDrugFragment extends Fragment {

    public static Provider provider;
    public static RecyclerView rcvDrug;
    public static ArrayList<Drug> listDrug;
    public static DrugAdapter drugAdapter;
    private EditText edtSearchDrug;
    private ImageButton btnSearchDrug;
    private View mRootView;
    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_drug,container,false);
        mRootView = view;
        addControls();
        addEvents();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = getActivity();
        provider = new Provider();

    }

    private void addControls() {

        edtSearchDrug = mRootView.findViewById(R.id.edtSearch_drug);
        btnSearchDrug = mRootView.findViewById(R.id.btnSearh_drug);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mContext,linearLayoutManager.getOrientation());

        listDrug = new ArrayList<>();
        drugAdapter = new DrugAdapter(mContext,listDrug);

        rcvDrug = mRootView.findViewById(R.id.rcvTask);
        rcvDrug.addItemDecoration(dividerItemDecoration);
        rcvDrug.setLayoutManager(linearLayoutManager);
        rcvDrug.setAdapter(drugAdapter);
    }

    private void addEvents() {
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
}
