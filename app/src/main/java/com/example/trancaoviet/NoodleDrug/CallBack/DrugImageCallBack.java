package com.example.trancaoviet.NoodleDrug.CallBack;

import java.io.File;

public interface DrugImageCallBack {
    void onSuccess(File file);
    void onFailure();
}
