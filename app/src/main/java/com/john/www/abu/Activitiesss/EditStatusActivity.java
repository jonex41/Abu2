package com.john.www.abu.Activitiesss;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.firestore.CollectionReference;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.john.www.abu.R;
import com.john.www.abu.helper.Constants;

import java.util.HashMap;
import java.util.Map;

public class EditStatusActivity extends AppCompatActivity implements View.OnClickListener{


    private EditText favourites_hobbies, relationship_statud, best_food,
            what_i_like_in_either, lovedoid_but_nasty, knows_u_bests, love_or_money, apologize_permission,
            type_of_music,most_loved_celebrity, worst_habbit, favourite_sport, chores_hate,
            things_ihate_opposite_sex,things_idoto_achieve_goal;

    private ImageButton btnfavourites_hobbies, btnrelationship_statud, btnbest_food,
            btnwhat_i_like_in_either, btnlovedoid_but_nasty, btnknows_u_bests, btnlove_or_money, btnapologize_permission,
            btntype_of_music,btnmost_loved_celebrity, btnworst_habbit, btnfavourite_sport, btnchores_hate,
            btnthings_ihate_opposite_sex,btnthings_idoto_achieve_goal;

    private FirebaseFirestore firestore;

    private CollectionReference documentReference;

    private ProgressDialog progressDialog;
    private String myId;
    private Button update_all;
    private String thingsdoinggoal;
    private String favourite;
    private String relationship;
    private String whatilikemost;
    private String best_foodgn;
    private String lovenasty;
    private String knowsubest;
    private String lovemoney;
    private String apologize;
    private String type_music;
    private String mostcelebrity;
    private String worsthabbit;
    private String choreshate;
    private String sportfavourite;
    private String hateoppositesex;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile_activity);
        progressDialog = new ProgressDialog(this);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
         myId = mAuth.getCurrentUser().getUid();
        firestore = FirebaseFirestore.getInstance();

        update_all = (Button) findViewById(R.id.update_all);
        update_all.setOnClickListener(this);

     //   documentReference = firestore.collection(Constants.USERS).document(myId).collection(Constants.PROFILE);

        initTextviews();
        initbtn();
    }

    private void initbtn() {
        btnfavourites_hobbies = (ImageButton) findViewById(R.id.r1);
        btnrelationship_statud = (ImageButton) findViewById(R.id.r2);
        btnbest_food = (ImageButton) findViewById(R.id.r3);
        btnwhat_i_like_in_either = (ImageButton) findViewById(R.id.r4);
        btnlovedoid_but_nasty = (ImageButton) findViewById(R.id.r5);
        btnknows_u_bests = (ImageButton) findViewById(R.id.r6);
        btnlove_or_money = (ImageButton) findViewById(R.id.r7);
        btnapologize_permission = (ImageButton) findViewById(R.id.r8);
        btntype_of_music = (ImageButton) findViewById(R.id.r9);
        btnmost_loved_celebrity = (ImageButton) findViewById(R.id.r10);
        btnworst_habbit = (ImageButton) findViewById(R.id.r11);
        btnfavourite_sport = (ImageButton) findViewById(R.id.r12);
        btnchores_hate = (ImageButton) findViewById(R.id.r13);
        btnthings_ihate_opposite_sex = (ImageButton) findViewById(R.id.r14);
        btnthings_idoto_achieve_goal = (ImageButton) findViewById(R.id.r15);

        btnfavourites_hobbies.setOnClickListener(this);
        btnrelationship_statud.setOnClickListener(this);
        btnbest_food.setOnClickListener(this);
        btnwhat_i_like_in_either.setOnClickListener(this);
        btnlovedoid_but_nasty.setOnClickListener(this);
        btnknows_u_bests.setOnClickListener(this);
        btnlove_or_money.setOnClickListener(this);
        btnapologize_permission.setOnClickListener(this);
        btntype_of_music.setOnClickListener(this);
        btnmost_loved_celebrity.setOnClickListener(this);
        btnworst_habbit.setOnClickListener(this);
        btnfavourite_sport.setOnClickListener(this);
        btnchores_hate.setOnClickListener(this);
        btnthings_ihate_opposite_sex.setOnClickListener(this);
        btnthings_idoto_achieve_goal.setOnClickListener(this);
        btnchores_hate.setOnClickListener(this);


    }

    private void   initTextviews(){


        favourites_hobbies = (EditText) findViewById(R.id.whatilikemostreal);
        relationship_statud = (EditText) findViewById(R.id.relationshipreal);
        best_food = (EditText) findViewById(R.id.foodreal);
        what_i_like_in_either = (EditText) findViewById(R.id.maleorfemalereal);
        lovedoid_but_nasty = (EditText) findViewById(R.id.nastylovereal);
        knows_u_bests = (EditText) findViewById(R.id.whoknowsreal);
        love_or_money = (EditText) findViewById(R.id.moneyreal);
        apologize_permission = (EditText) findViewById(R.id.apologizereal);
        type_of_music = (EditText) findViewById(R.id.musicreal);
        most_loved_celebrity = (EditText) findViewById(R.id.celebrityreal);
        worst_habbit = (EditText) findViewById(R.id.habbitreal);
        favourite_sport = (EditText) findViewById(R.id.sportreal);
        chores_hate = (EditText) findViewById(R.id.tasktodoreal);
        things_ihate_opposite_sex = (EditText) findViewById(R.id.hateinoppostereal);
        things_idoto_achieve_goal = (EditText) findViewById(R.id.things_havebeen_doing);

        favourite = favourites_hobbies.getText().toString();
        relationship = relationship_statud.getText().toString();
        best_foodgn = best_food.getText().toString();
        whatilikemost = what_i_like_in_either.getText().toString();
        lovenasty = lovedoid_but_nasty.getText().toString();
        knowsubest = knows_u_bests.getText().toString();
        lovemoney = love_or_money.getText().toString();
        apologize = apologize_permission.getText().toString();
        mostcelebrity = most_loved_celebrity.getText().toString();
        type_music = type_of_music.getText().toString();
        worsthabbit = worst_habbit.getText().toString();
        sportfavourite = favourite_sport.getText().toString();
        choreshate = chores_hate.getText().toString();
        hateoppositesex= things_ihate_opposite_sex.getText().toString();
        thingsdoinggoal = things_idoto_achieve_goal.getText().toString();




    }

    private void putOnline(String name, String other){
//        documentReference.document(name).update(other, SetOptions.merge());
        progressDialog.setMessage("Uploading...");
        Map<String, Object> map = new HashMap<>();
        map.put(name, other);

        Map<String, Object> map1 = new HashMap<>();

        map1.put(Constants.PROFILE, map);
                FirebaseFirestore.getInstance().collection(Constants.USERS).document(myId).set(map1, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            if(progressDialog.isShowing()){
                                progressDialog.dismiss();
                            }
                            Toast.makeText(EditStatusActivity.this, "Profile updated", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(EditStatusActivity.this, "Unable to upload profile", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


                      /*  .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if(task.isSuccessful()){
                            if(progressDialog.isShowing()){
                                progressDialog.dismiss();
                            }
                            Toast.makeText(EditStatusActivity.this, "Profile updated", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(EditStatusActivity.this, "Unable to upload profile", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
*/

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.r1:

                if(!TextUtils.isEmpty(favourite)) {
                    progressDialog.show();
                    progressDialog.setIndeterminate(true);
                    putOnline("favourite_hobbies", favourite);
                }else {
                    Toast.makeText(this, "Empty", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.r2:

                if(!TextUtils.isEmpty(relationship)) {
                    progressDialog.show();
                    progressDialog.setIndeterminate(true);
                    putOnline("relationship", relationship);

                }else {
                    Toast.makeText(this, "Empty", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.r3:

                if(!TextUtils.isEmpty(best_foodgn)) {
                    progressDialog.show();
                    progressDialog.setIndeterminate(true);
                    putOnline("best_food", best_foodgn);
                }else {
                    Toast.makeText(this, "Empty", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.r4:

                if(!TextUtils.isEmpty(whatilikemost)) {
                    progressDialog.show();
                    progressDialog.setIndeterminate(true);

                    putOnline("like_most", whatilikemost);
                }else {
                    Toast.makeText(this, "Empty", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.r5:

                if(!TextUtils.isEmpty(lovenasty)) {
                    progressDialog.show();
                    progressDialog.setIndeterminate(true);
                    putOnline("love_nasty", lovenasty);
                }else {
                    Toast.makeText(this, "Empty", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.r6:

                if(!TextUtils.isEmpty(knowsubest)) {
                    progressDialog.show();
                    progressDialog.setIndeterminate(true);
                    putOnline("Kowns_u_best", knowsubest);
                }else {
                    Toast.makeText(this, "Empty", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.r7:

                if(!TextUtils.isEmpty(lovemoney)) {
                    progressDialog.show();
                    progressDialog.setIndeterminate(true);
                    putOnline("love_money", lovemoney);
                }else {
                    Toast.makeText(this, "Empty", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.r8:

                if(!TextUtils.isEmpty(apologize)) {
                    progressDialog.show();
                    progressDialog.setIndeterminate(true);
                    putOnline("apology_permission", apologize);
                }else {
                    Toast.makeText(this, "Empty", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.r9:

                if(!TextUtils.isEmpty(type_music)) {
                    progressDialog.show();
                    progressDialog.setIndeterminate(true);
                    putOnline("type_music", type_music);

                }else {
                    Toast.makeText(this, "Empty", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.r10:

                if(!TextUtils.isEmpty(mostcelebrity)) {
                    progressDialog.show();
                    progressDialog.setIndeterminate(true);
                    putOnline("most_celebrity", mostcelebrity);

                }else {
                    Toast.makeText(this, "Empty", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.r11:

                if(!TextUtils.isEmpty(worsthabbit)) {
                    progressDialog.show();
                    progressDialog.setIndeterminate(true);
                    putOnline("worsts_habbit", worsthabbit);

                }else {
                    Toast.makeText(this, "Empty", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.r12:

                if(!TextUtils.isEmpty(sportfavourite)) {
                    progressDialog.show();
                    progressDialog.setIndeterminate(true);
                    putOnline("sport_favourite", sportfavourite);

                }else {
                    Toast.makeText(this, "Empty", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.r13:

                if(!TextUtils.isEmpty(choreshate)) {
                    progressDialog.show();
                    progressDialog.setIndeterminate(true);
                    putOnline("chores_hate", choreshate);

                }else {
                    Toast.makeText(this, "Empty", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.r14:

                if(!TextUtils.isEmpty(hateoppositesex)) {
                    progressDialog.show();
                    progressDialog.setIndeterminate(true);
                    putOnline("hate_opp_sex", hateoppositesex);

                }else {
                    Toast.makeText(this, "Empty", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.r15:

                if(!TextUtils.isEmpty(thingsdoinggoal)) {
                    progressDialog.show();
                    progressDialog.setIndeterminate(true);
                    putOnline("type_music", type_music);
                }else {
                    Toast.makeText(this, "Empty", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.update_all:
                if(!TextUtils.isEmpty(thingsdoinggoal)&&!TextUtils.isEmpty(hateoppositesex)&& !TextUtils.isEmpty(choreshate)&&!TextUtils.isEmpty(sportfavourite)
                        &&!TextUtils.isEmpty(worsthabbit)&&!TextUtils.isEmpty(mostcelebrity)&&!TextUtils.isEmpty(type_music)&&!TextUtils.isEmpty(apologize)
                        &&!TextUtils.isEmpty(lovemoney)&&!TextUtils.isEmpty(knowsubest)&&!TextUtils.isEmpty(lovenasty)&&!TextUtils.isEmpty(whatilikemost)
                        &&!TextUtils.isEmpty(best_foodgn)&&!TextUtils.isEmpty(relationship)&&!TextUtils.isEmpty(favourite)){
                    progressDialog.show();
                    progressDialog.setMessage("Uploading...");
                    progressDialog.setIndeterminate(true);
                    putOnline("favourite_hobbies", favourite);
                    putOnline("relationship", relationship);

                    putOnline("best_food", best_foodgn);

                    putOnline("like_most", whatilikemost);

                    putOnline("love_nasty", lovenasty);

                    putOnline("Kowns_u_best", knowsubest);

                    putOnline("love_money", lovemoney);

                    putOnline("apology_permission", apologize);

                    putOnline("type_music", type_music);

                    putOnline("most_celebrity", mostcelebrity);

                    putOnline("worsts_habbit", worsthabbit);

                    putOnline("sport_favourite", sportfavourite);

                    putOnline("chores_hate", choreshate);

                    putOnline("hate_opp_sex", hateoppositesex);

                    putOnline("thingsdoing_goal", thingsdoinggoal);

                    putOnline("type_music", type_music);

                    if(progressDialog.isShowing()){
                        progressDialog.dismiss();
                    }

                }

                break;


        }
    }
}
