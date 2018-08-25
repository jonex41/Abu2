package com.john.www.abu.Activitiesss;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.john.www.abu.R;
import com.john.www.abu.Rgistration.SignActivity;
import com.john.www.abu.helper.Constants;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by MR AGUDA on 15-Nov-17.
 */

public class StatusActivity extends AppCompatActivity implements View.OnClickListener {


    private Toolbar mToobar;
    private EditText statuset;
    private EditText name;
    private Button statuschangebutton;

    private com.google.firebase.auth.FirebaseAuth mAuth;
    private ProgressDialog progress;
    private View view;
    private String user_id;
  private FirebaseFirestore firestore;
  private CollectionReference collectionReference;
    private DocumentReference statusUpdate;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.status_activity);

        mAuth = FirebaseAuth.getInstance();
         user_id = mAuth.getCurrentUser().getUid();

      firestore = FirebaseFirestore.getInstance();


        statusUpdate = firestore.collection(Constants.USERS).document(user_id);
        mToobar = (Toolbar) findViewById(R.id.toolbarstatus);
        setSupportActionBar(mToobar);
        getSupportActionBar().setTitle("Change Status");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        statuset = (EditText) findViewById(R.id.etstatus);
        name = (EditText) findViewById(R.id.etname);
        statuschangebutton = (Button) findViewById(R.id.btchangeprofile);
        progress = new ProgressDialog(this);
        String old_status = getIntent().getStringExtra(Constants.PROFILESTATUS);
        String named = getIntent().getStringExtra(Constants.PROFILENAME);
        statuset.setText(old_status);
        name.setText(named);
statuschangebutton.setOnClickListener(this);

    }


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser == null){

            Intent intent = new Intent(getApplicationContext(), SignActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }else {
            FirebaseFirestore.getInstance().collection(Constants.USERS).document(user_id).update(Constants.ONLINE, "true");

        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId()== R.id.btchangeprofile){
                progress.setTitle("Changing profile status");
                progress.setMessage("wait, while we are changing your profile status");
                progress.setCancelable(false);
              changestatus();

        }
    }

    private void changestatus() {
        String new_status = statuset.getText().toString();
        String new_name = name.getText().toString();
        Map<String, Object> map = new HashMap<>();
        map.put(Constants.PROFILENAME, new_name);
        map.put(Constants.PROFILESTATUS, new_status);

        if (!TextUtils.isEmpty(new_status)||!TextUtils.isEmpty(new_name)) {

            Snackbar.make(view, "please write your new status", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return;
        } else




        statusUpdate.update(map)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                progress.dismiss();


                                finish();
                                Toast.makeText(getApplicationContext(), "status updated", Toast.LENGTH_LONG).show();

                            }else{
                                Toast.makeText(getApplicationContext(), "error occurred", Toast.LENGTH_LONG).show();
                                progress.dismiss();
                            }
                        }
                    });
        progress.dismiss();
    }

    }

