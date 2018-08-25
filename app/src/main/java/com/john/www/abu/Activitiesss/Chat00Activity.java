package com.john.www.abu.Activitiesss;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import com.google.firebase.storage.StorageReference;
import com.john.www.abu.Adapters.ChatFragmentRecycler;
import com.john.www.abu.Adapters.ChatooAdapters;
import com.john.www.abu.DatabaseStuffs.sqlMessage.DatMessage;
import com.john.www.abu.DatabaseStuffs.sqlUsers.User;
import com.john.www.abu.DatabaseStuffs.sqlUsers.DatabaseHelper;
import com.john.www.abu.R;
import com.john.www.abu.Rgistration.SignActivity;
import com.john.www.abu.helper.BottomNavigationViewBehavior;
import com.john.www.abu.helper.BottomNavigationViewHelper;

import com.yarolegovich.lovelydialog.LovelyProgressDialog;

import java.util.ArrayList;


public class Chat00Activity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private FirebaseUser currentUser;
    private ChatooAdapters chatooadapters;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private DatabaseHelper databaseHelper;
    private String myid;
    private RecyclerView recyclerView;

    private StorageReference storageReferenceimage;
    private ArrayList<User> allUsersLists;
    private LovelyProgressDialog dialogFindAllFriend;
    private User allUsersList;

    private ChatFragmentRecycler chatFragmentRecycler;
    private LovelyProgressDialog dialogWait;
    private ProgressDialog progressDialog;
    private SwipeRefreshLayout swipeRefreshLayout;

    private View view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();


        if(currentUser == null){

            Intent intent = new Intent(Chat00Activity.this, SignActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }else {

        }
          setContentView(R.layout.chatactivity);

        FragmentManager fm = getSupportFragmentManager();

        hidefragment( fm.findFragmentById(R.id.fragment));
        view = (View) findViewById(R.id.viewme);





        BottomNAvMethod();
        chatooadapters = new ChatooAdapters(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager.setAdapter(chatooadapters);
        tabLayout.setupWithViewPager(viewPager);



    }


public void hideView(){
        view.setVisibility(View.GONE);
}

    public void ShowView(){
        view.setVisibility(View.VISIBLE);
    }


    private void BottomNAvMethod() {

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomNavigationView.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationViewBehavior());
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.chatbotnav:

                        break;

                    case R.id.abugistbotnav:


                        FragmentManager fm = getSupportFragmentManager();
                        showfragment( fm.findFragmentById(R.id.fragment));
                        break;
                    case R.id.postsbotnav:

                        //  startActivity(new Intent(Chat00Activity.this, PostooActivity.class));

                        Intent  intenty = new Intent(Chat00Activity.this, PostooActivity.class);
                        intenty.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intenty);
                        break;
                    case R.id.faceofabubotnav:

                        //     startActivity(new Intent(Chat00Activity.this, FaceOfAbuActivity.class));

                        Intent  intento = new Intent(Chat00Activity.this, FaceOfAbuActivity.class);
                        intento.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intento);
                        break;
                    case R.id.utilitiesbotnav:

                        //  startActivity(new Intent(Chat00Activity.this, UtilitiesActivity.class));

                        Intent  intentp = new Intent(Chat00Activity.this, UtilitiesActivity.class);
                        intentp.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intentp);
                        break;
                }
                return false;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        overridePendingTransition(0,0);


    }


    @Override
    protected void onStop() {
        super.onStop();

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       /// super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.chatactvitymenu, menu);
        return true;
    }
    public void  hidefragment(final Fragment fragment){

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.hide(fragment);
        ft.commit();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
      int id = item.getItemId();
        if(id== R.id.logout){

            DatMessage datMessage = new DatMessage(Chat00Activity.this);
            datMessage.deleteAll();


            DatabaseHelper databaseHelper = new DatabaseHelper(Chat00Activity.this);
            databaseHelper.deleteContact();
            mAuth.signOut();
            Intent intent = new Intent(Chat00Activity.this, SignActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        return true;
    }
        return super.onOptionsItemSelected(item);
    }









}
