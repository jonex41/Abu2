package com.john.www.abu.Activitiesss;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.john.www.abu.R;
import com.john.www.abu.Rgistration.RegisterActivity;
import com.john.www.abu.Rgistration.SignActivity;


/**
 * Created by MR AGUDA on 14-Nov-17.
 */

public class WelcomeActivity extends AppCompatActivity {


  private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_page);

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {

            Intent intent = new Intent(WelcomeActivity.this, Chat00Activity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }else {

            Intent intent = new Intent(WelcomeActivity.this, SignActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }


        //isUserCurrentlyLoggedIn(WelcomeActivity.this);
        /*Thread thread = new Thread(){
            @Override
            public void run() {

                try{
                    sleep(10);
                }catch (Exception e){
                    e.printStackTrace();
                }finally {



                    }else {
                        Intent intent = new Intent(WelcomeActivity.this, Chat00Activity.class);
                        //  intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);

                    }
                }
            }
        };
        thread.start();*/
    }


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {

            Intent intent = new Intent(WelcomeActivity.this, Chat00Activity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}


