package com.john.www.abu.PreferenceStuffs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;


import com.john.www.abu.R;

public class Account extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbaraccount);
        setSupportActionBar(toolbar);
      //  getSupportActionBar().setTitle(" ");
       getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      /* ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);*/

    }
}
