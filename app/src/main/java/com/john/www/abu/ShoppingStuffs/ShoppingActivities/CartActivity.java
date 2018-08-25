package com.john.www.abu.ShoppingStuffs.ShoppingActivities;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.john.www.abu.Activitiesss.ChatActivity;
import com.john.www.abu.Adapters.CartAapter;
import com.john.www.abu.Adapters.MessageAdapterYes;
import com.john.www.abu.DatabaseStuffs.sqlUsers.UserDatabase;
import com.john.www.abu.Fragment.Chatoo.Chat;
import com.john.www.abu.Fragment.PicturePreviewDialog;
import com.john.www.abu.GlideApp;
import com.john.www.abu.R;
import com.john.www.abu.ShoppingStuffs.DatabaseForCart.CartDatabase;
import com.john.www.abu.ShoppingStuffs.DetailShopActivity;
import com.john.www.abu.ShoppingStuffs.Models.ListOfShop;
import com.john.www.abu.ShoppingStuffs.ShopListFragment;
import com.john.www.abu.helper.Constants;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity{


    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private CartAapter cartAapter;
    private List<ListOfShop> listOfShops = new ArrayList<ListOfShop>();
    private CartDatabase cartDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_activity);

            cartDatabase = new CartDatabase(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toobarcart);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);



        recyclerView = (RecyclerView) findViewById(R.id.cart_recycler);
        recyclerView.setHasFixedSize(true);
        cartAapter = new CartAapter(getApplicationContext(), listOfShops );
        linearLayoutManager = new LinearLayoutManager(this );

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(cartAapter);
        new FetchCountTask().execute();

    }
    class FetchCountTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            //int counts = (int) cartDatabase.getProfilesCount();
            if(listOfShops.size() >0){
                listOfShops.clear();
            }
            listOfShops.addAll(cartDatabase.getAllRecords());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            cartAapter.notifyDataSetChanged();
            recyclerView.scrollToPosition(listOfShops.size() - 1);

        }
    }
}
