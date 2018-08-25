package com.john.www.abu.Fragment.AbuGIstooo;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.john.www.abu.Adapters.ViewPagerAdapter;

import com.john.www.abu.DatabaseStuffs.sqlUsers.UserForVote;
import com.john.www.abu.R;
import com.john.www.abu.helper.CheckConnection;
import com.john.www.abu.helper.Constants;

import java.util.HashMap;
import java.util.Map;


public class VoteActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private Button btn_add_contestant;
    private TextView the_present_phase;
    ProgressDialog dialog;
    FirebaseAuth mAuth;
    private FirebaseFirestore firestore;
    private DocumentReference collectionReference;

    String userId;
    private DocumentReference collectionRefReal;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vote);
        dialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();


       firestore = FirebaseFirestore.getInstance();
       collectionReference = firestore.collection(Constants.USERS).document(userId);
       collectionRefReal = firestore.collection("vote").document("contestants");


        btn_add_contestant =  (Button) findViewById(R.id.btn_contesttant);
        the_present_phase = (TextView) findViewById(R.id.phases_completed);

        btn_add_contestant.setOnClickListener(this);



        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setUpViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }
    private void setUpViewPager(ViewPager viewPager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new MaleFragment(), "Male");
        adapter.addFragment(new FemaleFragment(), "Female");

        viewPager.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btn_contesttant:
                CheckConnection checkConnection = new CheckConnection(getApplicationContext());
               if( checkConnection.isConnected()){


                   AlertDialog.Builder builder = new AlertDialog.Builder(VoteActivity.this);
                   builder.setTitle("Add contestant");
                   builder.setMessage("Are you sure you want to be a contestant");
                   builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialogInterface, int i) {
                            dialog.setMessage("please wait while we validate and add your data");

                           continueWithProcess();
                           if(dialog.isShowing()){
                               dialog.cancel();
                           }
                       }
                   }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialogInterface, int i) {
                           dialogInterface.dismiss();
                       }
                   });
                   builder.show();
            }

        }
    }

    private void continueWithProcess() {
        dialog.setIndeterminate(true);
        dialog.setMessage("Checking data...");
        dialog.show();
     collectionReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
         @Override
         public void onComplete(@NonNull Task<DocumentSnapshot> task) {

             if (task.isSuccessful()) {

                 DocumentSnapshot documentSnapshot = task.getResult();
                 UserForVote userForVote = documentSnapshot.toObject(UserForVote.class);
                 String gender = userForVote.getGender();

                // Map<String, Object> map = new HashMap<>();

                 collectionRefReal.collection(gender).document(userId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                     @Override
                     public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                         if(task.isSuccessful()){
                             DocumentSnapshot documentSnapshot1 = task.getResult();
                             if (!documentSnapshot.exists()) {
                                 collectionRefReal.collection(gender).add(userId).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                     @Override
                                     public void onComplete(@NonNull Task<DocumentReference> task) {
                                         if (task.isSuccessful()) {
                                             dialog.cancel();
                                             Toast.makeText(VoteActivity.this, "You have been successfully added", Toast.LENGTH_SHORT).show();


                                         } else {
                                             dialog.cancel();
                                             Toast.makeText(VoteActivity.this, "please reapply a problem occur some where ", Toast.LENGTH_SHORT).show();

                                         }
                                     }
                                 });


                             } else {
                                 dialog.cancel();
                                 Toast.makeText(VoteActivity.this, "you are already a contestant", Toast.LENGTH_SHORT).show();
                             }
                         }
                     }
                 });


             }else {
                 Toast.makeText(VoteActivity.this, "Unable to get user, try again", Toast.LENGTH_SHORT).show();
             }
         }
          });



          }
    }