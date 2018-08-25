package com.john.www.abu.Activitiesss;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.john.www.abu.Adapters.MessageAdapterYes;
import com.john.www.abu.DatabaseStuffs.sqlMessage.DatMessage;
import com.john.www.abu.DatabaseStuffs.sqlMessage.Manss;
import com.john.www.abu.GlideApp;
import com.john.www.abu.R;
import com.john.www.abu.Rgistration.SignActivity;
import com.john.www.abu.helper.Constants;

import com.john.www.abu.helper.Utility;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.xml.transform.dom.DOMLocator;

import cafe.adriel.androidaudiorecorder.AndroidAudioRecorder;
import cafe.adriel.androidaudiorecorder.model.AudioChannel;
import cafe.adriel.androidaudiorecorder.model.AudioSampleRate;
import cafe.adriel.androidaudiorecorder.model.AudioSource;
import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;

/**
 * Created by MR AGUDA on 16-Nov-17.
 */

public class ChatActivity extends AppCompatActivity implements View.OnClickListener{


    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 34;
    private static final int REQUEST_RECORD_AUDIO = 43;
    private CircleImageView toolbarimageView;
    private TextView toolbarName;
    private TextView toolbarTime;
    private RecyclerView recyclerView;
    private EditText writemessage;


    private String myKey;
    private String myName;
    private String myImage;
    private List<Manss> messagessre;
    private MessageAdapterYes messageAdater;
    private DatMessage datMessage;

    private String othersKey;
    private String othersName;
    private String othersImage;
    private LinearLayoutManager linearLayoutManager;

    private FirebaseFirestore messageRef;
    private FirebaseFirestore recieveMessage;
    private FirebaseAuth mAuth;
    private ImageButton caption_image;



    public static final String DATA = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static Random RANDOM = new Random();

    private String FirstArgs;
    private ImageView imageView;
    private String SecongArgs;
    private Uri resultUrl =null;
    private static final int GALLERYPICK =45;
    private byte[] bytes;
    private Bitmap imageBitmap;
    private ImageButton btnRecord;

    private static final String AUDIO_RECORDER_FILE_EXT_3GP = ".3gp";
    private static final String AUDIO_RECORDER_FILE_EXT_MP4 = ".mp4";
    private static final String AUDIO_RECORDER_FOLDER = "AudioRecorder";
    private MediaRecorder recorder = null;
    private int currentFormat = 0;
    private int output_formats[] = { MediaRecorder.OutputFormat.MPEG_4, MediaRecorder.OutputFormat.THREE_GPP };
    private String file_exts[] = {AUDIO_RECORDER_FILE_EXT_MP4, AUDIO_RECORDER_FILE_EXT_3GP };
    private CollectionReference sendmessageRef;
    private CollectionReference recievemessageRef;
    private String message;

    /** Called when the activity is first created. */


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        messagessre = new ArrayList<Manss>();
        imageView = (ImageView) findViewById(R.id.kooimage);

        datMessage = new DatMessage(ChatActivity.this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarj);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(" ");
        mAuth = FirebaseAuth.getInstance();
        myKey = mAuth.getCurrentUser().getUid();

        btnRecord = (ImageButton) findViewById(R.id.btnrecord);
        btnRecord.setOnClickListener(this);


        othersKey = getIntent().getStringExtra(Constants.OTHERSKEY);

        Utility.requestPermission(ChatActivity.this, Manifest.permission.RECORD_AUDIO);
        Utility.requestPermission(ChatActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        FirstArgs = othersKey+ myKey;



//        othersImage = getIntent().getExtras().get(Constants.OTHERSIMAGE).toString();
       othersName = getIntent().getStringExtra(Constants.OTHERSNAME);
      //  Log.e("mango", "id"+othersImage);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.custom_toolbar, null);
        actionBar.setCustomView(view);


        toolbarimageView = (CircleImageView) findViewById(R.id.toolbar_image);
        toolbarName = (TextView) findViewById(R.id.toolbar_name);
        toolbarTime = (TextView) findViewById(R.id.toolbar_time);
        writemessage = (EditText) findViewById(R.id.editWriteMessage);
        message =writemessage.getText().toString();

         ImageButton sendButton = (ImageButton) findViewById(R.id.btnSend);
      /* sendButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 sendMessages();
             }
         });*/
         ImageButton recordButton = (ImageButton) findViewById(R.id.btnrecord);
        ImageButton selectImagetosend = (ImageButton) findViewById(R.id.btnselectImage);
        ImageButton adddoc = (ImageButton) findViewById(R.id.atach);
        ImageButton caption = (ImageButton) findViewById(R.id.captions_image);


         writemessage.addTextChangedListener(new TextWatcher() {
             @Override
             public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) u{
                 selectImagetosend.setVisibility(View.VISIBLE);
             }

             @Override
             public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                 if(!writemessage.getText().toString().equals("")){

                 //   int height = writemessage.getHeight();
                  //  if(height)
.
                   //  recyclerView.scrollToPosition(messagessre.size() - 1);
                     sendButton.setVisibility(View.VISIBLE);
                     adddoc.setVisibility(View.VISIBLE);
                        recordButton.setVisibility(View.GONE);
                     selectImagetosend.setVisibility(View.GONE);
                     selectImagetosend.setVisibility(View.GONE);
                    // recyclerView.scrollToPosition(messagessre.size() - 1);
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


        toolbarName.setText(othersName);

    //    Toast.makeText(this, "id: " + othersKey, Toast.LENGTH_LONG).show();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerChat);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(ChatActivity.this );
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        messageAdater = new MessageAdapterYes(ChatActivity.this, messagessre);
        recyclerView.setAdapter(messageAdater);
       // getDataFromSQLite();

        new FetchCountTask().execute();


        messageRef = FirebaseFirestore.getInstance();
      //  sendmessageRef = messageRef.collection(Constants.USERS).document(myKey).collection("messages").document(othersKey);
       //  sendmessageRef = messageRef.collection(Constants.USERS+"/"+myKey+"/"+"messages"+"/"+othersKey);
       //  recievemessageRef =  messageRef.collection(Constants.USERS+"/"+othersKey+"/"+"messages"+"/"+myKey);


        sendmessageRef = messageRef.collection("Messages");


      //  recievemessageRef = messageRef.collection("Messages/"+othersKey+"___"+myKey).document();




        GlideApp.with(this).load(othersImage).placeholder(R.drawable.facebook_avatar).error(R.drawable.facebook_avatar)
                .diskCacheStrategy(DiskCacheStrategy.ALL).into(toolbarimageView);
   //    getDataFromSQLite();
      //  fectdatais();
       // getDataFromSQLite();
    }

    public String getFilename(){
        String filepath = Environment.getExternalStorageDirectory().getPath();
        File file = new File(filepath,AUDIO_RECORDER_FOLDER);

        if(!file.exists()){
            file.mkdirs();
        }

        return (file.getAbsolutePath() + "/" + System.currentTimeMillis() +".wav");
    }

    @Override
    public void onClick(View v) {
        int t = v.getId();
            switch (t){
                case R.id.btnSend:
                    sendMessages();
                    break;

                case R.id.btnselectImage:
                   CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).start(ChatActivity.this);
                    break;

                case R.id.btnrecord:

                 //  String filePath = Environment.getExternalStorageDirectory() + "/recorded_audio.3gp";
                    int color = getResources().getColor(R.color.colorPrimaryDark);

                    AndroidAudioRecorder.with(this)
                            // Required
                            .setFilePath(getFilename())
                            .setColor(color)
                            .setRequestCode(REQUEST_RECORD_AUDIO)

                            // Optional
                            .setSource(AudioSource.MIC)
                            .setChannel(AudioChannel.STEREO)
                            .setSampleRate(AudioSampleRate.HZ_48000)
                            .setAutoStart(true)
                            .setKeepDisplayOn(true)

                            // Start recording
                            .record();
                    break;
            }
    }

    private void sendPicture() {
        long time = System.currentTimeMillis();
        String tiy = String.valueOf(time);

        Manss manss = new Manss( bytes,myKey, othersKey,"picture", getTimeStamp()+tiy, false);
       datMessage.createToDoForSender(manss);
       //  getDataFromSQLite();
        messagessre.add(manss);
        messageAdater.notifyDataSetChanged();
        recyclerView.scrollToPosition(messagessre.size() - 1 );
    }

    public boolean isAnagram(String firstWord, String secondWord) {
        char[] word1 = firstWord.replaceAll("[\\s]", "").toCharArray();
        char[] word2 = secondWord.replaceAll("[\\s]", "").toCharArray();
        Arrays.sort(word1);
        Arrays.sort(word2);
        return Arrays.equals(word1, word2);
    }
    private void fectdatairs() {




                recievemessageRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
              @Override
              public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
             if(e != null){

                 Toast.makeText(ChatActivity.this, "Error :"+e, Toast.LENGTH_SHORT).show();
             }


             for(QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots){

                 Manss manss = queryDocumentSnapshot.toObject(Manss.class);
                 String message = manss.getMessage();
                 String time =manss.getTime();
                 String senderId = manss.getSenderId();
                 String recieverid = manss.getRecieverId();
                 String type = manss.getType();

                 Manss user =new Manss(message, time, senderId, recieverid, type);
                 messagessre.add(user);
                 messageAdater.notifyDataSetChanged();
                 recyclerView.scrollToPosition(messagessre.size() - 1);
             }


              }
          });




    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



        if (requestCode == REQUEST_RECORD_AUDIO ) {
            if (resultCode == RESULT_OK) {
                long time = System.currentTimeMillis();
                String tiy = String.valueOf(time);


                Manss manss = new Manss( String.valueOf("/sdcard/AudioRecorder/1533138382440.mp4"),myKey, othersKey,"audio", getTimeStamp()+tiy, false);
                messagessre.add(manss);
                messageAdater.notifyDataSetChanged();
                recyclerView.scrollToPosition(messagessre.size() - 1 );
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Audio was not recorded", Toast.LENGTH_SHORT).show();
            }
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                resultUrl = result.getUri();


                File image_filepath = new File(resultUrl.getPath());

//                String user_id = mAuth.getCurrentUser().getUid();

                try{
                    imageBitmap = new Compressor(ChatActivity.this).setMaxWidth(512).setMaxHeight(512).setQuality(100).
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

    @Override
    protected void onStart() {
        super.onStart();

      //  getDataFromSQLite();
    //    getDataFromSQLite();
    //    fectdatais();
    //    getDataFromSQLite();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser == null){

            Intent intent = new Intent(getApplicationContext(), SignActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }else {

          
            FirebaseFirestore.getInstance().collection(Constants.USERS).document(myKey).update("online", "true");

        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
                recyclerView.scrollToPosition(messagessre.size() - 1);
               // Toast.makeText(ChatActivity.this, "all message acquired", Toast.LENGTH_SHORT).show();
            }
        }.execute();
    }

    public void sendMessages() {




        if (!TextUtils.isEmpty(message)) {

            long time = System.currentTimeMillis();
            String tiy = String.valueOf(time);
              Manss manss = new Manss(message ,String.valueOf(getTimeStamp())+tiy, myKey, othersKey, "text");


           messagessre.add(manss);
            messageAdater.notifyDataSetChanged();
            recyclerView.scrollToPosition(messagessre.size() - 1 );

            datMessage.createToDoForSender(manss);

            writemessage.setText("");




            Map messageMap = new HashMap();
            messageMap.put("message", message);
            messageMap.put("seen", false);
            messageMap.put("type", "text");
            messageMap.put("time", String.valueOf(getTimeStamp())+tiy);
            messageMap.put("senderId", myKey);
            messageMap.put("recieverId", othersKey);

          DocumentReference ref =  messageRef.collection("Messages").document();
            String id = ref.getId();

            Map<String, Object> map = new HashMap();
            map.put(id, messageMap);





            messageRef.collection("Message").document(myKey+"__"+othersKey).set(map, SetOptions.merge())
                    .addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {

                    if(task.isSuccessful()){
                        Toast.makeText(ChatActivity.this, "sent" , Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(ChatActivity.this, "not sent" , Toast.LENGTH_SHORT).show();
                    }
                }
            });



        }else{

            Toast.makeText(ChatActivity.this, "no message" , Toast.LENGTH_SHORT).show();

        }
    }


    public String ramdomString(int len){

        StringBuilder sb = new StringBuilder(len);
        for(int i = 1; i <len; i++){
            sb.append(DATA.charAt(RANDOM.nextInt(DATA.length())));
        }
        return sb.toString();
    }

    class FetchCountTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {


            if(messagessre.size() >0){
                messagessre.clear();
            }
            messagessre.addAll(datMessage.getindivMessages(FirstArgs));
            return null;
        }

        @Override
        public void onPostExecute(Void count) {

            messageAdater.notifyDataSetChanged();
            recyclerView.scrollToPosition(messagessre.size() - 1);

        }
    }

}
