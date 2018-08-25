
package com.john.www.abu.GroupsActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.john.www.abu.Adapters.MessageAdapterYes;
import com.john.www.abu.DatabaseStuffs.sqlMessage.DatMessage;
import com.john.www.abu.DatabaseStuffs.sqlMessage.Manss;
import com.john.www.abu.GlideApp;
import com.john.www.abu.R;
import com.john.www.abu.Rgistration.SignActivity;
import com.john.www.abu.helper.Constants;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;


public abstract class GroupChatActivity extends AppCompatActivity implements View.OnClickListener{


    private CircleImageView toolbarimageView;
    private TextView toolbarName;
    private TextView toolbarTime;
    private RecyclerView Recycler;
    private EditText writemessage;


    private String myKey;
    private String myName;
    private String myImage;
    private ArrayList<Manss> messagessre;
    private MessageAdapterYes messageAdater;
    private DatMessage datMessage;


    private LinearLayoutManager linearLayoutManager;


    private FirebaseAuth mAuth;
    private ImageButton caption_image;



    public static final String DATA = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static Random RANDOM = new Random();

   private FirebaseFirestore firestore;
   private CollectionReference collectionReference;
    private String FirstArgs;
    private ImageView imageView;
    private String SecongArgs;
    private Uri resultUrl =null;
    private static final int GALLERYPICK =45;
    private byte[] bytes;
    private Bitmap imageBitmap;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        messagessre = new ArrayList<Manss>();
        imageView = (ImageView) findViewById(R.id.kooimage);

      //  datMessage = new DatMessage(GroupChatActivity.this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarj);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        String titles = getIntent().getStringExtra("class");
        if(!titles.isEmpty()) {
            getSupportActionBar().setTitle(titles);

            if(titles.contains("Relationship")){
                getSupportActionBar().setSubtitle("Room for solving "+titles.toLowerCase()+ " problems");
            }else {

                getSupportActionBar().setSubtitle("Room for gisting about "+titles.toLowerCase());
            }
        }





        mAuth = FirebaseAuth.getInstance();
        myKey = mAuth.getCurrentUser().getUid();
        writemessage = (EditText) findViewById(R.id.editWriteMessage);


        ImageButton sendButton = (ImageButton) findViewById(R.id.btnSend);
        ImageButton recordButton = (ImageButton) findViewById(R.id.btnrecord);
        ImageButton selectImagetosend = (ImageButton) findViewById(R.id.btnselectImage);
        ImageButton adddoc = (ImageButton) findViewById(R.id.atach);
        ImageButton caption = (ImageButton) findViewById(R.id.captions_image);


        writemessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                selectImagetosend.setVisibility(View.VISIBLE);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!writemessage.getText().toString().equals("")){

                    //   int height = writemessage.getHeight();
                    //  if(height)

                    sendButton.setVisibility(View.VISIBLE);
                    adddoc.setVisibility(View.VISIBLE);
                    recordButton.setVisibility(View.GONE);
                    selectImagetosend.setVisibility(View.GONE);
                    selectImagetosend.setVisibility(View.GONE);
                    Recycler.scrollToPosition(messagessre.size() - 1);
                }else {
                    selectImagetosend.setVisibility(View.VISIBLE);
                    recordButton.setVisibility(View.VISIBLE);
                    sendButton.setVisibility(View.GONE);
                    adddoc.setVisibility(View.GONE);

                }
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        sendButton.setOnClickListener(this);
        selectImagetosend.setOnClickListener(this);




        //    Toast.makeText(this, "id: " + othersKey, Toast.LENGTH_LONG).show();
        Recycler = (RecyclerView) findViewById(R.id.recyclerChat);
        Recycler.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this );

        Recycler.setLayoutManager(linearLayoutManager);
        messageAdater = new MessageAdapterYes(GroupChatActivity.this, messagessre);
        Recycler.setAdapter(messageAdater);
        // getDataFromSQLite();

       firestore = FirebaseFirestore.getInstance();






        fectdatais();

    }

    @Override
    public void onBackPressed() {

      // finish();
       super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        int t = v.getId();
        switch (t){

            case R.id.btnSend:
                sendMessage();
                break;
            case R.id.btnselectImage:
                CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).start(GroupChatActivity.this);

/* Intent intent_upload = new Intent();
                    intent_upload.setType("image/*");
                    intent_upload.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(intent_upload,GALLERYPICK);*/



                break;
        }
    }

    private void sendPicture() {
        long time = System.currentTimeMillis();
        String tiy = String.valueOf(time);

        Manss manss = new Manss( myKey,"picture",bytes, getTimeStamp());

        messagessre.add(manss);
        messageAdater.notifyDataSetChanged();
        Recycler.scrollToPosition(messagessre.size() - 1 );
    }

    public boolean isAnagram(String firstWord, String secondWord) {
        char[] word1 = firstWord.replaceAll("[\\s]", "").toCharArray();
        char[] word2 = secondWord.replaceAll("[\\s]", "").toCharArray();
        Arrays.sort(word1);
        Arrays.sort(word2);
        return Arrays.equals(word1, word2);
    }
    private void fectdatais() {

                Query query = getDatabaseRef().collection("Messages");

                query.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                      if(e!=null){

                          Toast.makeText(GroupChatActivity.this, "Error"+e, Toast.LENGTH_SHORT).show();
                            return;
                      }

                        messagessre.clear();
                        for(QueryDocumentSnapshot ds : queryDocumentSnapshots){

                            if(ds !=null){

                                String message = ds.getString(Constants.MESSAGE);
                                String time = ds.getString(Constants.TIME);
                                String senderId = ds.getString(Constants.SENDERID);
                                String type = ds.getString(Constants.TYPE);


                                Manss user = new Manss(message,senderId,type ,time);
                                messagessre.add(user);
                                messageAdater.notifyDataSetChanged();
                                Recycler.scrollToPosition(messagessre.size() - 1);
                            }
                        }

                    }
                });





    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);




        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                resultUrl = result.getUri();


                File image_filepath = new File(resultUrl.getPath());

//                String user_id = mAuth.getCurrentUser().getUid();

                try{
                    imageBitmap = new Compressor(GroupChatActivity.this).setMaxWidth(512).setMaxHeight(512).setQuality(100).
                            compressToBitmap(image_filepath);


                } catch (IOException e) {
                    e.printStackTrace();
                }

                ByteArrayOutputStream boas = new ByteArrayOutputStream();
                imageBitmap.compress(Bitmap.CompressFormat.JPEG,100,boas);

                bytes = boas.toByteArray();


                sendPicture();


            }
        }




    }

   // public abstract Query query();
   // public abstract String groupName(String name);

    @Override
    protected void onStart() {
        super.onStart();

        //  getDataFromSQLite();
       // getDataFromSQLite();
        fectdatais();
     //   getDataFromSQLite();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser == null){

            Intent intent = new Intent(getApplicationContext(), SignActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }else {
          //  databaseReference.child("online").setValue("true");
        }
    }

    @Override
    protected void onStop() {
        super.onStop();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

/*if (mvalueEventListener != null) {
            recieveMessage.removeEventListener(mvalueEventListener);
        }*/

    }

    public static String getTimeStamp() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date());


    }

    @SuppressLint("StaticFieldLeak")
    private void getDataFromSQLite() {
        // AsyncTask is used that SQLite operation not blocks the UI Thread.

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {


                //   messagessre.clear();
                Log.d("mango", "chat:" + messagessre);
                if(messagessre.size() >0){
                    messagessre.clear();
                }
                messagessre.addAll(datMessage.getindivMessages(FirstArgs));

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                messageAdater.notifyDataSetChanged();
                Recycler.scrollToPosition(messagessre.size() - 1);
                // Toast.makeText(ChatActivity.this, "all message acquired", Toast.LENGTH_SHORT).show();
            }
        }.execute();
    }

    public abstract DocumentReference getDatabaseRef();

    public void sendMessage() {

        String message = writemessage.getText().toString();


        if (!TextUtils.isEmpty(message)) {

            long time = System.currentTimeMillis();
            String tiy = String.valueOf(time);

            Manss manss = new Manss(message,myKey ,"text",String.valueOf(getTimeStamp())+tiy );

            // getDataFromSQLite();
            messagessre.add(manss);
            messageAdater.notifyDataSetChanged();
            Recycler.scrollToPosition(messagessre.size() - 1 );

            DocumentReference databaseReference = getDatabaseRef();




            Map messageMap = new HashMap();
            messageMap.put("message", message);
            messageMap.put("seen", false);
            messageMap.put("type", "text");
            messageMap.put("time", String.valueOf(getTimeStamp()));
            messageMap.put("senderId", myKey);

            DocumentReference ref =  firestore.collection("Messages").document();
            String id = ref.getId();

            Map<String, Object> map = new HashMap();
            map.put(id, messageMap);





            writemessage.setText("");
                databaseReference.set(map).addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        Toast.makeText(GroupChatActivity.this, "sent" , Toast.LENGTH_SHORT).show();

                    }
                });


        }else{

            Toast.makeText(GroupChatActivity.this, "no message" , Toast.LENGTH_SHORT).show();

        }
    }


    public String ramdomString(int len){

        StringBuilder sb = new StringBuilder(len);
        for(int i = 1; i <len; i++){
            sb.append(DATA.charAt(RANDOM.nextInt(DATA.length())));
        }
        return sb.toString();
    }



}

