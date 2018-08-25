package com.john.www.abu.PostStuffs.utils;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;



import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.john.www.abu.R;

/**
 * Created by brad on 2017/02/05.
 */

public class BaseActivity extends AppCompatActivity {

    protected FirebaseUser mFirebaseUser;

    protected FirebaseAuth mAuth;
    private ProgressDialog mProgressDialog; //Not really needed but I like having it


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        if (mAuth != null) {
            mFirebaseUser = mAuth.getCurrentUser();
        }


    }
    protected void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setCancelable(false);
        }
        mProgressDialog.show();
    }

    protected void dismissProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    protected void signOut() {
        mAuth.signOut();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dismissProgressDialog();
    }
}
