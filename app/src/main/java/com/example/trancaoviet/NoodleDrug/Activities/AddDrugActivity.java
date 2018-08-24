package com.example.trancaoviet.NoodleDrug.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.trancaoviet.NoodleDrug.R;

public class AddDrugActivity extends AppCompatActivity {

    ImageButton btnTakePhoto, btnLoadPhoto;
    ImageView imgDrugImage;
    EditText edtDrugName, edtPrice, edtComponent, edtUseCase;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_drug);

        btnTakePhoto = findViewById(R.id.btn_drug_add_photo);
        btnLoadPhoto = findViewById(R.id.btn_drug_load_image);
        imgDrugImage = findViewById(R.id.img_drug_image);
        edtDrugName = findViewById(R.id.edt_drug_name);
        edtPrice = findViewById(R.id.edt_drug_price);
        edtComponent = findViewById(R.id.edt_drug_component);
        edtUseCase = findViewById(R.id.edt_drug_component);
        btnSubmit = findViewById(R.id.btn_submit);

        btnTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, 1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bundle extras = data.getExtras();
        Bitmap imageBitmap = (Bitmap) extras.get("data");
        imgDrugImage.setImageBitmap(imageBitmap);
    }
}
