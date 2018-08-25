package com.john.www.abu.Activitiesss;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import com.google.firebase.firestore.Query;
import com.john.www.abu.DatabaseStuffs.sqlUsers.UserDatabase;

import com.john.www.abu.Fragment.Chatoo.Search;
import com.john.www.abu.Fragment.PicturePreviewDialog;
import com.john.www.abu.GlideApp;
import com.john.www.abu.R;
import com.john.www.abu.helper.CheckConnection;
import com.john.www.abu.helper.Constants;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddFriendActivity extends AppCompatActivity  implements View.OnClickListener{

    private Spinner spinner;
    private RecyclerView recyclerView;
    private EditText searchword;
    private String edittext;
    private String spinnerAnswer;
    private Query query;
    private FirestoreRecyclerAdapter<UserDatabase, RecyclerForFriendHolder> mAdapter;
    private String image;
    private String name;
    private String userid;
    private String status;

    private String myKey;

    private FirebaseAuth mAuth;

    private ProgressDialog dialog;
   private FirebaseFirestore firestore;

   private CollectionReference collectionReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addfriend);
        dialog = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();

        myKey = mAuth.getCurrentUser().getUid();
       // collectionReference = firestore.collection(Constants.USERS+"/"+myKey+"/"+"friends");
        collectionReference = firestore.collection(Constants.USERS).document(myKey).collection("friends");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbaraddfriend);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add friend");

        getSupportActionBar().setSubtitle("enter friend details");

        spinner = (Spinner) findViewById(R.id.spinner);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerforsearch);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));



        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),
                R.array.typeof_search2, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        searchword = (EditText)findViewById(R.id.editWriteMessage);
        Button button = (Button)findViewById(R.id.sendmeoo);
        button.setOnClickListener(this);



    }
    @Override
    public void onClick(View view) {

        CheckConnection checkConnection = new CheckConnection(getApplicationContext());
        if (checkConnection.isConnected()) {


            if (view.getId() == R.id.sendmeoo) {
                edittext = searchword.getText().toString();
                Toast.makeText(getApplicationContext(), edittext, Toast.LENGTH_SHORT).show();
                int selectedItem = (int) spinner.getSelectedItemPosition();

                switch (selectedItem) {

                    case 0:
                        Toast.makeText(getApplicationContext(), "O", Toast.LENGTH_SHORT).show();
                        spinnerAnswer = "RegNo";
                        query = FirebaseFirestore.getInstance().collection(Constants.USERS).whereEqualTo("RegNo", edittext);
                        setUpAdaper2(query);
                        setit();
                        break;
                    case 1:
                        query = FirebaseFirestore.getInstance().collection(Constants.USERS).whereEqualTo("Username", edittext);

                        setUpAdaper2(query);
                        setit();
                        break;
                    case 2:


                        query = FirebaseFirestore.getInstance().collection(Constants.USERS).whereEqualTo("name", edittext);

                        setUpAdaper2(query);
                        setit();


                       /* setUpAdaper2(query);
                        setit();*/
                        break;

                    case 3:


                        query = FirebaseFirestore.getInstance().collection(Constants.USERS).whereEqualTo("Department", edittext);

                        setUpAdaper2(query);
                        setit();
                        break;
                    case 4:
                        query = FirebaseFirestore.getInstance().collection(Constants.USERS).whereEqualTo("gender", edittext);

                        setUpAdaper2(query);
                        setit();

                        break;


                    default:
                        break;
                }

            }


        } else {

            Toast.makeText(getApplicationContext(), "check connection", Toast.LENGTH_SHORT).show();
        }
    }


    private void setit() {
        if (mAdapter != null) {
            mAdapter.startListening();
        }

    }

    public void setUpAdaper2(Query query1){



        FirestoreRecyclerOptions<UserDatabase> options = new FirestoreRecyclerOptions.Builder<UserDatabase>().setQuery(query1, UserDatabase.class).build();

        mAdapter = new FirestoreRecyclerAdapter<UserDatabase, RecyclerForFriendHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull RecyclerForFriendHolder holder, int position, @NonNull UserDatabase userDatabase) {
                image = userDatabase.getThumbIamgeUri();

                name = userDatabase.getName();
                userid = userDatabase.getUserid();
                status = userDatabase.getStatus();
                holder.setUsername(userDatabase.getName());
                holder.setmStatus(userDatabase.getStatus());

                //  Glide.with(ctx).load(list.get(position).getImage()).crossFade().placeholder(R.drawable.facebook_avatar).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.tvimage);
                //  UserDatabase userDatabase1 = new UserDatabase(name, status, image, userid);
                GlideApp.with(AddFriendActivity.this).load(image).error(R.drawable.facebook_avatar).placeholder(R.drawable.facebook_avatar)
                        .into(holder.tvimage);


                holder.imagelayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        int pos = holder.getAdapterPosition();

                        if(pos != RecyclerView.NO_POSITION) {
                            image = userDatabase.getImage();
                            name = userDatabase.getName();
                            userid = userDatabase.getUserid();
                            String thumbimage = userDatabase.getThumbIamgeUri();


                            Bundle bundle = new Bundle();
                            bundle.putString("image", image);
                            bundle.putString("thumbImage", thumbimage);
                            bundle.putString("name", name);
                            bundle.putString("userid", userid);
                            bundle.putString("status", status);


                            PicturePreviewDialog dialog = new PicturePreviewDialog();
                            dialog.setArguments(bundle);
                            dialog.show(((FragmentActivity) getApplicationContext()).getSupportFragmentManager(), null);
                        }
                    }
                });
                holder.chatlayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = holder.getAdapterPosition();

                        if(pos != RecyclerView.NO_POSITION) {
                            image = userDatabase.getImage();
                            name = userDatabase.getName();
                            userid = userDatabase.getUserid();
                            AlertDialog.Builder builder = new AlertDialog.Builder(AddFriendActivity.this);
                            builder.setTitle("Add Friend");
                            builder.setMessage("Are you sure you want to continue to adding friend");
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                    continueWithProcess(userid);

                                }
                            });
                            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            });
                            builder.setCancelable(true);
                            builder.show();

                        }

                    }
                });
            }

            @NonNull
            @Override
            public RecyclerForFriendHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new RecyclerForFriendHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.allusersview, parent, false));

            }
        };


        recyclerView.setAdapter(mAdapter);

    }

    private void continueWithProcess(String userid) {
        dialog.setMessage("Please wait...Adding Friend");
        dialog.setIndeterminate(true);
        dialog.show();
        collectionReference.document(userid).addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable DocumentSnapshot documentSnapshot, @javax.annotation.Nullable FirebaseFirestoreException e) {


                if(e!=null){

                    Toast.makeText(AddFriendActivity.this, "Error:"+e, Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                return;
                }


                if(documentSnapshot == null || !documentSnapshot.exists()){

                    collectionReference.document().set(userid).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {
                                dialog.dismiss();
                                Toast.makeText(AddFriendActivity.this, "Friend added successfully", Toast.LENGTH_SHORT).show();
                            }else {
                                dialog.dismiss();
                                Toast.makeText(AddFriendActivity.this, "Please try again later...", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else {
                    dialog.dismiss();
                    Toast.makeText(AddFriendActivity.this, "Already a friend", Toast.LENGTH_SHORT).show();
                }

            }
        });




    }


    @Override
    public void onStart() {
        super.onStart();

    }


    @Override
    public void onStop() {
        super.onStop();
        if (mAdapter != null) {
            mAdapter.stopListening();
        }
    }

    class RecyclerForFriendHolder extends RecyclerView.ViewHolder  {

        private ImageView imageView;
        private
        CheckBox onlineimage;
        private TextView mName, mTime, mStatus;
        private ImageButton imageButton;
        private LinearLayout imagelayout, chatlayout, buttonlayout;
        CircleImageView tvimage;


        public RecyclerForFriendHolder(View itemView) {
            super(itemView);


            tvimage = (CircleImageView) itemView.findViewById(R.id.imageview_profile);
            mName = (TextView) itemView.findViewById(R.id.tv_name);
            mTime = (TextView) itemView.findViewById(R.id.tv_time);
            mStatus = (TextView) itemView.findViewById(R.id.tv_status);
            imagelayout = (LinearLayout) itemView.findViewById(R.id.imageLinearLayout);
            chatlayout = (LinearLayout) itemView.findViewById(R.id.chatlayout);
            buttonlayout = (LinearLayout) itemView.findViewById(R.id.buttonlayout);
            onlineimage = (CheckBox) itemView.findViewById(R.id.check_button);
            // databaseOnline = FirebaseDatabase.getInstance().getReference().child("users");

        }
        public void setUsername(String username){

            mName.setText(username);
        }
        public void setmStatus(String status){

            mStatus.setText(status);
        }
    }
}


