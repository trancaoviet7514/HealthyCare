package com.example.trancaoviet.NoodleDrug.DataIO;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.example.trancaoviet.NoodleDrug.Object.Drug;
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

    public static DatabaseReference mFirebaseDataBaseRef = FirebaseDatabase.getInstance().getReference();
    public static StorageReference mFirebaseStorageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://noodledrug.appspot.com");
    private static int maxID;
    private boolean isSignIn = false;
    public static String UserName, Password;


    public Provider() {

        if(!isSignIn){
            UserName = "UN_SIGN_IN";
        }

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

    public void getAllDrug(final DrugChangeCallBack drugChangeCallBack){
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

    public void getAllDrugImage(int drugID, final DrugImageCallBack drugImageCallBack){

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

    public static void insertDrug(Drug drug){

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

