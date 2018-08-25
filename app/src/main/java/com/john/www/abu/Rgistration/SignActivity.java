package com.john.www.abu.Rgistration;

import  android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.john.www.abu.Activitiesss.Chat00Activity;
import com.john.www.abu.R;
import com.john.www.abu.helper.InputValidation;

/**
 * Created by MR AGUDA on 14-Nov-17.
 */

public class SignActivity extends AppCompatActivity implements  View.OnClickListener {

    private static final String TAG = "EmailPassword";


    private TextView mStatusTextView;
    private TextView mDetailTextView;
    private TextInputEditText mEmailField;
    private TextInputEditText mPasswordField;
    private TextInputEditText mnameField;

    private TextInputLayout mEmailFieldla;
    private TextInputLayout mPasswordFieldla;
    private TextInputLayout mnameFieldla;

    String email, password ,name;

    private ProgressDialog progressDialog;
    private InputValidation validation;
    private String userId;

    private Button register, signin;

    // [START declare_auth]
    private com.google.firebase.auth.FirebaseAuth mAuth;
    private FirebaseFirestore mDatabase;
    private FirebaseFirestore profileDatabase;
    private FirebaseUser currentUser;
    // [END declare_auth]

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_activity);

        String email = getIntent().getStringExtra("email");
        String password = getIntent().getStringExtra("password");

        // [END initialize_auth]

         /* Toolbar toolbar = (Toolbar) findViewById(R.id.toolbart);
           if(toolbar != null) {
               setSupportActionBar(toolbar);
               getSupportActionBar().setTitle("Log In");
           }*/

        validation = new InputValidation(getApplicationContext());
        // Views

        mEmailField = (TextInputEditText) findViewById(R.id.register_field_email);
        mEmailField.setText(email);
        mPasswordField = (TextInputEditText) findViewById(R.id.register_field_password);
        mPasswordField.setText(password);
        //  mnameField = (TextInputEditText) findViewById(R.id.register_field_name);


        //  mnameFieldla = (TextInputLayout) findViewById(R.id.register_layout_name);
        mEmailFieldla = (TextInputLayout) findViewById(R.id.register_layout_email);
        mPasswordFieldla = (TextInputLayout) findViewById(R.id.register_layout_passsword);
        // Buttons
        register=(Button) findViewById(R.id.create_new_account);
        signin=(Button) findViewById(R.id.signin);
        register.setOnClickListener(this);
        signin.setOnClickListener(this);
        // [START initialize_auth]

        profileDatabase = FirebaseFirestore.getInstance();
        progressDialog = new ProgressDialog(this);

    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.create_new_account) {
            startActivity(new Intent(SignActivity.this, RegisterActivity.class));

        } else if (i == R.id.signin) {
            if (!validation.isInputEditTextFilled(mEmailField, mEmailFieldla, getString(R.string.error_message_emailnull))||!validation.isInputEditTextEmail(mEmailField, mEmailFieldla, getString(R.string.error_message_emailnull))||!validation.isInputEditTextFilled(mPasswordField, mPasswordFieldla, getString(R.string.error_message_password))) {
                return;
            }
            signIn(mEmailField.getText().toString(), mPasswordField.getText().toString());
        }
    }
    // [START on_start_check_user]
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.

    }
    // [END on_start_check_user]


    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);


        progressDialog.setTitle("Log in");
        progressDialog.setMessage("Processing your account,please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        // [START sign_in_with_email]
        mAuth = FirebaseAuth.getInstance();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(SignActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            Intent intent = new Intent(SignActivity.this, Chat00Activity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();

                            // checkUserExist();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(SignActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // [START_EXCLUDE]
                        if (!task.isSuccessful()) {
                            Toast.makeText(SignActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                        // [END_EXCLUDE]
                    }
                });
        // [END sign_in_with_email]
    }

    private void checkUserExist() {


        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        userId = mAuth.getUid();

    }





}



