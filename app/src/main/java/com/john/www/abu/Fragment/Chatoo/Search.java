package com.john.www.abu.Fragment.Chatoo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.john.www.abu.Activitiesss.ChatActivity;
import com.john.www.abu.DatabaseStuffs.sqlUsers.User;
import com.john.www.abu.DatabaseStuffs.sqlUsers.UserDatabase;
import com.john.www.abu.Fragment.PicturePreviewDialog;
import com.john.www.abu.GlideApp;
import com.john.www.abu.R;
import com.john.www.abu.helper.CheckConnection;
import com.john.www.abu.helper.Constants;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by MR AGUDA on 10-Mar-18.
 */

public class Search  extends Fragment  implements View.OnClickListener {

    private EditText searchword;
    private Spinner spinner;

    private String spinnerAnswer = " ";

    private String name;
    private String status;
    private String image;
    private String userid;
    private FirestoreRecyclerAdapter<UserDatabase, RecyclerForFriendHolder> mAdapter;
    private String edittext;
    private Query query;
    private RecyclerView recyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.search, container, false);

        spinner = (Spinner) rootView.findViewById(R.id.spinner);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerforsearch);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));



        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.typeof_search, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        searchword = (EditText) rootView.findViewById(R.id.editWriteMessage);
        ImageButton button = (ImageButton) rootView.findViewById(R.id.sendmeoo);
        button.setOnClickListener(Search.this);




        return rootView;


    }

    @Override
    public void onClick(View view) {

        CheckConnection checkConnection = new CheckConnection(getContext());
        if (checkConnection.isConnected()) {


            if (view.getId() == R.id.sendmeoo) {
                 edittext = searchword.getText().toString();
                Toast.makeText(getContext(), edittext, Toast.LENGTH_SHORT).show();
                int selectedItem = (int) spinner.getSelectedItemPosition();

                switch (selectedItem) {

                    case 0:
                        Toast.makeText(getContext(), "O", Toast.LENGTH_SHORT).show();
                        spinnerAnswer = "RegNo";
                        query = FirebaseFirestore.getInstance().collection(Constants.USERS).whereLessThanOrEqualTo("RegNo", edittext);
                        setUpAdaper2(query);
                        setit();
                        break;
                    case 1:
                        query = FirebaseFirestore.getInstance().collection(Constants.USERS).whereGreaterThanOrEqualTo("Username", edittext);

                        setUpAdaper2(query);
                        setit();
                        break;
                    case 2:


                        query = FirebaseFirestore.getInstance().collection(Constants.USERS).whereLessThanOrEqualTo("name", edittext);

                        setUpAdaper2(query);
                        setit();
                        break;

                    case 3:


                        query = FirebaseFirestore.getInstance().collection(Constants.USERS).whereLessThanOrEqualTo("Department", edittext);

                        setUpAdaper2(query);
                        setit();
                        break;
                    case 4:
                        query = FirebaseFirestore.getInstance().collection(Constants.USERS).whereLessThanOrEqualTo("gender", edittext);

                        setUpAdaper2(query);
                        setit();

                        break;


                    default:
                        break;
                }

            }


        } else {

            Toast.makeText(getContext(), "check connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void setit() {
        if (mAdapter != null) {
            mAdapter.startListening();
        }

    }

    public void setUpAdaper2(Query query1){



        FirestoreRecyclerOptions<UserDatabase> options = new FirestoreRecyclerOptions.Builder<UserDatabase>().setQuery(query1, UserDatabase.class).build();

        mAdapter = new FirestoreRecyclerAdapter<UserDatabase, RecyclerForFriendHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull RecyclerForFriendHolder holder, int position, @NonNull UserDatabase userDatabase) {
                image = userDatabase.getThumbIamgeUri();

                name = userDatabase.getName();
                userid = userDatabase.getUserid();
                status = userDatabase.getStatus();
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
                                            //   databaseHelper.deleteRecord(userid);

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
                return new Search.RecyclerForFriendHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.allusersview, parent, false));

            }
        };


        recyclerView.setAdapter(mAdapter);

    }


    @Override
    public void onStart() {
        super.onStart();

    }


    @Override
    public void onStop() {
        super.onStop();
        if (mAdapter != null) {
            mAdapter.stopListening();
        }
    }

    class RecyclerForFriendHolder extends RecyclerView.ViewHolder  {

        private ImageView imageView;
        private
        CheckBox onlineimage;
        private TextView mName, mTime, mStatus;
        private ImageButton imageButton;
        private LinearLayout imagelayout, chatlayout, buttonlayout;
        CircleImageView tvimage;


        public RecyclerForFriendHolder(View itemView) {
            super(itemView);


            tvimage = (CircleImageView) itemView.findViewById(R.id.imageview_profile);
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
}
