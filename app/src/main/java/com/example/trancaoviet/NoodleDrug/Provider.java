package com.example.trancaoviet.NoodleDrug;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

public class Provider {

    public static DatabaseReference mDataBase = FirebaseDatabase.getInstance().getReference();
    private ArrayList<Drug> listDrug = new ArrayList<>();
    private int maxID;
    private boolean isSignIn = false;
    public static String UserName, Password;

    public Provider() {

        if(!isSignIn){
            UserName = "UN_SIGN_IN";
        }

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if ( dataSnapshot == null ) return;
                maxID = ((Long) dataSnapshot.getValue()).intValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        mDataBase.child("MaxID").addValueEventListener(valueEventListener);
    }

    public void getAllDrug(final DrugChangeCallBack drugChangeCallBack){

            ValueEventListener myValueEventListener = new ValueEventListener(){
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if ( dataSnapshot == null ) return;
                    listDrug.clear();
                    for (DataSnapshot child : dataSnapshot.getChildren()) {

                        Drug drug = child.getValue(Drug.class);
                        listDrug.add(drug);
                    }
                    drugChangeCallBack.onFinish(listDrug);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            };
            mDataBase.child("drug_list").addListenerForSingleValueEvent(myValueEventListener);
    }

    public void insertDrug(Drug drug) {

        drug.setId(maxID);
        mDataBase.child("User").child(UserName).child("listDrug").child ( String.valueOf ( maxID ) ).setValue ( drug );
        mDataBase.child("User").child(UserName).child("MaxID").setValue(new Integer ( ++maxID ) );

    }

    public boolean deleteTask(int id) {

        mDataBase.child("User").child(UserName).child("listDrug").child( String.valueOf(id) ).removeValue();

        return true;

    }

    interface DrugChangeCallBack {
        void onFinish(ArrayList<Drug> listDrug);
    }

    public ArrayList<Drug> getListDrug(final String drugName){

        getAllDrug(new DrugChangeCallBack() {
            @Override
            public void onFinish(ArrayList<Drug> _listDrug) {
                for(int i = 0; i < _listDrug.size();i++){
                    if(listDrug.get(i).getName().equals(drugName)){
                       listDrug.add(_listDrug.get(i));
                    }
                }
            }
        });

        return listDrug;
    }
}

