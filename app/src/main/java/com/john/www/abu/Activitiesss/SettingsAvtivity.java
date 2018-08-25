package com.john.www.abu.Activitiesss;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.john.www.abu.DatabaseStuffs.sqlUsers.UserOnly;
import com.john.www.abu.GlideApp;
import com.john.www.abu.PreferenceStuffs.Account;
import com.john.www.abu.PreferenceStuffs.PrefNotification;
import com.john.www.abu.PreferenceStuffs.PreferenceChat;
import com.john.www.abu.PreferenceStuffs.PreferenceDataUsage;
import com.john.www.abu.R;
import com.john.www.abu.helper.Constants;

import de.hdodenhof.circleimageview.CircleImageView;


public class SettingsAvtivity extends AppCompatActivity implements View.OnClickListener{

    private TextView accountsettings;
    private TextView chatssettings;
    private TextView notificationsettings;
    private TextView dataandstoragesettings;
    private TextView invitenotification;
    private TextView helpsettings;

    private FirebaseFirestore firestore;
    private DocumentReference collectionReference;
    private LinearLayout linearLayout;
    private CircleImageView circleImageView;
    private TextView name;
    private TextView status;


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarsettings);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle(" ");
       /* ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);*/
        FirebaseAuth mAtuh = FirebaseAuth.getInstance();
        String uid = mAtuh.getCurrentUser().getUid();
          getSupportActionBar().setDisplayHomeAsUpEnabled(true);

          name = (TextView) findViewById(R.id.myname);
          status = (TextView) findViewById(R.id.mystatus);
          circleImageView = (CircleImageView) findViewById(R.id.myimage);
          linearLayout = (LinearLayout) findViewById(R.id.liprofile);
          linearLayout.setOnClickListener(this);



          firestore = FirebaseFirestore.getInstance();
          collectionReference = firestore.collection(Constants.USERS).document(uid);

          collectionReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
              @Override
              public void onSuccess(DocumentSnapshot queryDocumentSnapshots) {


                  UserOnly userOnly = queryDocumentSnapshots.toObject(UserOnly.class);

                  String names= userOnly.getName();
                  String image = userOnly.getImage();
                  String statusq = userOnly.getStatus();


                  GlideApp.with(getApplicationContext()).load(image).placeholder(R.drawable.facebook_avatar).diskCacheStrategy(DiskCacheStrategy.ALL).into(circleImageView);

                  name.setText(names);
                  status.setText(statusq);

              }
          });

        accountsettings = (TextView) findViewById(R.id.accountofsettings);
        chatssettings = (TextView) findViewById(R.id.chatssettings);
        notificationsettings = (TextView) findViewById(R.id.notificationsettings);
        dataandstoragesettings = (TextView) findViewById(R.id.dataandstoragesettings);
        invitenotification = (TextView) findViewById(R.id.invitesettings);
        helpsettings = (TextView) findViewById(R.id.helpsettings);

        accountsettings.setOnClickListener(this);
        chatssettings.setOnClickListener(this);
        notificationsettings.setOnClickListener(this);
        dataandstoragesettings.setOnClickListener(this);
        invitenotification.setOnClickListener(this);
        helpsettings.setOnClickListener(this);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.accountofsettings:
                startActivity(new Intent(SettingsAvtivity.this, Account.class));
                break;
            case R.id.chatssettings:
                        startActivity(new Intent(SettingsAvtivity.this, PreferenceChat.class));
                break;
            case R.id.notificationsettings:
                startActivity(new Intent(SettingsAvtivity.this, PrefNotification.class));
                break;
            case R.id.dataandstoragesettings:
                startActivity(new Intent(SettingsAvtivity.this, PreferenceDataUsage.class));
                break;
            case R.id.invitesettings:

                Intent smsIntent = new Intent(Intent.ACTION_VIEW);
                smsIntent.setType("vnd.android-dir/mms-sms");
                smsIntent.putExtra("address", "12125551212");
                smsIntent.putExtra("sms_body","Body of Message");
                startActivity(smsIntent);
                break;
            case R.id.helpsettings:

                break;
            case R.id.liprofile:
                startActivity(new Intent(SettingsAvtivity.this, ProfileActivity.class));
            break;

        }
    }
}
