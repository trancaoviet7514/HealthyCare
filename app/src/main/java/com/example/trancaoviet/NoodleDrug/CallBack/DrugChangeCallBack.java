package com.example.trancaoviet.NoodleDrug.CallBack;

import com.example.trancaoviet.NoodleDrug.Model.Drug;
import java.util.ArrayList;

public interface DrugChangeCallBack {
    void onSuccess(ArrayList<Drug> listDrug);
    void onFailure();
}
