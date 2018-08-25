package com.john.www.abu.Fragment.AbuGIstooo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.load.engine.DiskCacheStrategy;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.john.www.abu.GlideApp;
import com.john.www.abu.R;


import javax.annotation.Nullable;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by MR AGUDA on 10-Mar-18.
 */

public class FOA  extends Fragment implements View.OnClickListener {

    private TextView please_click_to_vote;
    private TextView king_of_abu_name;
    private TextView king_of_abu_department;
    private CircleImageView king_of_abu_image;

    private TextView queen_of_abu_name;
    private TextView queen_of_abu_department;
    private CircleImageView queen_of_abu_image;

    private LinearLayout king_linear;
    private LinearLayout queen_linear;
   private FirebaseFirestore firestore;
   private CollectionReference collectionReference;

    private String king_name;
    private String king_image;
    private String king_department;
    private String id;
    private String gender;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.foa, container, false);


        firestore = FirebaseFirestore.getInstance();

        collectionReference = firestore.collection("Best");


        king_of_abu_image = (CircleImageView) rootView.findViewById(R.id.king_of_abu_image);
        king_of_abu_name = (TextView) rootView.findViewById(R.id.king_of_abu_name);
        king_of_abu_department = (TextView) rootView.findViewById(R.id.king_of_abu_department);

        queen_of_abu_image = (CircleImageView) rootView.findViewById(R.id.queen_of_abu_image);
        queen_of_abu_department = (TextView) rootView.findViewById(R.id.queen_of_abu_department);
        queen_of_abu_name = (TextView) rootView.findViewById(R.id.queen_of_abu_name);

       king_linear = (LinearLayout) rootView.findViewById(R.id.king_layout);
        queen_linear = (LinearLayout) rootView.findViewById(R.id.queen_layout);

        king_linear.setOnClickListener(this);
        queen_linear.setOnClickListener(this);

        please_click_to_vote = (TextView) rootView.findViewById(R.id.please_clickto_vote);
        please_click_to_vote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), VoteActivity.class));


            }
        });

        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                if (e != null) {
                    Toast.makeText(getContext(), "Error" + e, Toast.LENGTH_SHORT).show();
                }
                for (QueryDocumentSnapshot ds :queryDocumentSnapshots) {

                    king_name = ds.getString("name");
                    king_image = ds.getString("image");
                    king_department = ds.getString("department");
                    gender = ds.getString("gender");
                    if (!gender.isEmpty()) {
                        if (gender.contains("male")) {
                            king_of_abu_name.setText(king_name);
                            king_of_abu_department.setText(king_department);
                            GlideApp.with(getContext()).load(king_image).placeholder(R.drawable.facebook_avatar).diskCacheStrategy(DiskCacheStrategy.ALL).into(king_of_abu_image);


                        } else {
                            queen_of_abu_name.setText(king_name);
                            queen_of_abu_department.setText(king_department);
                            GlideApp.with(getActivity()).load(king_image).placeholder(R.drawable.facebook_avatar).diskCacheStrategy(DiskCacheStrategy.ALL).into(queen_of_abu_image);


                        }

                    }
                }
            }
        });





        return rootView;

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.king_layout:

                break;
            case R.id.queen_layout:

                break;
        }
    }

    @Override
    public void onStop() {
        super.onStop();

    }
}
