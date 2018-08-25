package com.john.www.abu.Activitiesss;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.john.www.abu.GlideApp;
import com.john.www.abu.R;
import com.john.www.abu.Rgistration.SignActivity;
import com.john.www.abu.helper.Constants;

/**
 * Created by MR AGUDA on 21-Apr-18.
 */

public class JustPicture extends AppCompatActivity implements View.OnClickListener{


    private String name;
    private String image;
    private String userid;
    private String status;
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    private String id;


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.just_picture);

        Toolbar toolbar = (Toolbar) findViewById(R.id.justpicturetoolbar);
        toolbar.setTitle(" ");
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
       // actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.justpicturetoobar, null);
        actionBar.setCustomView(view);

        ImageView back = (ImageView) findViewById(R.id.backtomainactivity);
        TextView textView = (TextView) findViewById(R.id.justpicturename);
        back.setOnClickListener(this);

         image = getIntent().getStringExtra("image");
         name = getIntent().getStringExtra("name");
        String thumbimage = getIntent().getStringExtra("thmbimage");
         userid = getIntent().getStringExtra("userid");
        status = getIntent().getStringExtra("status");

       textView.setText(name);
        ImageView imageView = (ImageView) findViewById(R.id.justimagestuff);


        GlideApp.with(JustPicture.this).load(image).placeholder(R.drawable.facebook_avatar).thumbnail(
                Glide.with(JustPicture.this).load(thumbimage)
        ).into(imageView);
      //  GlideApp.with(JustPicture.this).load(image).error(R.drawable.facebook_avatar).placeholder(R.drawable.facebook_avatar).into(imageView);
        Button button = (Button) findViewById(R.id.buttontoprofile);
        button.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

         id = mAuth.getCurrentUser().getUid();


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.backtomainactivity:
               finish();
                break;
            case R.id.buttontoprofile:
                Intent intent = new Intent(JustPicture.this, FriendProfilePage.class);
                intent.putExtra("image", image);
                intent.putExtra("name", name);
                intent.putExtra("userid", userid);
                intent.putExtra("status", status);
                startActivity(intent);
                finish();
                break;
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
