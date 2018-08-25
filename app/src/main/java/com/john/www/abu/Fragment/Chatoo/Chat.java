package com.john.www.abu.Fragment.Chatoo;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.os.Bundle;
import android.support.annotation.NonNull;

import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import com.john.www.abu.Activitiesss.ChatActivity;
import com.john.www.abu.DatabaseStuffs.sqlUsers.DatabaseHelper;
import com.john.www.abu.DatabaseStuffs.sqlUsers.UserDatabase;

import com.john.www.abu.Fragment.PicturePreviewDialog;
import com.john.www.abu.GlideApp;
import com.john.www.abu.R;
import com.john.www.abu.Rgistration.SignActivity;
import com.john.www.abu.helper.BitmapStuffs;
import com.john.www.abu.helper.CheckConnection;
import com.john.www.abu.helper.Constants;
import com.yarolegovich.lovelydialog.LovelyProgressDialog;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by MR AGUDA on 10-Mar-18.
 */

public class Chat  extends Fragment implements SwipeRefreshLayout.OnRefreshListener {


    private RecyclerView recyclerView;
    private FirebaseAuth mAuth;
    private String myid;

    private StorageReference storageReferenceimage;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<UserDatabase> allUsersLists = new ArrayList<>();
   private FirebaseFirestore firestore;
   private CollectionReference collectionReference;
    private LovelyProgressDialog dialogFindAllFriend;

  //  private ChatFragmentRecycler chatFragmentRecycler;
    byte[] photos;
    private LovelyProgressDialog dialogWait;
    private ProgressDialog progressDialog;
    private DatabaseHelper databaseHelper;

    private BitmapStuffs bitmapStuffs;
    private byte[] bytes;
    private String name;
    private String status;
    private String image;
    private String userid;
    private String userChoosenTask;
    private Bitmap bp;
    private FirestoreRecyclerAdapter<UserDatabase, RecyclerForFriendHolder> mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.chats_fragment, container, false);
        databaseHelper = new DatabaseHelper(getContext());
        init(rootView);

        return rootView;
    }

    private void init(View view) {
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        //  String uid  = mAuth.getCurrentUser().getUid();
        if(currentUser == null){

            Intent intent = new Intent(getContext(), SignActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }else {
           /* databaseReference = FirebaseDatabase.getInstance().getReference().child(Constants.USERS).child(uid);

            databaseReference.child("online").setValue("true");*/
        }
        myid = mAuth.getCurrentUser().getUid();


        recyclerView = (RecyclerView) view.findViewById(R.id.myfriendsrecycler);
        //  mygroupRecyler = (RecyclerView)findViewById(R.id.mygroupsrecycler);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                Boolean reachedBottom = !recyclerView.canScrollVertically(1);


                if(dy>0) {

                 //  chat00Activity.hideView();
                }else{
                 //
                    //  chat00Activity.ShowView();
                }

            }
        });
        recyclerView.setHasFixedSize(true);

        mAuth = FirebaseAuth.getInstance();
        String online_user_id = mAuth.getCurrentUser().getUid();

        firestore = FirebaseFirestore.getInstance();
        collectionReference = firestore.collection(Constants.USERS);

        storageReferenceimage = FirebaseStorage.getInstance().getReference().child("profile_images");
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipelayout);
        swipeRefreshLayout.setOnRefreshListener(Chat.this);




        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        allUsersLists = new ArrayList<>();
        bitmapStuffs = new BitmapStuffs();

        dialogFindAllFriend = new LovelyProgressDialog(getContext());




        dialogWait = new LovelyProgressDialog(getContext());

        progressDialog = new ProgressDialog(getContext());

           setUpAdaper2();
        recyclerView.setAdapter(mAdapter);


    }


    private byte[] profileImage(Bitmap b) {
       /* progressDialog.setMessage("i am converting to byte array.....wait");
        progressDialog.show();*/
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.PNG, 100, bos);
      //  progressDialog.cancel();
        return bos.toByteArray();

    }






    public void setUpAdaper2(){

        Query query = collectionReference;



            FirestoreRecyclerOptions<UserDatabase> options = new FirestoreRecyclerOptions.Builder<UserDatabase>().setQuery(query, UserDatabase.class).build();

            mAdapter = new FirestoreRecyclerAdapter<UserDatabase, RecyclerForFriendHolder>(options) {
                @Override
                protected void onBindViewHolder(@NonNull RecyclerForFriendHolder holder, int position, @NonNull UserDatabase userDatabase) {
                    image = userDatabase.getThumbIamgeUri();
                    name = userDatabase.getName();
                    userid = userDatabase.getUserid();
                    status = userDatabase.getStatus();
                    String online = userDatabase.getOnline();
                    if(online.contains("true")){
                        holder.onlineImagewe.setVisibility(View.VISIBLE);
                    }
                    holder.setUsername(userDatabase.getName());
                    holder.setmStatus(userDatabase.getStatus());

                    //  Glide.with(ctx).load(list.get(position).getImage()).crossFade().placeholder(R.drawable.facebook_avatar).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.tvimage);
                    //  UserDatabase userDatabase1 = new UserDatabase(name, status, image, userid);
                    GlideApp.with(getActivity()).load(image).error(R.drawable.facebook_avatar).placeholder(R.drawable.facebook_avatar)
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
                                dialog.show(((FragmentActivity) getContext()).getSupportFragmentManager(), null);
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

                                Intent intent1 = new Intent(getContext(), ChatActivity.class);
                                intent1.putExtra(Constants.OTHERSKEY, userid);
                                intent1.putExtra(Constants.OTHERSIMAGE, image);
                                intent1.putExtra(Constants.OTHERSNAME, name);
                                startActivity(intent1);

                            }

                        }
                    });
                    holder.chatlayout.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            int pos = holder.getAdapterPosition();
                            if (pos != RecyclerView.NO_POSITION) {

                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                                builder.setMessage("Do you want to delete user")
                                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {


                                                userid = userDatabase.getUserid();
                                                databaseHelper.deleteRecord(userid);

                                                /**//* userDatabase.remove(pos);
                                                notifyItemRemoved(pos);
                                                notifyItemRangeChanged(pos, list.size());
                                                notifyDataSetChanged();
                                                */
                                            }
                                        })
                                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();
                                            }
                                        });
                                AlertDialog dialog = builder.create();
                                dialog.setTitle("Delete");
                                dialog.show();
                            }
                            return true;
                        }
                    });
                }

                @NonNull
                @Override
                public RecyclerForFriendHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    return new RecyclerForFriendHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.allusersview, parent, false));

                }
            };




    }


    @Override
    public void onStart() {
        super.onStart();
        if (mAdapter != null) {
            mAdapter.startListening();
        }

    }




    class RecyclerForFriendHolder extends RecyclerView.ViewHolder  {

        private ImageView imageView;
        private CheckBox onlineimage;
        private TextView mName, mTime, mStatus;
        private ImageButton imageButton;
        private LinearLayout imagelayout, chatlayout, buttonlayout;
        CircleImageView tvimage;
        CircleImageView onlineImagewe;


        public RecyclerForFriendHolder(View itemView) {
            super(itemView);


            tvimage = (CircleImageView) itemView.findViewById(R.id.imageview_profile);
            onlineImagewe = (CircleImageView) itemView.findViewById(R.id.online_image);
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


    @Override
    public void onStop() {
        super.onStop();
        if (mAdapter != null) {
            mAdapter.stopListening();
        }
    }



    @Override
    public void onRefresh() {
        CheckConnection checkConnection = new CheckConnection(getContext());
        if (checkConnection.isConnected()) {
            swipeRefreshLayout.setRefreshing(true);
           if(allUsersLists.size() >0) {
               allUsersLists.clear();
           }


         //   fecthdata();
          //  getDataFromSQLite();

            swipeRefreshLayout.setRefreshing(false);
        } else {
            swipeRefreshLayout.setRefreshing(false);
            Toast.makeText(getContext(), "check connection", Toast.LENGTH_SHORT).show();
        }
    }


    private byte[] BitmaptobyteArray(Bitmap bp){

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bp.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }


    public Bitmap StringToBitMap(String encodedString){
        try{
            byte [] encodeByte= Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }catch(Exception e){
            e.getMessage();
            return null;
        }
    }

    public String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream ByteStream=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, ByteStream);
        byte [] b=ByteStream.toByteArray();
        String temp=Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }
}