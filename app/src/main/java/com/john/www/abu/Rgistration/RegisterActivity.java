package com.john.www.abu.Rgistration;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.john.www.abu.R;
import com.john.www.abu.helper.Constants;
import com.john.www.abu.helper.InputValidation;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity implements  View.OnClickListener{

    private static final String TAG = "EmailPassword";


    private TextView mStatusTextView;
    private TextView mDetailTextView;
    private TextInputEditText mEmailField;
    private TextInputEditText mPasswordField;
    private TextInputEditText mnameField;

    private TextInputLayout mEmailFieldla;
    private TextInputLayout mPasswordFieldla;
    private TextInputLayout mnameFieldla;
    String gender = " ";

    String email, password ,name;

    private ProgressDialog progressDialog;
    private InputValidation validation;
    private String userId;
    private RadioButton male;
    private RadioButton female;

    private AppCompatButton register, signin;

    // [START declare_auth]
    private com.google.firebase.auth.FirebaseAuth mAuth;
    private FirebaseFirestore mDatabase;
    private FirebaseFirestore profileDatabase;
    private FirebaseUser currentUser;
    private TextInputEditText regNo;
    private TextInputLayout mRegNo;


    private TextInputEditText departmentedt;
    private TextInputLayout departmentlayout;
    // [END declare_auth]

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = com.google.firebase.auth.FirebaseAuth.getInstance();
        // [END initialize_auth]

        /**   Toolbar toolbar = (Toolbar) findViewById(R.id.toolbart);
         if(toolbar != null) {
         setSupportActionBar(toolbar);
         getSupportActionBar().setTitle("Log In");
         }
         */

        male = (RadioButton) findViewById(R.id.male);
        female = (RadioButton) findViewById(R.id.female);



        validation = new InputValidation(getApplicationContext());
        // Views

        mEmailField = (TextInputEditText) findViewById(R.id.register_field_email);
        mPasswordField = (TextInputEditText) findViewById(R.id.register_field_password);
        mnameField = (TextInputEditText) findViewById(R.id.register_field_name);
        regNo = (TextInputEditText) findViewById(R.id.register_field_reg_no);
        departmentedt = (TextInputEditText) findViewById(R.id.register_field_department);


        mnameFieldla = (TextInputLayout) findViewById(R.id.register_layout_name);
        mEmailFieldla = (TextInputLayout) findViewById(R.id.register_layout_email);
        mPasswordFieldla = (TextInputLayout) findViewById(R.id.register_layout_passsword);
        mRegNo = (TextInputLayout) findViewById(R.id.register_layout_reg_no);

        departmentlayout = (TextInputLayout) findViewById(R.id.register_layout_department);
        // Buttons
        register=(AppCompatButton) findViewById(R.id.registerAccount);
        signin=(AppCompatButton) findViewById(R.id.siginagian);
        register.setOnClickListener(this);
        signin.setOnClickListener(this);
        // [START initialize_auth]

        profileDatabase = FirebaseFirestore.getInstance();
        progressDialog = new ProgressDialog(this);

    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.registerAccount) {


            if (!validation.isInputEditTextFilled(mnameField, mnameFieldla, getString(R.string.error_message_name))||
                    !validation.isInputEditTextFilled(departmentedt, departmentlayout, getString(R.string.error_message_name))||
                    !validation.isInputEditTextFilled(mEmailField, mEmailFieldla, getString(R.string.error_message_emailnull))||
                    !validation.isInputEditTextEmail(mEmailField, mEmailFieldla, getString(R.string.error_message_emailnull))||
                    !validation.isInputEditTextFilled(regNo, mRegNo, getString(R.string.error_message_emailnull))||!
                    validation.isInputEditTextFilled(mPasswordField, mPasswordFieldla, getString(R.string.error_message_password))) {
                return;
            }

            if(Pattern.matches("[a-zA-Z]+", regNo.getText().toString()) || regNo.getText().toString().matches("\\d+(?:\\.\\d+)?")){
                Toast.makeText(getApplicationContext(), "check your student reg no", Toast.LENGTH_SHORT).show();
                return;
            }
            if(!male.isChecked() && !female.isChecked()){

                Toast.makeText(getApplicationContext(), "Please select your gender", Toast.LENGTH_LONG).show();
                return;
            }else {
                if(male.isChecked()){
                    gender = "male";
                }else if(female.isChecked()){
                    gender = "female";
                }
            }


            createAccount(mEmailField.getText().toString(), mPasswordField.getText().toString(),
                    mnameField.getText().toString(), regNo.getText().toString(), departmentedt.getText().toString());
        } else if (i == R.id.siginagian) {

            startActivity(new Intent(RegisterActivity.this, SignActivity.class));
        }
    }
    // [START on_start_check_user]


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.

    }
    // [END on_start_check_user]

    private void createAccount(String email, String password, final String name, final String regno, final String department) {
        Log.d(TAG, "createAccount:" + email);
        //  mDatabase = FirebaseDatabase.getInstance().getReference();



        progressDialog.setTitle("Creating new account");
        progressDialog.setMessage("please wait, while we create an account for you");
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        // [START create_user_with_email]


        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");

                            mAuth = FirebaseAuth.getInstance();
                            String userid1 = mAuth.getCurrentUser().getUid();
                            //   String useratoken = FirebaseInstanceId.getInstance().getToken();
                            Map<String, Object> map = new HashMap<>();

                            map.put(Constants.PROFILENAME, name);
                            map.put(Constants.ONLINE, "true");
                            map.put(Constants.PROFILEIMAGE, Constants.DEFUALTIMAGE);
                            map.put("Department", department);
                            map.put(Constants.USERID, userid1);
                            map.put("RegNo", regno);
                            map.put("Gender", gender);
                            map.put(Constants.PROFILESTATUS, "Hey it's " + name + ", kind of new here");

                            profileDatabase.collection("Users").document(userid1).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    progressDialog.dismiss();
                                    Intent intent = new Intent(RegisterActivity.this, SignActivity.class);
                                    intent.putExtra("email",email);
                                    intent.putExtra("password", password);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressDialog.dismiss();
                                    Toast.makeText(RegisterActivity.this, " please try again, unable to create an account", Toast.LENGTH_LONG).show();
                                }

                            });









                        } else {
                            // If sign in fails, display a message to the user.
                            progressDialog.dismiss();
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed."+task.getException(),
                                    Toast.LENGTH_SHORT).show();

                        }
                    }


                });



    }
}



