package com.example.trancaoviet.NoodleDrug.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.trancaoviet.NoodleDrug.DataIO.Provider;
import com.example.trancaoviet.NoodleDrug.Object.Drug;
import com.example.trancaoviet.NoodleDrug.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddDrugActivity extends AppCompatActivity {

    Provider provider = null;

    private static final int REQUEST_GET_PHOTO = 1;
    private static final int REQUEST_TAKE_PHOTO = 2;
    private static final int REQUEST_SCAN_TEXT_NAME = 3;
    private static final int REQUEST_SCAN_TEXT_PRICE = 4;
    private static final int REQUEST_SCAN_TEXT_COMPONENT = 5;
    private static final int REQUEST_SCAN_TEXT_USECASE = 6;
    private static final int REQUEST_SCAN_TEXT = 7;
    ImageButton btnTakePhoto, btnLoadPhoto;
    ImageView imgDrugImage;
    EditText edtDrugName, edtPrice, edtComponent, edtUseCase;
    Button btnSubmit;
    ImageButton btnDeleteDrugImage, btnScanTextName, btnScanTextPrice, btnScanTextComponent, btnScanTextUsecase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_drug);

        provider = Provider.getInstance();

        btnTakePhoto = findViewById(R.id.btn_drug_add_photo);
        btnLoadPhoto = findViewById(R.id.btn_drug_load_image);
        imgDrugImage = findViewById(R.id.img_drug_image);
        edtDrugName = findViewById(R.id.edt_drug_name);
        edtPrice = findViewById(R.id.edt_drug_price);
        edtComponent = findViewById(R.id.edt_drug_component);
        edtUseCase = findViewById(R.id.edt_drug_usecase);
        btnSubmit = findViewById(R.id.btn_submit);
        btnDeleteDrugImage = findViewById(R.id.btn_delete_image);
        btnScanTextName = findViewById(R.id.btn_scan_text_drug_name);
        btnScanTextPrice = findViewById(R.id.btn_scan_text_drug_price);
        btnScanTextComponent = findViewById(R.id.btn_scan_text_drug_component);
        btnScanTextUsecase = findViewById(R.id.btn_scan_text_drug_usecase);

        btnScanTextName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddDrugActivity.this, ScanText.class);
                intent.putExtra("CODE", "NAME");
                startActivityForResult(intent, REQUEST_SCAN_TEXT);
            }
        });
        btnScanTextPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddDrugActivity.this, ScanText.class);
                intent.putExtra("CODE", "PRICE");
                startActivityForResult(intent, REQUEST_SCAN_TEXT);
            }
        });
        btnScanTextComponent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddDrugActivity.this, ScanText.class);
                intent.putExtra("CODE", "COMPONENT");
                startActivityForResult(intent, REQUEST_SCAN_TEXT);
            }
        });
        btnScanTextUsecase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddDrugActivity.this, ScanText.class);
                intent.putExtra("CODE", "USECASE");
                startActivityForResult(intent, REQUEST_SCAN_TEXT);
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String drugName = edtDrugName.getText().toString();
                String drugPrice = edtPrice.getText().toString();
                String useCase = edtUseCase.getText().toString();
                String component = edtComponent.getText().toString();
                File imgFile = new  File(mCurrentPhotoPath);
                Bitmap myBitmap = null;
                if(imgFile.exists()) {
                   myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                }
                Drug newDrug = new Drug(drugName, component, useCase, drugPrice, myBitmap);
                provider.insertDrug(newDrug);
            }
        });

        btnDeleteDrugImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgDrugImage.setVisibility(View.GONE);
                btnDeleteDrugImage.setVisibility(View.GONE);
            }
        });

        btnLoadPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, REQUEST_GET_PHOTO);
            }
        });

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
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {}

            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.trancaoviet.NoodleDrug",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case REQUEST_GET_PHOTO:
                try {
                    final Uri imageUri = data.getData();
                    final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    imgDrugImage.setImageBitmap(selectedImage);
                    btnDeleteDrugImage.setVisibility(View.VISIBLE);
                } catch (FileNotFoundException e) {}

                break;
            case REQUEST_TAKE_PHOTO:
//                Bundle extras = data.getExtras();
//                Bitmap imageBitmap = (Bitmap) extras.get("data");
//                imgDrugImage.setImageBitmap(imageBitmap);
//                btnDeleteDrugImage.setVisibility(View.VISIBLE);
                File imgFile = new  File(mCurrentPhotoPath);
                if(imgFile.exists()){
                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    Bitmap ThumbImage = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(mCurrentPhotoPath),
                            myBitmap.getWidth()/4, myBitmap.getHeight()/4);
                    imgDrugImage.setImageBitmap(ThumbImage);

                }
                break;
            case REQUEST_SCAN_TEXT_NAME:
                edtDrugName.setText(data.getStringExtra("VALUE"));
                break;
            case REQUEST_SCAN_TEXT_PRICE:
                edtPrice.setText(data.getStringExtra("VALUE"));
                break;
            case REQUEST_SCAN_TEXT_COMPONENT:
                edtComponent.setText(data.getStringExtra("VALUE"));
                break;
            case REQUEST_SCAN_TEXT_USECASE:
                edtUseCase.setText(data.getStringExtra("VALUE"));
                break;
        }


    }

    String mCurrentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }
}
