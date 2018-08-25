package com.john.www.abu.PostStuffs;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresPermission;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import com.john.www.abu.Activitiesss.FaceOfAbuActivity;
import com.john.www.abu.Activitiesss.PostooActivity;

import com.john.www.abu.PostStuffs.models.PostModel;
import com.john.www.abu.R;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import id.zelory.compressor.Compressor;

/**
 * Created by brad on 2017/02/05.
 */

public class PostCreateDialog extends AppCompatActivity implements View.OnClickListener {
    private static final int RC_PHOTO_PICKER = 1;
    private PostModel mPost;
    private ProgressDialog mProgressDialog;
    private Uri postImageUri;
    private ImageView mPostDisplay;
    private View mRootView;
    private Bitmap imageBitmap;
    private byte[] byteArray;
    private StorageReference storageReferenceimage;
    private String postId;
    private Bitmap compressedImageFile;

    FirebaseAuth mAuth;
    private String userId;
    private Bitmap bp;
    String value;

    private FirebaseFirestore firebaseFirestore;
    private String email;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_post_dialog);
        mPost = new PostModel();
        mProgressDialog = new ProgressDialog(PostCreateDialog.this);

        value = getIntent().getStringExtra("just");
        if (value != null) {
            if (value.contains("Artists")) {
                value = "Artists";
            } else {
                value = "Gists";
            }
        } else {
            value = "posts";
        }


        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();


        firebaseFirestore = FirebaseFirestore.getInstance();


        email = mAuth.getCurrentUser().getEmail();
        mPostDisplay = (ImageView) findViewById(R.id.post_dialog_display);
        findViewById(R.id.post_dialog_send_imageview).setOnClickListener(this);
        findViewById(R.id.post_dialog_select_imageview).setOnClickListener(this);
        storageReferenceimage = FirebaseStorage.getInstance().getReference().child("postImage").child(userId + "_" + email);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.post_dialog_send_imageview:
                sendPost();
                break;
            case R.id.post_dialog_select_imageview:
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setMinCropResultSize(512, 512)
                        .setAspectRatio(1, 1)
                        .start(PostCreateDialog.this);
                break;
        }
    }

    private void sendPost() {
        mProgressDialog.setMessage("Sending post...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.show();
        //  mdatabase.collection("users").document(userId);


        DocumentReference documentReference = FirebaseFirestore.getInstance().collection(value).document();
            postId = documentReference.getId();
        TextView postDialogTextView = (TextView) findViewById(R.id.post_dialog_edittext);
        String text = postDialogTextView.getText().toString();
       mPost.setUserid(userId);
        mPost.setNumComments(0);
        mPost.setNumLikes(0);
        mPost.setType("picture");
       mPost.setTimeCreated(System.currentTimeMillis());
        mPost.setPostId(postId);
        if (!TextUtils.isEmpty(text)) {
            mPost.setPostText(text);
        }
        if (postImageUri != null) {


            final String randomName = UUID.randomUUID().toString();

            // PHOTO UPLOAD
            File newImageFile = new File(postImageUri.getPath());
            try {

                compressedImageFile = new Compressor(PostCreateDialog.this)
                        .setMaxHeight(720)
                        .setMaxWidth(720)
                        .setQuality(20)
                        .compressToBitmap(newImageFile);

            } catch (IOException e) {
                e.printStackTrace();
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            compressedImageFile.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] imageData = baos.toByteArray();

            // PHOTO UPLOAD
            final StorageReference reference = storageReferenceimage.child("realImage" + postId + ".jpg");
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
                                mPost.setPostImageUrl(String.valueOf(downloadUri));


                                File newThumbFile = new File(postImageUri.getPath());
                                try {

                                    compressedImageFile = new Compressor(PostCreateDialog.this)
                                            .setMaxHeight(50)
                                            .setMaxWidth(50)
                                            .setQuality(1)
                                            .compressToBitmap(newThumbFile);

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                compressedImageFile.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                                byte[] thumbData = baos.toByteArray();


                                final StorageReference reference1 = storageReferenceimage.child("thumbImage" + postId + ".jpg");
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
                                                        mPost.setThumbIamgeUri(String.valueOf(downloadUri));

                                                        writeNewPost();


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
        } else {
            writeNewPost();
            mProgressDialog.dismiss();
        }


    }





    private void writeNewPost() {
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(  postId, mPost);
        Map<String, Object> idmap = new HashMap<>();
        idmap.put(  postId, true);

        WriteBatch batch = firebaseFirestore.batch();

        DocumentReference nycRef = firebaseFirestore.collection(value).document(postId);
        batch.set(nycRef, mPost);


        DocumentReference laRef = firebaseFirestore.collection("user_posts").document(userId);
        batch.set(laRef,idmap );

        batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                mProgressDialog.cancel();

                if(value.contains("Artists") || value.contains("Gists")){
                    Toast.makeText(PostCreateDialog.this, "Post was added", Toast.LENGTH_LONG).show();
                    Intent mainIntent = new Intent(PostCreateDialog.this, FaceOfAbuActivity.class);
                    startActivity(mainIntent);
                    finish();
                }else {

                    Toast.makeText(PostCreateDialog.this, "Post was added", Toast.LENGTH_LONG).show();
                    Intent mainIntent = new Intent(PostCreateDialog.this, PostooActivity.class);
                    startActivity(mainIntent);
                    finish();
                }
            }
        });






    }


    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/jpeg");
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        startActivityForResult(Intent.createChooser(intent, "Complete action using"), RC_PHOTO_PICKER);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
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

                mPostDisplay.setImageBitmap(bp);
            }
        }
    }


}
