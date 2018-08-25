package com.john.www.abu.ShoppingStuffs.ShoppingActivities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import android.widget.RadioGroup;

import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import com.john.www.abu.Activitiesss.UtilitiesActivity;
import com.john.www.abu.R;
import com.john.www.abu.ShoppingStuffs.Models.ListOfShop;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class AddGoodsActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText nameof_product,priceof_prouct,minimum_number_torequest, description;
    private ImageView imageView;
    private Uri imageUrl;

    private Button submit_product;

    String radioGroupResult = " ";

    private RadioGroup radioGroup;
    private Bitmap bp;
    private StorageReference storageRef;
    private ProgressDialog progressDialog;
    public static final String DATA = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static Random RANDOM = new Random();
    private FirebaseAuth mAuth;
  private String descriptionstring,name,price,minimun,userid;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addgoods_activity);
        progressDialog = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();
        userid = mAuth.getCurrentUser().getUid();

        String mangochop = getIntent().getStringExtra("mangochop");
        TextView textView = (TextView) findViewById(R.id.mintxv);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);


        nameof_product = (EditText) findViewById(R.id.name_of_products);
        priceof_prouct = (EditText) findViewById(R.id.price_perone);
        minimum_number_torequest = (EditText) findViewById(R.id.minimumto_order);
        description = (EditText) findViewById(R.id.descriptionofproduct);

        if(mangochop != null){

            minimum_number_torequest.setVisibility(View.GONE);
            textView.setVisibility(View.GONE);

        }
        storageRef = FirebaseStorage.getInstance().getReference().child("Shopping_files");
        radioGroup = (RadioGroup) findViewById(R.id.radioGroupshop);

        imageView = (ImageView) findViewById(R.id.imageview_for_post);
        imageView.setOnClickListener(this);

        submit_product = (Button) findViewById(R.id.summit_product);
        submit_product.setOnClickListener(this);





    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.imageview_for_post:
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setMaxCropResultSize(300, 300)
                        .start(AddGoodsActivity.this);
                break;
            case R.id.summit_product:
                   // submit_product.setEnabled(false);
                progressDialog.setIndeterminate(false);
                progressDialog.setMessage("posting...");
                progressDialog.show();
                int selectedId=radioGroup.getCheckedRadioButtonId();

                switch (selectedId){
                    case R.id.for_foodonly:
                        radioGroupResult = "food";

                        break;
                    case R.id.for_clothingsonly:
                        radioGroupResult = "clothing";
                    case R.id.for_accessoriessonly:
                        radioGroupResult = "accessories";


                        break;

                        default:
                            radioGroupResult =" ";
                            break;
                }

                name = nameof_product.getText().toString();
                price = priceof_prouct.getText().toString();
                minimun = minimum_number_torequest.getText().toString();
                descriptionstring = description.getText().toString();

                uploadProduct();
                break;
        }

    }



    public String ramdomString(int len){

        StringBuilder sb = new StringBuilder(len);
        for(int i = 1; i <len; i++){
            sb.append(DATA.charAt(RANDOM.nextInt(DATA.length())));
        }
        return sb.toString();
    }
    private void uploadProduct() {

                 dotheUploading();


    }

    private void dotheUploading() {
        if (imageUrl != null && !TextUtils.isEmpty(name) && !TextUtils.isEmpty(price) && !TextUtils.isEmpty(minimun)
                && !TextUtils.isEmpty(radioGroupResult)) {
            ListOfShop listOfShop = new ListOfShop();
            listOfShop.setNameOfItem(name);
            listOfShop.setCostOfItem(price);
            listOfShop.setMinimum_for_delivery(minimun);
            listOfShop.setDescription(descriptionstring);
            listOfShop.setSellerid(userid);


            ByteArrayOutputStream boas = new ByteArrayOutputStream();
            bp.compress(Bitmap.CompressFormat.JPEG, 100, boas);
            byte[] bytes = boas.toByteArray();

            DocumentReference documentReference = FirebaseFirestore.getInstance().collection("Users").document();

            String id = documentReference.getId();

            final StorageReference ref = storageRef.child(radioGroupResult + "/" + id + ".jpg");
            UploadTask uploadTask = ref.putBytes(bytes);

            uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if (task.isSuccessful()) {

                        uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                            @Override
                            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                if (!task.isSuccessful()) {
                                    progressDialog.dismiss();
                                    throw task.getException();
                                }

                                // Continue with the task to get the download URL
                                return ref.getDownloadUrl();
                            }
                        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                if (task.isSuccessful()) {
                                    Uri downloadUri = task.getResult();
                                    // downloadUri = ref.getDownloadUrl().toString();
                                    listOfShop.setImageUrl(String.valueOf(downloadUri));



                                    FirebaseFirestore.getInstance().collection(radioGroupResult).document(id).set(listOfShop)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Map<String, Object> map = new HashMap<String, Object>();
                                                        map.put("productstars", 0);

                                                        Map<String, Object> mapid = new HashMap<>();
                                                        mapid.put(id, map);


                                                        FirebaseFirestore.getInstance().collection("Sellers").document(userid).set(mapid).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if(task.isSuccessful()) {
                                                                    progressDialog.dismiss();
                                                                    Toast.makeText(getApplicationContext(), "product has been uploaded", Toast.LENGTH_LONG).show();
                                                                    startActivity(new Intent(AddGoodsActivity.this, UtilitiesActivity.class));
                                                                    finish();
                                                                }else {
                                                                    Toast.makeText(getApplicationContext(), "please try again, unable to create user...", Toast.LENGTH_LONG).show();
                                                                }
                                                            }
                                                        });

                                                        // submit_product.setEnabled(true);
                                                    } else {
                                                        Toast.makeText(getApplicationContext(), "please try again, Uploading failed for firabase", Toast.LENGTH_LONG).show();
                                                        //  submit_product.setEnabled(true);
                                                    }
                                                }
                                            });
                                    progressDialog.dismiss();

                                } else {
                                    // Handle failures
                                    progressDialog.dismiss();
                                    // ...
                                }
                            }
                        });

                        {

                        }
                    } else
                        progressDialog.dismiss();
                    {

                    }

                }
            });


        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                imageUrl = result.getUri();
                //  mPostDisplay.setImageURI(postImageUri);

                try {
                    bp = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUrl);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                imageView.setImageBitmap(bp);
            }
        }
    }
}
