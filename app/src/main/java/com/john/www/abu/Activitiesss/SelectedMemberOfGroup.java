package com.john.www.abu.Activitiesss;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.john.www.abu.Adapters.NewSelectedAdapter;
import com.john.www.abu.Adapters.UserAdapterSelecting;
import com.john.www.abu.DatabaseStuffs.sqlUsers.DatabaseHelper;
import com.john.www.abu.DatabaseStuffs.sqlUsers.UserDatabase;
import com.john.www.abu.Interfaces.OnItemclicklistener;
import com.john.www.abu.R;
import com.john.www.abu.helper.Constants;

import java.util.ArrayList;

public class SelectedMemberOfGroup extends AppCompatActivity implements OnItemclicklistener{

    private RecyclerView recyclerView, recycler2;
    private UserAdapterSelecting chatFragmentRecycler;
    private NewSelectedAdapter newSelectedAdapter;
    private ArrayList<UserDatabase> allUsersLists = new ArrayList<>();
    private DatabaseHelper databaseHelper;
    private CheckBox checkBox;
    private ArrayList<UserDatabase> groupmember = new ArrayList<>();
     int counter = 0;
    private TextView toobarCounter;
    private ImageView okmarker;
    private LinearLayout linearLayout;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_group_member);

          FirebaseFirestore.getInstance().collection(Constants.USERS).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
              @Override
              public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                  for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){

                      UserDatabase userDatabase = documentSnapshot.toObject(UserDatabase.class);

                      allUsersLists.add(userDatabase);
                      chatFragmentRecycler.notifyDataSetChanged();
                  }

              }
          });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_group_member);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(" ");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.custom_toolbar_group, null);

        actionBar.setCustomView(view);
        toobarCounter = (TextView) findViewById(R.id.adder_text);


        databaseHelper = new DatabaseHelper(getApplicationContext());

        linearLayout = (LinearLayout) findViewById(R.id.selectedmemberlinear);
        okmarker = (ImageView) findViewById(R.id.okmarker);
        okmarker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(groupmember !=  null) {
                    Intent intent = new Intent(SelectedMemberOfGroup.this, CreateGroup.class);
                    intent.putExtra("arrayboy", groupmember);
                    startActivity(intent);
                    finish();


                }
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.select_recyclerview);
        recycler2 = (RecyclerView) findViewById(R.id.selected_member);

        recyclerView.setHasFixedSize(true);
        recycler2.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        RecyclerView.LayoutManager layoutgroupManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);



        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recycler2.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setLayoutManager(mLayoutManager);
        recycler2.setLayoutManager(layoutgroupManager);

        newSelectedAdapter = new NewSelectedAdapter(getApplicationContext(), groupmember);
        chatFragmentRecycler = new UserAdapterSelecting(getApplicationContext(), allUsersLists);
        chatFragmentRecycler.setClickListener(this);
        recyclerView.setAdapter(chatFragmentRecycler);
        recycler2.setAdapter(newSelectedAdapter);
     //   getDataFromSQLite();

    }

    @SuppressLint("StaticFieldLeak")
    private void getDataFromSQLite() {
        // AsyncTask is used that SQLite operation not blocks the UI Thread.
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                allUsersLists.clear();
                Log.d("mango", "chat:" + allUsersLists);
                allUsersLists.addAll(databaseHelper.getAllRecords());
                Log.d("mango", "chat:" + allUsersLists);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                chatFragmentRecycler.notifyDataSetChanged();
                // AllUsersList allUsersList = new AllUsersList();
                //chatFragmentRecycler.notifyItemInserted(getItemIndex(allUsersList));
            }
        }.execute();
    }


    public void prepareSelection(View v, int position) {


        if(((CheckBox)v).isChecked()){


            UserDatabase allUsersList = allUsersLists.get(position);

                if(counter<= 200) {
                    groupmember.add(allUsersList);
                    linearLayout.setVisibility(View.VISIBLE);
                    newSelectedAdapter.notifyDataSetChanged();

                    counter = counter + 1;
                    updateCounter(counter);
                }else {
                    Toast.makeText(this, "Maximum number exceeded", Toast.LENGTH_SHORT).show();
            }

        } else {
            counter = counter - 1;
            groupmember.remove(allUsersLists.get(position));
           newSelectedAdapter.notifyDataSetChanged();
           // Log.d("mango", "chat:" + groups);


            updateCounter(counter);

        }
        }



    private void updateCounter(int counter) {


        if(counter <=0){
            toobarCounter.setText("0 item selected");

            linearLayout.setVisibility(View.GONE);


        }else {
                if(counter >= 1) {
                    okmarker.setVisibility(View.VISIBLE);
                }else {
                    okmarker.setVisibility(View.GONE);
                }
            toobarCounter.setText(counter +" item out of 200");
        }

    }

    @Override
    public void Onclick(View view, int position) {
            prepareSelection(view, position);
    }
}
