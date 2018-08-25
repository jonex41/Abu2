package com.john.www.abu.Activitiesss;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.LayerDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.john.www.abu.Adapters.ViewPagerAdapter;
import com.john.www.abu.R;
import com.john.www.abu.Rgistration.SignActivity;
import com.john.www.abu.ShoppingStuffs.ClothesFragment;
import com.john.www.abu.ShoppingStuffs.DatabaseForCart.CartDatabase;
import com.john.www.abu.ShoppingStuffs.FoodFragment;
import com.john.www.abu.ShoppingStuffs.ShoppingActivities.AccessoryFragment;
import com.john.www.abu.ShoppingStuffs.ShoppingActivities.AddGoodsActivity;
import com.john.www.abu.ShoppingStuffs.ShoppingActivities.CartActivity;
import com.john.www.abu.ShoppingStuffs.count.Utils;
import com.john.www.abu.helper.BottomNavigationViewBehavior;
import com.john.www.abu.helper.BottomNavigationViewHelper;
import com.john.www.abu.helper.CheckConnection;
import com.john.www.abu.helper.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MR AGUDA on 10-Mar-18.
 */

public class UtilitiesActivity extends AppCompatActivity {

    private com.google.firebase.auth.FirebaseAuth mAuth;
    private com.google.firebase.auth.FirebaseAuth.AuthStateListener mAuthStateListener;
    private FirebaseUser currentUser;
   // private UtilitiesAdapter chatooadapters;
    private TabLayout tabLayout;
    private ViewPager viewPager;
  private FirebaseFirestore firestore;
  private CollectionReference collectionReference;



    private int mNotificationsCount = 0;

    CartDatabase cartDatabase;
    private String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
/*
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
*/
        setContentView(R.layout.utilitiesactivity);
       Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        cartDatabase = new CartDatabase(getApplicationContext());

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

         id = mAuth.getCurrentUser().getUid();
        FloatingActionButton fab= (FloatingActionButton) findViewById(R.id.fabo);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckConnection checkConnection = new CheckConnection(getApplicationContext());

                if(checkConnection.isConnected()) {

                    Intent intent = new Intent(getApplicationContext(), AddGoodsActivity.class);
                    startActivity(intent);
                }else{

                    Toast.makeText(UtilitiesActivity.this, "Please check your connection!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        FloatingActionButton fabio= (FloatingActionButton) findViewById(R.id.fabio);
        fabio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckConnection checkConnection = new CheckConnection(getApplicationContext());

               startActivity(new Intent(UtilitiesActivity.this, SwapperActivity.class));
            }
        });


        FragmentManager fm = getSupportFragmentManager();

        hidefragment( fm.findFragmentById(R.id.fragment));
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
       BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomNavigationView.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationViewBehavior());
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(4);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.chatbotnav:
                       // startActivity(new Intent(UtilitiesActivity.this, Chat00Activity.class));

                        Intent  intent = new Intent(UtilitiesActivity.this, Chat00Activity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent);
                        break;

                    case R.id.abugistbotnav:


                        FragmentManager fm = getSupportFragmentManager();
                        showfragment( fm.findFragmentById(R.id.fragment));
                        break;
                    case R.id.postsbotnav:

                        Intent  intentjj = new Intent(UtilitiesActivity.this, PostooActivity.class);
                        intentjj.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intentjj);
                        break;
                    case R.id.faceofabubotnav:

                     //   startActivity(new Intent(UtilitiesActivity.this, FaceOfAbuActivity.class));

                        Intent  intentu = new Intent(UtilitiesActivity.this, FaceOfAbuActivity.class);
                        intentu.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intentu);
                        break;
                    case R.id.utilitiesbotnav:



                        break;
                }
                return false;
            }
        });

      //  chatooadapters = new UtilitiesAdapter(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setUpViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


    }

    private void setUpViewPager(ViewPager viewPager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ClothesFragment(), "Clothing");
        adapter.addFragment(new FoodFragment(), "Food");
        adapter.addFragment(new AccessoryFragment(), "Accessories");
        viewPager.setAdapter(adapter);
    }


    private void updateNotificationsBadge(int count) {
        mNotificationsCount = count;

        // force the ActionBar to relayout its MenuItems.
        // onCreateOptionsMenu(Menu) will be called again.
        invalidateOptionsMenu();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_notifications: {
                Intent intent = new Intent(this, CartActivity.class);
                startActivity(intent);
                return true;
            }
            //TODO

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.utility_menu, menu);

        // Get the notifications MenuItem and
        // its LayerDrawable (layer-list)
        MenuItem item = menu.findItem(R.id.action_notifications);
        LayerDrawable icon = (LayerDrawable) item.getIcon();
        //the buyers notification

        // Update LayerDrawable's BadgeDrawable
        Utils.setBadgeCount(this, icon, mNotificationsCount);

        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        new FetchCountTask().execute();
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



    class FetchCountTask extends AsyncTask<Void, Void, Integer> {

        @Override
        protected Integer doInBackground(Void... params) {


            int counts = (int) cartDatabase.getProfilesCount();
            return counts;
        }

        @Override
        public void onPostExecute(Integer count) {
            updateNotificationsBadge(count);
        }
    }
}
