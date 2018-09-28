package com.example.trancaoviet.NoodleDrug.DataIO;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.example.trancaoviet.NoodleDrug.Activities.BookServiceActivity;
import com.example.trancaoviet.NoodleDrug.CallBack.ClinicListCallback;
import com.example.trancaoviet.NoodleDrug.CallBack.DrugChangeCallBack;
import com.example.trancaoviet.NoodleDrug.CallBack.DrugImageCallBack;
import com.example.trancaoviet.NoodleDrug.CallBack.IsSiginCallBack;
import com.example.trancaoviet.NoodleDrug.CallBack.LoginVerifyCallBack;
import com.example.trancaoviet.NoodleDrug.Fragment.HomeFragment;
import com.example.trancaoviet.NoodleDrug.Fragment.ListClinicFragment;
import com.example.trancaoviet.NoodleDrug.Fragment.ServiceFragment;
import com.example.trancaoviet.NoodleDrug.Model.Clinic;
import com.example.trancaoviet.NoodleDrug.Model.Drug;
import com.example.trancaoviet.NoodleDrug.Model.News;
import com.example.trancaoviet.NoodleDrug.Model.Service;
import com.example.trancaoviet.NoodleDrug.Model.User;
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
import java.io.IOException;
import java.util.ArrayList;

public class Provider {

    private DatabaseReference mFirebaseDataBaseRef = FirebaseDatabase.getInstance().getReference();
    private StorageReference mFirebaseStorageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://noodledrug.appspot.com");
    private int maxID;
    private User user;
    private boolean isLogin;
    private static Provider instance = null;
    private static final String PATH_TO_LIST_USER = "User";
    private static final String PATH_TO_LIST_DRUG = "drug_list";
    private static final String PATH_TO_LIST_SERVICE = "list_service";
    private static final String PATH_TO_LIST_CLINIC = "list_clinic";
    private static final String PATH_TO_LIST_NEWS = "listNews";

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public static synchronized Provider getInstance() {
        if(instance == null) {
            return instance = new Provider();
        } else {
            return instance;
        }
    }

    private Provider() {

        user = new User();
        isLogin = false;

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

        Clinic clinic = new Clinic();
        for (int i = 0; i < 10; i++) {
            clinic.setId(i);
            clinic.setName("clinic"+ i);
            clinic.setAddress("address" + i);
            mFirebaseDataBaseRef.child(PATH_TO_LIST_CLINIC).child(String.valueOf(i)).setValue(clinic);
        }


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
            mFirebaseDataBaseRef.child(PATH_TO_LIST_DRUG).addListenerForSingleValueEvent(myValueEventListener);
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

    public void checkUserIsRegister(final IsSiginCallBack isSiginCallBack, String _userName) {
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Object value = dataSnapshot.getValue();
                if(value != null) {
                    isSiginCallBack.isSigin();
                } else {
                    isSiginCallBack.notSigin();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        };

        mFirebaseDataBaseRef.child(PATH_TO_LIST_USER).child(_userName).addListenerForSingleValueEvent(valueEventListener);
    }

    public void verifyAccount(final User _user, final LoginVerifyCallBack _loginVerifyCallBack) {
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null ) {
                    _loginVerifyCallBack.isFailed();
                    return;
                }
                for(DataSnapshot child: dataSnapshot.getChildren()) {
                    if(child.getValue() != null) {
                        if (child.child("Password").getValue() != null){
                            if(child.child("Password").getValue().equals(_user.getPassword() ) == true ) {
                                _loginVerifyCallBack.isSucess();
                                return;
                            } else {
                                _loginVerifyCallBack.isFailed();
                                return;
                            }
                        }
                        else {
                            _loginVerifyCallBack.isFailed();
                            return;
                        }
                    } else {
                        _loginVerifyCallBack.isFailed();
                        return;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                _loginVerifyCallBack.isFailed();
            }
        };

        mFirebaseDataBaseRef.child(PATH_TO_LIST_USER).child(_user.getName()).addListenerForSingleValueEvent(valueEventListener);
    }

    public void registerNewUser(User user) {
        if(user == null)    return;
        String UserName = user.getName();
        UserName = UserName.substring(0, UserName.indexOf("."));
        mFirebaseDataBaseRef.child(PATH_TO_LIST_USER).child(UserName).setValue(user);
    }

    public void loadAllNews() {
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) return;
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    News news = child.getValue(News.class);
                    HomeFragment.listNews.add(news);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        mFirebaseDataBaseRef.child(PATH_TO_LIST_NEWS).addListenerForSingleValueEvent(valueEventListener);
    }

    public void loadAllServices() {
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) return;
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Service service = child.getValue(Service.class);
                    ServiceFragment.listServices.add(service);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        mFirebaseDataBaseRef.child(PATH_TO_LIST_SERVICE).addListenerForSingleValueEvent(valueEventListener);
    }

    public void loadAllClinic(final Service service, final ClinicListCallback clinicListCallback) {
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    Clinic clinic = dataSnapshot.getValue(Clinic.class);
                    ListClinicFragment.listClinic.add(clinic);

                }
                if (ListClinicFragment.listClinic.size() == service.getPharmacyList().size()) {
                    clinicListCallback.onFinish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        for (Integer clinicId: service.getPharmacyList()) {
            mFirebaseDataBaseRef.child(PATH_TO_LIST_CLINIC).child(String.valueOf(clinicId)).addListenerForSingleValueEvent(valueEventListener);
        }
    }
}

