package com.john.www.abu.Activitiesss;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.SupportActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import com.john.www.abu.DatabaseStuffs.sqlUsers.UserDatabase;

import com.john.www.abu.GlideApp;

import com.john.www.abu.R;
import com.john.www.abu.Rgistration.SignActivity;
import com.john.www.abu.helper.Constants;
import com.john.www.abu.helper.Utility;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;


import javax.annotation.Nullable;

import id.zelory.compressor.Compressor;

/**
 * Created by MR AGUDA on 15-Nov-17.
 */

public class ProfileActivity extends AppCompatActivity implements  View.OnClickListener{

    private static final int REQUEST_CAMERA = 45;
    private ImageView profileimage;
    private TextView profilestatus;
    private TextView profilename;

    private Button changeimage;
    private Button changestatus;
    private static int  GALLERYPICK =1;
     private String image;


    private com.google.firebase.auth.FirebaseAuth mAuth;
    private StorageReference storageReferenceimage;
    private StorageReference storageReference_cut;
    private ProgressDialog LoadingProgressBar;


    private Bitmap imageBitmap = null;
    private FirebaseFirestore firestore;
    private CollectionReference collectionReference;
    private String userChoosenTask;
    private Bitmap compressedImageFile;
    private Uri resultUrl = null;
    UserDatabase userDatabase;

    private String downloadUrlReal = " ";
    private String downloadUrlthumb=" ";
    private String online_user_id;

    private TextView  favourites_hobbies, relationship_statud, best_food,
            what_i_like_in_either, lovedoid_but_nasty, knows_u_bests, love_or_money, apologize_permission,
            type_of_music,most_loved_celebrity, worst_habbit, favourite_sport, chores_hate,
            things_ihate_opposite_sex,things_idoto_achieve_goal;
   private Button followers, following, models;
    private ImageView iamgeview;
    private LinearLayout linear_name_status;

    private DocumentReference docref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.profile_activity);



initTextviews();

        mAuth = FirebaseAuth.getInstance();
         online_user_id = mAuth.getCurrentUser().getUid();
        Toolbar mToobar = (Toolbar) findViewById(R.id.toolbarpp);
        setSupportActionBar(mToobar);

        getSupportActionBar().setTitle(" ");


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        userDatabase = new UserDatabase();
        firestore = FirebaseFirestore.getInstance();



        collectionReference = firestore.collection(Constants.USERS);
        docref = firestore.document(Constants.USERS+"/"+online_user_id);




        storageReferenceimage = FirebaseStorage.getInstance().getReference().child("profile_images");
        profileimage = (ImageView) findViewById(R.id.imageview_profile11);
        profilename = (TextView) findViewById(R.id.myusername);
        profilestatus = (TextView) findViewById(R.id.mystatus);
       iamgeview = (ImageView) findViewById(R.id.imageview_profile11);
        changestatus = (Button) findViewById(R.id.change_status);

        LoadingProgressBar = new ProgressDialog(this);


        iamgeview.setOnClickListener(this);
       changestatus.setOnClickListener(this);



    }

  private void   initTextviews(){

        linear_name_status = (LinearLayout) findViewById(R.id.layout_for_name_status);
        linear_name_status.setOnClickListener(this);
        followers = (Button) findViewById(R.id.followers);
        following = (Button) findViewById(R.id.following);
        models = (Button) findViewById(R.id.models);
        followers.setOnClickListener(this);
        following.setOnClickListener(this);
        models.setOnClickListener(this);


        favourites_hobbies = (TextView) findViewById(R.id.whatilikemostreal);
      relationship_statud = (TextView) findViewById(R.id.relationshipreal);
      best_food = (TextView) findViewById(R.id.foodreal);
      what_i_like_in_either = (TextView) findViewById(R.id.maleorfemalereal);
      lovedoid_but_nasty = (TextView) findViewById(R.id.nastylovereal);
      knows_u_bests = (TextView) findViewById(R.id.whoknowsreal);
      love_or_money = (TextView) findViewById(R.id.moneyreal);
      apologize_permission = (TextView) findViewById(R.id.apologizereal);
      type_of_music = (TextView) findViewById(R.id.musicreal);
      most_loved_celebrity = (TextView) findViewById(R.id.celebrityreal);
      worst_habbit = (TextView) findViewById(R.id.habbitreal);
      favourite_sport = (TextView) findViewById(R.id.sportreal);
      chores_hate = (TextView) findViewById(R.id.tasktodoreal);
      things_ihate_opposite_sex = (TextView) findViewById(R.id.hateinoppostereal);
      things_idoto_achieve_goal = (TextView) findViewById(R.id.things_havebeen_doing);




    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser == null){

            Intent intent = new Intent(getApplicationContext(), SignActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }else {
            collectionReference.document(online_user_id).update(Constants.ONLINE,"true");

        }

        DocumentReference documentReference = firestore.collection("Users").document(online_user_id);
           documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
               @Override
               public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                   Map<String, Object> packs = (Map<String, Object>) documentSnapshot.get("Profile");

                    if(packs != null) {
                        summitthis(packs);
                    }





               }
           });
        FirebaseFirestore.getInstance().collection(Constants.USERS).document(online_user_id)
                .addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                String name = documentSnapshot.getString(Constants.PROFILENAME);
                String status = documentSnapshot.getString(Constants.PROFILESTATUS);
                image = documentSnapshot.getString(Constants.PROFILEIMAGE);


                profilename.setText(name);
                profilestatus.setText(status);
                if(!image.equals(Constants.DEFUALTIMAGE)) {
                    GlideApp.with(getApplicationContext()).load(image).placeholder(R.drawable.facebook_avatar).diskCacheStrategy(DiskCacheStrategy.ALL).into(profileimage);


                }
            }
        });

        GlideApp.with(getApplicationContext()).load(image).placeholder(R.drawable.facebook_avatar).diskCacheStrategy(DiskCacheStrategy.ALL).into(profileimage);


    }

    private void summitthis(Map<String, Object> packs) {

        String favourites =(String) packs.get("favourite_hobbies");
        String relationship =(String) packs.get("relationship");
        String best_foode = (String) packs.get("best_food");
        String like_most = (String) packs.get("like_most");
        String love_nasty = (String) packs.get("love_nasty");
        String kowns_u_best =(String) packs.get("Kowns_u_best");
        String love_money = (String) packs.get("love_money");
        String apology_permission =(String) packs.get("apology_permission");
        String type_music = (String) packs.get("type_music");
        String most_celebrity =(String) packs.get("most_celebrity");
        String worsts_habbit = (String) packs.get("worsts_habbit");
        String sport_favourite = (String) packs.get("sport_favourite");
        String chores_hates = (String) packs.get("chores_hate");
        String hate_opp_sex = (String) packs.get("hate_opp_sex");
        String thingsdoing_goal = (String) packs.get("thingsdoing_goal");
        if(!TextUtils.isEmpty(relationship)) {
            relationship_statud.setText(relationship);
        }
        if(!TextUtils.isEmpty(favourites)) {
            favourites_hobbies.setText(favourites);
        }
        if(!TextUtils.isEmpty(kowns_u_best)) {
            knows_u_bests.setText(kowns_u_best);
        }

        if(!TextUtils.isEmpty(worsts_habbit)) {
            worst_habbit.setText(worsts_habbit);
        }
        if(!TextUtils.isEmpty(thingsdoing_goal)) {
            things_idoto_achieve_goal.setText(thingsdoing_goal);
        }if(!TextUtils.isEmpty(hate_opp_sex)) {
            things_ihate_opposite_sex.setText(hate_opp_sex);
        }if(!TextUtils.isEmpty(chores_hates)) {
            chores_hate.setText(chores_hates);
        }if(!TextUtils.isEmpty(sport_favourite)) {
            favourite_sport.setText(sport_favourite);
        }if(!TextUtils.isEmpty(most_celebrity)) {
            most_loved_celebrity.setText(most_celebrity);
        }if(!TextUtils.isEmpty(love_money)) {
            love_or_money.setText(love_money);
        }
        if(!TextUtils.isEmpty(love_nasty)) {
            lovedoid_but_nasty.setText(love_nasty);
        }if(!TextUtils.isEmpty(apology_permission)) {
            apologize_permission.setText(apology_permission);
        }
        if(!TextUtils.isEmpty(type_music)) {
            type_of_music.setText(type_music);
        }if(!TextUtils.isEmpty(like_most)) {
            what_i_like_in_either.setText(like_most);
        }if(!TextUtils.isEmpty(best_foode)) {
            best_food.setText(best_foode);
        }


    }


    @Override
    protected void onStop() {
        super.onStop();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                LoadingProgressBar.setTitle("Uploading");
                LoadingProgressBar.setMessage("Please wait while we upload your profile picture");
                LoadingProgressBar.setIndeterminate(false);
                LoadingProgressBar.show();
                 resultUrl = result.getUri();
                if(resultUrl != null) {

                    File image_filepath = new File(resultUrl.getPath());
                    long length = image_filepath.length() / 1024 *100;

                    Toast.makeText(this, "size"+length, Toast.LENGTH_LONG).show();

                    String user_id = mAuth.getCurrentUser().getUid();

                    try {
                        imageBitmap = new Compressor(ProfileActivity.this).setMaxWidth(200).setMaxHeight(200).setQuality(100).
                                compressToBitmap(image_filepath);

                        //  ImageCompression imageCompression = new ImageCompression(getApplicationContext());
                        //   imageCompression.compressImage(String.valueOf(resultUrl));


                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                    ByteArrayOutputStream baos = new ByteArrayOutputStream();

                    imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    final byte[] byteArray = baos.toByteArray();


                    final StorageReference filepath_cut = storageReferenceimage.child(online_user_id).child("profilerealimage" + ".jpg");


                    UploadTask uploadTask = filepath_cut.putBytes(byteArray);


                    uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task_cut) {

                            if (task_cut.isSuccessful()) {


                                uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                                    @Override
                                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                        if (!task.isSuccessful()) {

                                            throw task.getException();
                                        }

                                        // Continue with the task to get the download URL
                                        return filepath_cut.getDownloadUrl();
                                    }
                                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Uri> task) {
                                        if (task.isSuccessful()) {
                                            Uri downloadUri = task.getResult();

                                         downloadUrlReal = String.valueOf(downloadUri);

                                            File newThumbFile = new File(resultUrl.getPath());
                                            try {

                                                compressedImageFile = new Compressor(ProfileActivity.this)
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


                                            StorageReference reference1 = storageReferenceimage.child(online_user_id).child("profileThumbimage" + ".jpg");;
                                            UploadTask uploadTask = reference1.putBytes(thumbData);


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

                                                                    downloadUrlthumb  = String.valueOf(downloadUri);
                                                                   // userDatabase.setThumbIamgeUri(String.valueOf(downloadUri));




                                                                    Map<String, Object> map = new HashMap<>();
                                                                    map.put(Constants.PROFILETHUMBURL, downloadUrlthumb);
                                                                    map.put( Constants.PROFILEIMAGE, downloadUrlReal);
                                                                    collectionReference.document(online_user_id).update(map)
                                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                @Override
                                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                                    Toast.makeText(ProfileActivity.this, "profile image changed successfully", Toast.LENGTH_LONG).show();
                                                                                      LoadingProgressBar.dismiss();


                                                                                }
                                                                            });



                                                                } else {
                                                                    // Handle failures
                                                                    //  mProgressDialog.dismiss();
                                                                    LoadingProgressBar.dismiss();
                                                                    Toast.makeText(getApplicationContext(), "unable to get Url", Toast.LENGTH_LONG).show();

                                                                    // ...
                                                                }
                                                            }
                                                        });


                                                    } else {
                                                        // Handle failures
                                                        //    mProgressDialog.dismiss();
                                                        LoadingProgressBar.dismiss();
                                                        Toast.makeText(getApplicationContext(), "unable to get Url", Toast.LENGTH_LONG).show();

                                                        // ...
                                                    }
                                                }
                                            });


                                        } else {
                                            // Handle failures
                                            //  mProgressDialog.dismiss();
                                            LoadingProgressBar.dismiss();
                                            Toast.makeText(getApplicationContext(), "unable to get Url", Toast.LENGTH_LONG).show();

                                            // ...
                                        }
                                    }
                                });



                            }

                        }
                    });
                }else {
                    LoadingProgressBar.dismiss();
                    Toast.makeText(getApplicationContext(), "no image selected", Toast.LENGTH_LONG).show();
                }

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Toast.makeText(ProfileActivity.this, "Error : " + error, Toast.LENGTH_LONG).show();
                LoadingProgressBar.dismiss();
            }

        }
    }
    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {


                if (items[item].equals("Take Photo")) {
                    userChoosenTask="Take Photo";
                   boolean result= Utility.checkPermission(ProfileActivity.this);
                    if(result)
                   cameraIntent();

                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask="Choose from Library";
                    boolean result= Utility.checkPermission(ProfileActivity.this);
                    if(result)
                        galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.profile_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.edit_profile){

            startActivity(new Intent(ProfileActivity.this, EditStatusActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void galleryIntent() {
        Intent galleryintent  = new Intent();
        galleryintent.setAction(Intent.ACTION_GET_CONTENT);
        galleryintent.setType("image/*");
        startActivityForResult(galleryintent, GALLERYPICK);
    }

    @Override
    public void onClick(View v) {
        int t = v.getId();
        switch (t){

            case R.id.imageview_profile11:
           dialogStuffs();


                break;
            case R.id.change_status:
               gostatus();
                break;

            case R.id.layout_for_name_status:
                gostatus();
                break;
        }
    }
    private void gostatus(){

        String profileName = profilename.getText().toString();
        String old_status = profilestatus.getText().toString();

        Intent intent=new Intent(getApplicationContext(), StatusActivity.class);
        intent.putExtra(Constants.PROFILESTATUS, old_status);
        intent.putExtra(Constants.PROFILENAME, profileName);
        startActivity(intent);
    }

    private void dialogStuffs(){
        final CharSequence[] items = { "Save image", "Change image","Remove image", "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
        builder.setTitle("Profile");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {


                if (items[item].equals("Change image")) {
                    boolean result= Utility.checkPermission(ProfileActivity.this);
                    if(result) {
                        CropImage.activity()
                                .setGuidelines(CropImageView.Guidelines.ON)
                                .setMinCropResultSize(512, 512)
                                .setAspectRatio(1, 1)
                                .start(ProfileActivity.this);
                    }
                } else if (items[item].equals("Save image")) {

                    BitmapDrawable drawable = (BitmapDrawable) profileimage.getDrawable();
                    Bitmap bitmap = drawable.getBitmap();
                    if(bitmap == null){
                        Toast.makeText(ProfileActivity.this, "image is empty", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(ProfileActivity.this, "image is not empty 0000000", Toast.LENGTH_SHORT).show();
                        SaveImage(bitmap);
                        Toast.makeText(ProfileActivity.this, "done", Toast.LENGTH_SHORT).show();

                    }


                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }  else if (items[item].equals("Remove image")) {


                }
            }
        });
        builder.show();


    }
    private void scanFile(String path) {

        MediaScannerConnection.scanFile(ProfileActivity.this,
                new String[] { path }, null,
                new MediaScannerConnection.OnScanCompletedListener() {

                    public void onScanCompleted(String path, Uri uri) {
                        Log.i("TAG", "Finished scanning " + path);
                    }
                });
    }
    private void SaveImage(Bitmap finalBitmap) {

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/ABU");
        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image-"+ n +".jpg";
        File file = new File (myDir, fname);
      //  scanFile(file.getAbsolutePath());
        if (file.exists ()) file.delete ();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();

         //   new SingleMediaScanner(ProfileActivity.this, file);
           // scanFile(file.getAbsolutePath());
            /*sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                    Uri.parse(file.getAbsolutePath() + Environment.getExternalStorageDirectory())));*/

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public class SingleMediaScanner implements MediaScannerConnection.MediaScannerConnectionClient {

        private MediaScannerConnection mMs;
        private File mFile;

        public SingleMediaScanner(Context context, File f) {
            mFile = f;
            mMs = new MediaScannerConnection(context, this);
            mMs.connect();
        }

        @Override
        public void onMediaScannerConnected() {
            mMs.scanFile(mFile.getAbsolutePath(), null);
        }

        @Override
        public void onScanCompleted(String path, Uri uri) {
            mMs.disconnect();
        }

    }

}
