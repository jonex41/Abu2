package com.john.www.abu.Activitiesss;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.john.www.abu.DatabaseStuffs.sqlUsers.UserDatabase;
import com.john.www.abu.GlideApp;
import com.john.www.abu.R;
import com.john.www.abu.Rgistration.SignActivity;
import com.john.www.abu.helper.Constants;

import java.util.Map;


import javax.annotation.Nullable;


    /**
     * Created by MR AGUDA on 15-Nov-17.
     */

    public class FriendFromPost extends AppCompatActivity implements  View.OnClickListener{

        private static final int REQUEST_CAMERA = 45;
        private ImageView profileimage;
        private TextView profilestatus;
        private TextView profilename;

        private Button changeimage;
        private Button changestatus;
        private static int  GALLERYPICK =1;
        private String image;




        private Bitmap imageBitmap = null;
        private FirebaseFirestore firestore;
        private CollectionReference collectionReference;
        private String userChoosenTask;
        private Bitmap compressedImageFile;
        private Uri resultUrl = null;
        UserDatabase userDatabase;

        private String downloadUrlReal = " ";
        private String downloadUrlthumb=" ";
        private String online_user_id;

        private TextView  favourites_hobbies, relationship_statud, best_food,
                what_i_like_in_either, lovedoid_but_nasty, knows_u_bests, love_or_money, apologize_permission,
                type_of_music,most_loved_celebrity, worst_habbit, favourite_sport, chores_hate,
                things_ihate_opposite_sex,things_idoto_achieve_goal;
        private Button followers, following, models;
        private ImageView iamgeview;
        private LinearLayout linear_name_status;

        private DocumentReference docref;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setContentView(R.layout.profile_activity);



            initTextviews();

           online_user_id = getIntent().getStringExtra("postUser");
            Toolbar mToobar = (Toolbar) findViewById(R.id.toolbarpp);
            setSupportActionBar(mToobar);

            getSupportActionBar().setTitle(" ");


            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            userDatabase = new UserDatabase();
            firestore = FirebaseFirestore.getInstance();



            collectionReference = firestore.collection(Constants.USERS);
            docref = firestore.document(Constants.USERS+"/"+online_user_id);




            profileimage = (ImageView) findViewById(R.id.imageview_profile11);
            profilename = (TextView) findViewById(R.id.myusername);
            profilestatus = (TextView) findViewById(R.id.mystatus);
            iamgeview = (ImageView) findViewById(R.id.imageview_profile11);







        }

        private void   initTextviews(){

            linear_name_status = (LinearLayout) findViewById(R.id.layout_for_name_status);
            linear_name_status.setOnClickListener(this);
            followers = (Button) findViewById(R.id.followers);
            following = (Button) findViewById(R.id.following);
            models = (Button) findViewById(R.id.models);
            followers.setOnClickListener(this);
            following.setOnClickListener(this);
            models.setOnClickListener(this);


            favourites_hobbies = (TextView) findViewById(R.id.whatilikemostreal);
            relationship_statud = (TextView) findViewById(R.id.relationshipreal);
            best_food = (TextView) findViewById(R.id.foodreal);
            what_i_like_in_either = (TextView) findViewById(R.id.maleorfemalereal);
            lovedoid_but_nasty = (TextView) findViewById(R.id.nastylovereal);
            knows_u_bests = (TextView) findViewById(R.id.whoknowsreal);
            love_or_money = (TextView) findViewById(R.id.moneyreal);
            apologize_permission = (TextView) findViewById(R.id.apologizereal);
            type_of_music = (TextView) findViewById(R.id.musicreal);
            most_loved_celebrity = (TextView) findViewById(R.id.celebrityreal);
            worst_habbit = (TextView) findViewById(R.id.habbitreal);
            favourite_sport = (TextView) findViewById(R.id.sportreal);
            chores_hate = (TextView) findViewById(R.id.tasktodoreal);
            things_ihate_opposite_sex = (TextView) findViewById(R.id.hateinoppostereal);
            things_idoto_achieve_goal = (TextView) findViewById(R.id.things_havebeen_doing);




        }
        @Override
        protected void onStart() {
            super.onStart();

            DocumentReference documentReference = firestore.collection("Users").document(online_user_id);
            documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                    Map<String, Object> packs = (Map<String, Object>) documentSnapshot.get("Profile");

                    if(packs != null) {
                        summitthis(packs);
                    }





                }
            });
            FirebaseFirestore.getInstance().collection(Constants.USERS).document(online_user_id)
                    .addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                            String name = documentSnapshot.getString(Constants.PROFILENAME);
                            String status = documentSnapshot.getString(Constants.PROFILESTATUS);
                            image = documentSnapshot.getString(Constants.PROFILEIMAGE);


                            profilename.setText(name);
                            profilestatus.setText(status);
                            if(!image.equals(Constants.DEFUALTIMAGE)) {
                                GlideApp.with(getApplicationContext()).load(image).placeholder(R.drawable.facebook_avatar).diskCacheStrategy(DiskCacheStrategy.ALL).into(profileimage);


                            }
                        }
                    });

            GlideApp.with(getApplicationContext()).load(image).placeholder(R.drawable.facebook_avatar).diskCacheStrategy(DiskCacheStrategy.ALL).into(profileimage);


        }

        private void summitthis(Map<String, Object> packs) {

            String favourites =(String) packs.get("favourite_hobbies");
            String relationship =(String) packs.get("relationship");
            String best_foode = (String) packs.get("best_food");
            String like_most = (String) packs.get("like_most");
            String love_nasty = (String) packs.get("love_nasty");
            String kowns_u_best =(String) packs.get("Kowns_u_best");
            String love_money = (String) packs.get("love_money");
            String apology_permission =(String) packs.get("apology_permission");
            String type_music = (String) packs.get("type_music");
            String most_celebrity =(String) packs.get("most_celebrity");
            String worsts_habbit = (String) packs.get("worsts_habbit");
            String sport_favourite = (String) packs.get("sport_favourite");
            String chores_hates = (String) packs.get("chores_hate");
            String hate_opp_sex = (String) packs.get("hate_opp_sex");
            String thingsdoing_goal = (String) packs.get("thingsdoing_goal");
            if(!TextUtils.isEmpty(relationship)) {
                relationship_statud.setText(relationship);
            }
            if(!TextUtils.isEmpty(favourites)) {
                favourites_hobbies.setText(favourites);
            }
            if(!TextUtils.isEmpty(kowns_u_best)) {
                knows_u_bests.setText(kowns_u_best);
            }

            if(!TextUtils.isEmpty(worsts_habbit)) {
                worst_habbit.setText(worsts_habbit);
            }
            if(!TextUtils.isEmpty(thingsdoing_goal)) {
                things_idoto_achieve_goal.setText(thingsdoing_goal);
            }if(!TextUtils.isEmpty(hate_opp_sex)) {
                things_ihate_opposite_sex.setText(hate_opp_sex);
            }if(!TextUtils.isEmpty(chores_hates)) {
                chores_hate.setText(chores_hates);
            }if(!TextUtils.isEmpty(sport_favourite)) {
                favourite_sport.setText(sport_favourite);
            }if(!TextUtils.isEmpty(most_celebrity)) {
                most_loved_celebrity.setText(most_celebrity);
            }if(!TextUtils.isEmpty(love_money)) {
                love_or_money.setText(love_money);
            }
            if(!TextUtils.isEmpty(love_nasty)) {
                lovedoid_but_nasty.setText(love_nasty);
            }if(!TextUtils.isEmpty(apology_permission)) {
                apologize_permission.setText(apology_permission);
            }
            if(!TextUtils.isEmpty(type_music)) {
                type_of_music.setText(type_music);
            }if(!TextUtils.isEmpty(like_most)) {
                what_i_like_in_either.setText(like_most);
            }if(!TextUtils.isEmpty(best_foode)) {
                best_food.setText(best_foode);
            }


        }


        @Override
        protected void onStop() {
            super.onStop();


        }









        @Override
        public void onClick(View v) {
            int t = v.getId();
            switch (t){

                case R.id.imageview_profile11:
                   // dialogStuffs();


                    break;

            }
        }





    }

