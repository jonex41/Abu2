package com.john.www.abu.Activitiesss;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.john.www.abu.Adapters.PostssAdapter;
import com.john.www.abu.R;
import com.john.www.abu.Rgistration.SignActivity;
import com.john.www.abu.helper.BottomNavigationViewBehavior;
import com.john.www.abu.helper.BottomNavigationViewHelper;
import com.john.www.abu.helper.Constants;


/**
 * Created by MR AGUDA on 10-Mar-18.
 */

public class PostooActivity  extends AppCompatActivity {


    private com.google.firebase.auth.FirebaseAuth mAuth;
    private com.google.firebase.auth.FirebaseAuth.AuthStateListener mAuthStateListener;
    private FirebaseUser currentUser;
    private PostssAdapter chatooadapters;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


      /*  requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
*/

        setContentView(R.layout.postssactivity);

        FragmentManager fm = getSupportFragmentManager();

        hidefragment( fm.findFragmentById(R.id.fragment));
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        id = mAuth.getCurrentUser().getUid();




        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomNavigationView.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationViewBehavior());
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.chatbotnav:
                       // startActivity(new Intent(PostooActivity.this, Chat00Activity.class));
                        Intent  intent = new Intent(PostooActivity.this, Chat00Activity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent);
                        break;

                    case R.id.abugistbotnav:
                      //  startActivity(new Intent(PostooActivity.this, AbuGistActivity.class));
                       /* Intent  intent2 = new Intent(PostooActivity.this, AbuGistActivity.class);
                        intent2.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent2);*/

                        FragmentManager fm = getSupportFragmentManager();
                        showfragment( fm.findFragmentById(R.id.fragment));
                        break;
                    case R.id.postsbotnav:


                        break;
                    case R.id.faceofabubotnav:

                      //  startActivity(new Intent(PostooActivity.this, FaceOfAbuActivity.class));
                        Intent  intent3 = new Intent(PostooActivity.this, FaceOfAbuActivity.class);
                        intent3.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent3);
                        break;
                    case R.id.utilitiesbotnav:

                      //  startActivity(new Intent(PostooActivity.this, UtilitiesActivity.class));

                        Intent  intent4 = new Intent(PostooActivity.this, UtilitiesActivity.class);
                        intent4.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent4);

                        break;
                }
                return false;
            }
        });




        chatooadapters = new PostssAdapter(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager.setAdapter(chatooadapters);
        tabLayout.setupWithViewPager(viewPager);


    }

    @Override
    protected void onStart() {
        super.onStart();

        overridePendingTransition(0,0);


        if(currentUser == null){

            Intent intent = new Intent(getApplicationContext(), SignActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }else {
            FirebaseFirestore.getInstance().collection(Constants.USERS).document(id).update(Constants.ONLINE, "true");
        }
    }

    public void  hidefragment(final Fragment fragment){

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        ft.hide(fragment);
        ft.commit();
    }
    private void showfragment(Fragment fragmentById) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.show(fragmentById);
        ft.commit();
    }
    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0,0);
    }
}
