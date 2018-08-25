package com.john.www.abu.Fragment.AbuGIstooo;




import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.john.www.abu.DatabaseStuffs.sqlUsers.User;
import com.john.www.abu.DatabaseStuffs.sqlUsers.UserDatabase;
import com.john.www.abu.GlideApp;
import com.john.www.abu.PostStuffs.PostActivity;
import com.john.www.abu.R;
import com.john.www.abu.helper.Constants;

import javax.annotation.Nullable;


public abstract class ListOfMaleFemale extends Fragment {


    private FirestoreRecyclerAdapter<UserDatabase, PictureViewHolder> mAdapter;
    private String image;
    private String name;
    private String userid;
    private String status;
    private LinearLayoutManager mManager;
    private RecyclerView mPostRecyclerView;
    private FirebaseFirestore firestore;
    private DocumentReference collectionReference;
    private String myKey;

    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.gists, container, false);
        firestore = FirebaseFirestore.getInstance();
        collectionReference = firestore.collection("vote").document("likesVote");



      mAuth =   FirebaseAuth.getInstance();
      myKey = mAuth.getCurrentUser().getUid();
        mPostRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclervote);
        mPostRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        setUpAdaper2();
        mPostRecyclerView.setAdapter(mAdapter);
        return rootView;
    }


    public void setUpAdaper2(){

        Query query = getQuery();




        FirestoreRecyclerOptions<UserDatabase> options = new FirestoreRecyclerOptions.Builder<UserDatabase>().setQuery(query, UserDatabase.class).build();

        mAdapter = new FirestoreRecyclerAdapter<UserDatabase, PictureViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull PictureViewHolder holder, int position, @NonNull UserDatabase userDatabase) {
                image = userDatabase.getThumbIamgeUri();
                name = userDatabase.getName();
                userid = userDatabase.getUserid();
                status = userDatabase.getStatus();

                holder.setName(userDatabase.getName());
                holder.setDepartment(userDatabase.getDepartment());


/*

                collectionReference.document(userDatabase.getUserid()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                        long counts;
                        counts = documentSnapshot.get();

                        if (dataSnapshot.hasChild(myKey)) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                holder.likeImage.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.mark_vote_red, getActivity().getTheme()));

                                holder.postNumLikesTextView.setText(String.valueOf(counts));

                            } else {
                                holder.likeImage.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.mark_vote_red));
                                holder.postNumLikesTextView.setText(String.valueOf(counts));
                            }

                        } else {


                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                holder.likeImage.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.mark_vote_gray, getActivity().getTheme()));
                                holder.postNumLikesTextView.setText(String.valueOf(counts));
                            } else {
                                holder.likeImage.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.mark_vote_gray));
                                holder.postNumLikesTextView.setText(String.valueOf(counts));
                            }

                        }

                    }
                });
*/




                holder.postOwnerUsernameTextView.setText(userDatabase.getName());

                holder.postTimeCreatedTextView.setText(userDatabase.getDepartment());





                //   postNumCommentsTextView.setText(String.valueOf(messaglle.getNumComments()));


                Glide.with(getActivity())
                        .load(userDatabase.getThumbIamgeUri())
                        .into(holder.postOwnerDisplayImageView);


                if (userDatabase.getImage() != null) {
                    holder.postDisplayImageVIew.setVisibility(View.VISIBLE);
                    //  StorageReference storageReference = FirebaseStorage.getInstance().getReference(model.getPostImageUrl());
                    GlideApp.with(getActivity()).load(userDatabase.getImage()).thumbnail(
                            Glide.with(getActivity()).load(userDatabase.getThumbIamgeUri())
                    ).apply(new RequestOptions().override(800, 400)).into(holder.postDisplayImageVIew);
                } else {
                    holder.postDisplayImageVIew.setImageBitmap(null);
                    holder.postDisplayImageVIew.setVisibility(View.GONE);
                }
                holder.postLikeLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                      //  onLikeClick(userDatabase.getUserid());
                        holder.postLikeLayout.setEnabled(false);
                        new android.os.Handler().postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                holder.postLikeLayout.setEnabled(true);
                            }
                        }, 2000);

                    }
                });



                holder.postCommentLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), PostActivity.class);
                        intent.putExtra(Constants.EXTRA_POST, userDatabase);
                        startActivity(intent);

                    }
                });

            }

            @NonNull
            @Override
            public PictureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new PictureViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_single_vote, parent, false));

            }
        };




    }

    public abstract Query getQuery();

    @Override
    public void onStart() {
        super.onStart();
        if (mAdapter != null) {
            mAdapter.startListening();
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAdapter != null) {
            mAdapter.stopListening();
        }
    }


    private class PictureViewHolder extends RecyclerView.ViewHolder {
        ImageView postOwnerDisplayImageView;
        TextView postOwnerUsernameTextView;
        TextView postTimeCreatedTextView;
        ImageView postDisplayImageVIew;
        TextView postTextTextView;
        LinearLayout postLikeLayout;
        LinearLayout postCommentLayout;
        TextView postNumLikesTextView;
        TextView postNumCommentsTextView;
        ImageView likeImage;
        ImageView moreOptions;


        PictureViewHolder(View itemView) {
            super(itemView);


            postOwnerDisplayImageView = (ImageView) itemView.findViewById(R.id.iv_post_owner_display);
            postOwnerUsernameTextView = (TextView) itemView.findViewById(R.id.tv_post_username);
            postTimeCreatedTextView = (TextView) itemView.findViewById(R.id.tv_department);
            postDisplayImageVIew = (ImageView) itemView.findViewById(R.id.iv_post_display);
            postLikeLayout = (LinearLayout) itemView.findViewById(R.id.like_layout);
            postCommentLayout = (LinearLayout) itemView.findViewById(R.id.comment_layout);
            postNumLikesTextView = (TextView) itemView.findViewById(R.id.tv_likes);
            postNumCommentsTextView = (TextView) itemView.findViewById(R.id.tv_comments);
            postTextTextView = (TextView) itemView.findViewById(R.id.tv_post_text);
            likeImage = (ImageView) itemView.findViewById(R.id.iv_like);
            moreOptions = (ImageView) itemView.findViewById(R.id.morevertsooo);
        }


        public void setName(String name) {
           postOwnerUsernameTextView.setText(name);
        }

        public void setDepartment(String department) {
            postTimeCreatedTextView.setText(department);
        }
    }



}
