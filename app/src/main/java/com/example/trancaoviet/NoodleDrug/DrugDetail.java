package com.example.trancaoviet.NoodleDrug;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class DrugDetail extends AppCompatActivity {

    public static RecyclerView rcvDrugStore;
    public static ArrayList<DrugStore> listDrugStore;
    public static DrugStoreAdapter drugAdapterStore;

    ImageView imgDrugImage;
    TextView txtDrugName, txtComponent, txtPrice, txtUsecase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drug_detail);

        imgDrugImage = findViewById(R.id.img_drug_image);
        txtComponent = findViewById(R.id.txt_drug_component);
        txtPrice = findViewById(R.id.txt_drug_price);
        txtUsecase = findViewById(R.id.txt_drug_usecase);
        txtDrugName = findViewById(R.id.txt_drug_name);

        imgDrugImage.setImageBitmap(DrugAdapter.DrugSelected.getImage());
        txtComponent.setText(DrugAdapter.DrugSelected.getComponent());
        txtPrice.setText(DrugAdapter.DrugSelected.getPrice());
        txtUsecase.setText(DrugAdapter.DrugSelected.getUseCase());
        txtDrugName.setText(DrugAdapter.DrugSelected.getName());

        listDrugStore = new ArrayList<>();
        listDrugStore.add(new DrugStore("Hoa Hồng","123 Nguyễn Văn Linh, phường 4, quận 7"));
        listDrugStore.add(new DrugStore("Bạch Tuyết","34/5 Nguyễn Thị Nghĩa, phường Phạm Ngũ Lão, quận 1"));
        listDrugStore.add(new DrugStore("Thúy Hằng","64 Trần Bình Trọng, phường 23, quận 5"));
        listDrugStore.add(new DrugStore("Kim Tuyến","91/3 Lý Thái Tổ, phường 12, quận 10"));
        listDrugStore.add(new DrugStore("Bà Tiên","434 Bạch Đằng, phường 14, quận Bình Thạnh"));
        listDrugStore.add(new DrugStore("Hoa Hồng","123 Nguyễn Văn Linh, phường 4, quận 7"));
        listDrugStore.add(new DrugStore("Bạch Tuyết","34/5 Nguyễn Thị Nghĩa, phường Phạm Ngũ Lão, quận 1"));
        listDrugStore.add(new DrugStore("Thúy Hằng","64 Trần Bình Trọng, phường 23, quận 5"));
        listDrugStore.add(new DrugStore("Kim Tuyến","91/3 Lý Thái Tổ, phường 12, quận 10"));
        listDrugStore.add(new DrugStore("Bà Tiên","434 Bạch Đằng, phường 14, quận Bình Thạnh"));
        listDrugStore.add(new DrugStore("Hoa Hồng","123 Nguyễn Văn Linh, phường 4, quận 7"));
        listDrugStore.add(new DrugStore("Bạch Tuyết","34/5 Nguyễn Thị Nghĩa, phường Phạm Ngũ Lão, quận 1"));
        listDrugStore.add(new DrugStore("Thúy Hằng","64 Trần Bình Trọng, phường 23, quận 5"));
        listDrugStore.add(new DrugStore("Kim Tuyến","91/3 Lý Thái Tổ, phường 12, quận 10"));
        listDrugStore.add(new DrugStore("Bà Tiên","434 Bạch Đằng, phường 14, quận Bình Thạnh"));

        drugAdapterStore = new DrugStoreAdapter(this,listDrugStore);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,linearLayoutManager.getOrientation());
        rcvDrugStore = findViewById(R.id.rcv_drug_store);
        rcvDrugStore.addItemDecoration(dividerItemDecoration);
        rcvDrugStore.setLayoutManager(linearLayoutManager);
        rcvDrugStore.setAdapter(drugAdapterStore);


    }
}
