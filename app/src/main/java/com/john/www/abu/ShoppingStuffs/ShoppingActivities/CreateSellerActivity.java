package com.john.www.abu.ShoppingStuffs.ShoppingActivities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.john.www.abu.Activitiesss.ChatActivity;
import com.john.www.abu.Adapters.MessageAdapterYes;
import com.john.www.abu.DatabaseStuffs.sqlMessage.Manss;
import com.john.www.abu.DatabaseStuffs.sqlUsers.UserForPost;
import com.john.www.abu.GlideApp;
import com.john.www.abu.PostStuffs.utils.Constants;
import com.john.www.abu.R;
import com.john.www.abu.ShoppingStuffs.Models.ListOfShop;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cafe.adriel.androidaudiorecorder.AndroidAudioRecorder;
import cafe.adriel.androidaudiorecorder.model.AudioChannel;
import cafe.adriel.androidaudiorecorder.model.AudioSampleRate;
import cafe.adriel.androidaudiorecorder.model.AudioSource;
import de.hdodenhof.circleimageview.CircleImageView;

public class  CreateSellerActivity extends ChatActivity {

    private TextView sellername, sellerdepartment;
    private CircleImageView sellerImage;
    private String sellerId;
    private EditText writemessage;
    private RecyclerView recyclerView;
    private MessageAdapterYes messageAdater;
    private List<Manss> messagessre = new ArrayList<Manss>();
    private LinearLayoutManager linearLayoutManager;
    private final int REQUEST_RECORD_AUDIO=23;
    private String userid;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_customer);
        userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        firestore = FirebaseFirestore.getInstance();

        sellername = (TextView) findViewById(R.id.sellername);
        sellerdepartment = (TextView) findViewById(R.id.sellerdepartment);
        sellerImage = (CircleImageView) findViewById(R.id.sellerimage);
        sellerId = getIntent().getStringExtra("sellerid");
        writemessage = (EditText) findViewById(R.id.editWriteMessage);
        ImageButton sendButton = (ImageButton) findViewById(R.id.btnSend);
        ImageButton recordButton = (ImageButton) findViewById(R.id.btnrecord);
        ImageButton selectImagetosend = (ImageButton) findViewById(R.id.btnselectImage);
        ImageButton adddoc = (ImageButton) findViewById(R.id.atach);
        ImageButton caption = (ImageButton) findViewById(R.id.captions_image);
        sendButton.setOnClickListener(this);
        selectImagetosend.setOnClickListener(this);

        FirebaseFirestore.getInstance().collection("Buyers").document(sellerId).collection(userid).addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {

                for(QueryDocumentSnapshot query : queryDocumentSnapshots){

                    Manss manss = query.toObject(Manss.class);
                    messagessre.add(manss);
                    messageAdater.notifyDataSetChanged();
                    recyclerView.scrollToPosition(messagessre.size() - 1);

                }

            }
        });




        //    Toast.makeText(this, "id: " + othersKey, Toast.LENGTH_LONG).show();
        recyclerView = (RecyclerView) findViewById(R.id.sellerrecyclerview);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this );
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        messageAdater = new MessageAdapterYes(CreateSellerActivity.this, messagessre);
        recyclerView.setAdapter(messageAdater);

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
                    recyclerView.scrollToPosition(messagessre.size() - 1);
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





        FirebaseFirestore.getInstance().collection("Users").document(sellerId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                DocumentSnapshot doc = task.getResult();

                UserForPost userForPost = doc.toObject(UserForPost.class);

                sellername.setText(userForPost.getName());
                sellerdepartment.setText(userForPost.getDepartment());
                GlideApp.with(getApplicationContext()).load(userForPost.getImage()).into(sellerImage);



            }
        });
    }

    @Override
    public void onClick(View v) {
        int t = v.getId();
        switch (t){

            case R.id.btnSend:
                sendMessages();
                break;
            case R.id.btnselectImage:
                CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).start(CreateSellerActivity.this);
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

    @Override
    public void sendMessages() {
        String message = writemessage.getText().toString().trim();


        if (!TextUtils.isEmpty(message)) {

            long time = System.currentTimeMillis();
            String tiy = String.valueOf(time);

            Manss manss = new Manss(message ,String.valueOf(getTimeStamp())+tiy, userid, sellerId, "text");




            messagessre.add(manss);
            messageAdater.notifyDataSetChanged();
            recyclerView.scrollToPosition(messagessre.size() - 1 );

          // datMessage.createToDoForSender(manss);






            Map messageMap = new HashMap();
            messageMap.put("message", message);
            messageMap.put("seen", false);
            messageMap.put("type", "text");
            messageMap.put("time", String.valueOf(getTimeStamp())+tiy);
            messageMap.put("senderId", userid);
            messageMap.put("recieverId", sellerId);





            FirebaseFirestore.getInstance().collection("Buyers").document(sellerId).collection(userid).add(messageMap).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if(task.isSuccessful()){
                        Toast.makeText(CreateSellerActivity.this, "Message sent.", Toast.LENGTH_SHORT).show();

                    }else{
                        writemessage.setText("");
                        Toast.makeText(CreateSellerActivity.this, "Unable to send message.", Toast.LENGTH_SHORT).show();
                    }
                }
            });




        }else{

            Toast.makeText(CreateSellerActivity.this, "no message" , Toast.LENGTH_SHORT).show();

        }
    }
}
