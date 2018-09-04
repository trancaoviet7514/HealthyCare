package com.example.trancaoviet.NoodleDrug.DataIO;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.example.trancaoviet.NoodleDrug.Object.Drug;
import com.example.trancaoviet.NoodleDrug.Object.User;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Provider {

    private DatabaseReference mFirebaseDataBaseRef = FirebaseDatabase.getInstance().getReference();
    private StorageReference mFirebaseStorageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://noodledrug.appspot.com");
    private int maxID;
    private String UserName, Password;
    private User user;
    private static Provider instance = null;

    public DatabaseReference getmFirebaseDataBaseRef() {
        return mFirebaseDataBaseRef;
    }

    public void setmFirebaseDataBaseRef(DatabaseReference mFirebaseDataBaseRef) {
        this.mFirebaseDataBaseRef = mFirebaseDataBaseRef;
    }

    public StorageReference getmFirebaseStorageRef() {
        return mFirebaseStorageRef;
    }

    public void setmFirebaseStorageRef(StorageReference mFirebaseStorageRef) {
        this.mFirebaseStorageRef = mFirebaseStorageRef;
    }

    public int getMaxID() {
        return maxID;
    }

    public void setMaxID(int maxID) {
        this.maxID = maxID;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public static synchronized Provider getInstance() {
        if(instance==null) {
            return instance = new Provider();
        } else {
            return instance;
        }
    }

    public Provider() {

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                maxID = ((Long) dataSnapshot.getValue()).intValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        mFirebaseDataBaseRef.child("max_id").addValueEventListener(valueEventListener);

    }

    public void getAllDrug(final DrugChangeCallBack drugChangeCallBack) {
            final ArrayList<Drug> listDrug = new ArrayList<>();
            ValueEventListener myValueEventListener = new ValueEventListener(){
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Drug drug = null;
                    for (DataSnapshot child : dataSnapshot.getChildren() ) {
                        drug = child.getValue(Drug.class);
                        listDrug.add(drug);
                    }
                    drugChangeCallBack.onSuccess(listDrug);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    drugChangeCallBack.onFailure();
                }
            };
            mFirebaseDataBaseRef.child("drug_list").addListenerForSingleValueEvent(myValueEventListener);
    }

    public void getAllDrugImage(int drugID, final DrugImageCallBack drugImageCallBack) {

        File localFile = null;
        try {
            localFile = File.createTempFile("images", "jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
        final File finalLocalFile = localFile;
        mFirebaseStorageRef.child("images/"+ String.valueOf(drugID) ).getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
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

    public void insertDrug(Drug drug){

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        drug.getImage().compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        mFirebaseStorageRef.child("images").child(String.valueOf(maxID)).putBytes(data);

        drug.setImage(null);
        mFirebaseDataBaseRef.child("drug_list").child(String.valueOf(maxID)).setValue(drug);
        maxID++;
        mFirebaseDataBaseRef.child("max_id").setValue(maxID);
    }

    public interface DrugChangeCallBack {
        void onSuccess(ArrayList<Drug> listDrug);
        void onFailure();
    }
    public interface DrugImageCallBack {
        void onSuccess(File file);
        void onFailure();
    }
}

