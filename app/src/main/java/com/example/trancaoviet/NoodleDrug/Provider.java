package com.example.trancaoviet.NoodleDrug;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Provider {

    public static DatabaseReference mDataBase = FirebaseDatabase.getInstance().getReference();
    public static StorageReference mDataBase_Media= FirebaseStorage.getInstance().getReferenceFromUrl("gs://noodledrug.appspot.com");
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
                    drugChangeCallBack.onSuccess(listDrug);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            };
            mDataBase.child("drug_list").addListenerForSingleValueEvent(myValueEventListener);
    }

    public void getAllDrugImage(String drugName, final DrugImageCallBack drugImageCallBack){

        File localFile = null;
        try {
            localFile = File.createTempFile("images", "jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
        final File finalLocalFile = localFile;
        mDataBase_Media.child("images/"+ drugName + ".jpg").getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                drugImageCallBack.onSuccess(finalLocalFile);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                drugImageCallBack.onFailure();
            }
        });
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
        void onSuccess(ArrayList<Drug> listDrug);
        void onFailure();
    }
    interface DrugImageCallBack {
        void onSuccess(File file);
        void onFailure();
    }

    public ArrayList<Drug> getListDrug(final String drugName){

        getAllDrug(new DrugChangeCallBack() {
            @Override
            public void onSuccess(ArrayList<Drug> _listDrug) {
                for(int i = 0; i < _listDrug.size();i++){
                    if(listDrug.get(i).getName().equals(drugName)){
                       listDrug.add(_listDrug.get(i));
                    }
                }
            }

            @Override
            public void onFailure() {

            }
        });

        return listDrug;
    }
}

