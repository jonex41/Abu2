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

import com.google.firebase.firestore.FirebaseFirestore;
import com.john.www.abu.Adapters.ViewPagerAdapter;
import com.john.www.abu.Fragment.AbuGIstooo.Artist;
import com.john.www.abu.Fragment.AbuGIstooo.FOA;
import com.john.www.abu.Fragment.AbuGIstooo.Gist;
import com.john.www.abu.R;
import com.john.www.abu.Rgistration.SignActivity;
import com.john.www.abu.helper.BottomNavigationViewBehavior;
import com.john.www.abu.helper.BottomNavigationViewHelper;
import com.john.www.abu.helper.Constants;

/**
 * Created by MR AGUDA on 10-Mar-18.
 */

public class FaceOfAbuActivity  extends AppCompatActivity {

    private com.google.firebase.auth.FirebaseAuth mAuth;
    private com.google.firebase.auth.FirebaseAuth.AuthStateListener mAuthStateListener;
    private FirebaseUser currentUser;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    String id;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

      /*  requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
*/


        setContentView(R.layout.faceofabuactivity);
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
        MenuItem menuItem = menu.getItem(3);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.chatbotnav:
                      //  startActivity(new Intent(FaceOfAbuActivity.this, Chat00Activity.class));

                        Intent  intent2 = new Intent(FaceOfAbuActivity.this, Chat00Activity.class);
                        intent2.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent2);
                        break;

                    case R.id.abugistbotnav:

                        FragmentManager fm = getSupportFragmentManager();
                        showfragment( fm.findFragmentById(R.id.fragment));
                        break;
                    case R.id.postsbotnav:

                      //  startActivity (new Intent(FaceOfAbuActivity.this, PostooActivity.class));

                        Intent  intent5 = new Intent(FaceOfAbuActivity.this, PostooActivity.class);
                        intent5.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent5);
                        break;
                    case R.id.faceofabubotnav:


                        break;
                    case R.id.utilitiesbotnav:

                     //   startActivity(new Intent(FaceOfAbuActivity.this, UtilitiesActivity.class));

                        Intent  intent6 = new Intent(FaceOfAbuActivity.this, UtilitiesActivity.class);
                        intent6.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent6);
                        break;
                }
                return false;
            }
        });


        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);

       setUpViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setUpViewPager(ViewPager viewPager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new Gist(), "Talk am");
        adapter.addFragment(new FOA(), "FOA");
        adapter.addFragment(new Artist(), "Artists");


        viewPager.setAdapter(adapter);
    }
    @Override
    protected void onStart() {
        super.onStart();

        overridePendingTransition(0,0);

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser == null){

            Intent intent = new Intent(getApplicationContext(), SignActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }else {


            FirebaseFirestore.getInstance().collection(Constants.USERS).document(id).update(Constants.ONLINE, "true");

        }
    }
    private void showfragment(Fragment fragmentById) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.show(fragmentById);
        ft.commit();
    }

    public void  hidefragment(final Fragment fragment){

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        ft.hide(fragment);
        ft.commit();
    }
    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0,0);
    }
}
