package com.john.www.abu.Activitiesss;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import com.john.www.abu.Adapters.ChatFragmentRecycler;
import com.john.www.abu.DatabaseStuffs.sqlMessage.Manss;
import com.john.www.abu.DatabaseStuffs.sqlUsers.GroupData;
import com.john.www.abu.DatabaseStuffs.sqlUsers.UserDatabase;
import com.john.www.abu.PostStuffs.PostCreateDialog;
import com.john.www.abu.R;
import com.john.www.abu.helper.CheckConnection;
import com.john.www.abu.helper.Constants;
import com.john.www.abu.helper.Utility;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Nullable;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;

/**
 * Created by MR AGUDA on 27-Jan-18.
 */

public class CreateGroup extends AppCompatActivity implements View.OnClickListener{


    private static int GALLERYPICK = 43;
    private Button createGroup;
  private FirebaseFirestore firestore;
  private CollectionReference documentReference;
    private FirebaseAuth mAuth;
    private Bitmap imageBitmap;
    private ProgressDialog LoadingProgressBar;

    private StorageReference storageReferenceimage;
    private CircleImageView circleImageView, memberselecttion;
   private RecyclerView recyclerView;
   private ChatFragmentRecycler mAdapter;
   private TextInputEditText name_of_group;
    private Uri postImageUri;
    private Bitmap bp;
    private GroupData groupData;
    private ProgressDialog mProgressDialog;
    private String online_user_id;
    private String groupId;
    private  ArrayList<String> list = new ArrayList<>();
    private DocumentReference collection;
    ArrayList<String> adminlist;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_group);
        mProgressDialog = new ProgressDialog(this);
        groupData = new GroupData();
        mAuth = FirebaseAuth.getInstance();
        online_user_id = mAuth.getCurrentUser().getUid();

        Toolbar toolbar = (Toolbar) findViewById(R.id.group_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(" ");


        ArrayList<UserDatabase> myList = (ArrayList<UserDatabase>) getIntent().getSerializableExtra("arrayboy");
        adminlist = new ArrayList<>();
        adminlist.add(online_user_id);



            if(myList !=null){
        for(UserDatabase userDatabase :myList) {


            list.add(userDatabase.getUserid());

        }
                list.add(online_user_id);
        }else {
                Toast.makeText(this, "Please make sure members are selected", Toast.LENGTH_SHORT).show();
                finish();
            }

         recyclerView = (RecyclerView) findViewById(R.id.groupcreate_recycler);

        mAdapter = new ChatFragmentRecycler(CreateGroup.this, myList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);


        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.custom_toolbar_group, null);
        actionBar.setCustomView(view);

        createGroup = (Button) findViewById(R.id.createGroup);
        circleImageView = (CircleImageView) findViewById(R.id.circleImage_group);
        name_of_group = (TextInputEditText) findViewById(R.id.name_of_groups);
        memberselecttion = (CircleImageView) findViewById(R.id.select_members);

        memberselecttion.setOnClickListener(this);
        circleImageView.setOnClickListener(this);

        documentReference =  FirebaseFirestore.getInstance().collection("Created_groups");


        createGroup.setOnClickListener(this);



         storageReferenceimage = FirebaseStorage.getInstance().getReference().child("CreatedGroups");
        LoadingProgressBar = new ProgressDialog(CreateGroup.this);

    }


    @Override
    public void onClick(View v) {
        int y = v.getId();
        switch (y){
            case R.id.createGroup:
                CheckConnection checkConnection = new CheckConnection(getApplicationContext());
               if (checkConnection.isConnected()){
                Utility.hideKeyboard(CreateGroup.this);
                   sendPost();
               }else {
                   Toast.makeText(this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
               }
                break;
            case R.id.circleImage_group:
                CropImage.activity().setAspectRatio(1,1).setGuidelines(CropImageView.Guidelines.ON).start(CreateGroup.this);

                break;
            case R.id.select_members:
                startActivity(new Intent(CreateGroup.this, SelectedMemberOfGroup.class));
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                postImageUri = result.getUri();
                //  mPostDisplay.setImageURI(postImageUri);

                try {
                    bp = MediaStore.Images.Media.getBitmap(getContentResolver(), postImageUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                circleImageView.setImageBitmap(bp);
            }
        }



    }
    private void sendPost() {
        mProgressDialog.setMessage("Creating request...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.show();


        String text = name_of_group.getText().toString();


        if (postImageUri != null && !TextUtils.isEmpty(text)) {
            groupData.setGroup_name(text);

            FirebaseFirestore.getInstance().collection("Create_groups").document().addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                    if(e !=null){

                        Toast.makeText(CreateGroup.this, "Error"+e, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    groupId = documentSnapshot.getId();


                    File newImageFile = new File(postImageUri.getPath());
                    try {

                        bp = new Compressor(CreateGroup.this)
                                .setMaxHeight(720)
                                .setMaxWidth(720)
                                .setQuality(20)
                                .compressToBitmap(newImageFile);

                    } catch (IOException ei) {
                        ei.printStackTrace();
                    }

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] imageData = baos.toByteArray();

                    // PHOTO UPLOAD
                    final StorageReference reference = storageReferenceimage.child(groupId).child("realImage.jpg");
                    final UploadTask filePath = reference.putBytes(imageData);


                    filePath.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull final Task<UploadTask.TaskSnapshot> task) {

                            filePath.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                                @Override
                                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                    if (!task.isSuccessful()) {

                                        throw task.getException();
                                    }

                                    // Continue with the task to get the download URL
                                    return reference.getDownloadUrl();
                                }
                            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    if (task.isSuccessful()) {
                                        Uri downloadUri = task.getResult();
                                        groupData.setGroup_Image_realURL(String.valueOf(downloadUri));


                                        File newThumbFile = new File(postImageUri.getPath());
                                        try {

                                            bp = new Compressor(CreateGroup.this)
                                                    .setMaxHeight(50)
                                                    .setMaxWidth(50)
                                                    .setQuality(1)
                                                    .compressToBitmap(newThumbFile);

                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }

                                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                        bp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                                        byte[] thumbData = baos.toByteArray();


                                        final StorageReference reference1 = storageReferenceimage.child(groupId).child("thumbImage.jpg");
                                        final UploadTask uploadTask = reference1.putBytes(thumbData);


                                        uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                                if (task.isSuccessful()) {

                                                    uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                                                        @Override
                                                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                                            if (!task.isSuccessful()) {

                                                                throw task.getException();
                                                            }

                                                            // Continue with the task to get the download URL
                                                            return reference1.getDownloadUrl();
                                                        }
                                                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Uri> task) {
                                                            if (task.isSuccessful()) {
                                                                Uri downloadUri = task.getResult();
                                                                groupData.setGroup_Image_fakeURL(String.valueOf(downloadUri));

                                                                writeNewPost(groupId);


                                                            } else {
                                                                // Handle failures
                                                                mProgressDialog.dismiss();
                                                                Toast.makeText(getApplicationContext(), "unable to get Url", Toast.LENGTH_LONG).show();

                                                                // ...
                                                            }
                                                        }
                                                    });


                                                } else {
                                                    // Handle failures
                                                    mProgressDialog.dismiss();
                                                    Toast.makeText(getApplicationContext(), "unable to get Url", Toast.LENGTH_LONG).show();

                                                    // ...
                                                }
                                            }
                                        });
                                    } else {
                                        // Handle failures
                                        mProgressDialog.dismiss();
                                        Toast.makeText(getApplicationContext(), "unable to get Url", Toast.LENGTH_LONG).show();

                                        // ...
                                    }
                                }
                            });

                        }
                    });
                }


            });
        } else{
            Toast.makeText(getApplicationContext(), "please fill all the fileds", Toast.LENGTH_SHORT).show();
            mProgressDialog.dismiss();
        }
    }

    private void writeNewPost(String groupId) {
        mProgressDialog.setMessage("Creating groups...ok");


        Map<String, Object> manemap = new HashMap<>();
        manemap.put("group_name",groupData.getGroup_name() );
        manemap.put("group_Image_realURL",groupData.getGroup_Image_realURL() );
        manemap.put("group_Image_fakeURL",groupData.getGroup_Image_fakeURL() );





        Map<String, Object> mapw = new HashMap<>();
        mapw.put("Admins", adminlist);

        manemap.put("Admins", mapw);


        Map<String, Object> mapqa = new HashMap<>();
        mapqa.put("members", list);
        manemap.put("Members", list);




        documentReference.document(groupId).set(manemap).addOnCompleteListener(new OnCompleteListener<Void>() {
           @Override
           public void onComplete(@NonNull Task<Void> task) {
          if(task.isSuccessful()){

              mProgressDialog.dismiss();
              Toast.makeText(CreateGroup.this, "Group created", Toast.LENGTH_SHORT).show();


          }else{
                    mProgressDialog.dismiss();
              Toast.makeText(CreateGroup.this, "Please try again later, creating of group failed", Toast.LENGTH_SHORT).show();
          }


           }
       });

    }


}

