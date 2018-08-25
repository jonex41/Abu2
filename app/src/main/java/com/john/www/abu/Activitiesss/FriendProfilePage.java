package com.john.www.abu.Activitiesss;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.john.www.abu.GlideApp;
import com.john.www.abu.R;
import com.john.www.abu.Rgistration.SignActivity;
import com.john.www.abu.helper.Constants;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by MR AGUDA on 21-Apr-18.
 */

public class FriendProfilePage extends AppCompatActivity implements View.OnClickListener {


    private String image;
    private String name;
    private String userid;
    private ImageView profileimage;
    private TextView profilename;
    private TextView profilestatus;
    private Button changeimage;
    private Button changestatus;
    private String status;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;

    private CollectionReference collectionReference;

    private FirebaseUser currentUser;
    private String id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friendprofile_layout);

       image = getIntent().getStringExtra(Constants.OTHERSIMAGE);
        name = getIntent().getStringExtra(Constants.OTHERSNAME);
         userid = getIntent().getStringExtra(Constants.OTHERSKEY);
       status = getIntent().getStringExtra("status");

        profileimage = (ImageView) findViewById(R.id.imageview_profile11);
        GlideApp.with(FriendProfilePage.this).load(image).placeholder(R.drawable.facebook_avatar).diskCacheStrategy(DiskCacheStrategy.ALL).into(profileimage);

        profilename = (TextView) findViewById(R.id.myusername);
        profilename.setText(name);
        profilestatus = (TextView) findViewById(R.id.mystatus);
        profilestatus.setText(status);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

         id = mAuth.getCurrentUser().getUid();



        firestore = FirebaseFirestore.getInstance();

    }

    @Override
    public void onClick(View view) {
       int t = view.getId();

       switch (t){


       }
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

            FirebaseFirestore.getInstance().collection(Constants.USERS).document(id).update(Constants.ONLINE, "true");
        }
    }
}

