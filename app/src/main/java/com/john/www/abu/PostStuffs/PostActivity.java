package com.john.www.abu.PostStuffs;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.john.www.abu.DatabaseStuffs.sqlUsers.UserForPost;
import com.john.www.abu.PostStuffs.models.Comment;
import com.john.www.abu.PostStuffs.models.PostModel;
import com.john.www.abu.PostStuffs.utils.Constants;
import com.john.www.abu.PostStuffs.utils.FirebaseUtils;
import com.john.www.abu.R;
import com.john.www.abu.ShoppingStuffs.Models.ListOfShop;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

public class PostActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String BUNDLE_COMMENT = "comment";
    private PostModel mPost;
    private EditText mCommentEditTextView;
    private Comment mComment;
    private String uid;
    private FirebaseFirestore db;
    private CollectionReference collectionReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
         uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
         db = FirebaseFirestore.getInstance();


        if (savedInstanceState != null) {
            mComment = (Comment) savedInstanceState.getSerializable(BUNDLE_COMMENT);
        }

        Intent intent = getIntent();
        mPost = (PostModel) intent.getSerializableExtra(Constants.EXTRA_POST);
        collectionReference = db.collection("Comments");

        init();
        initPost();
       initCommentSection();
    }

  private void initCommentSection() {
        RecyclerView commentRecyclerView = (RecyclerView) findViewById(R.id.comment_recyclerview);
        commentRecyclerView.setLayoutManager(new LinearLayoutManager(PostActivity.this));

       Query query = collectionReference.whereEqualTo("commentId", mPost.getPostId());

       FirestoreRecyclerOptions<Comment> options = new FirestoreRecyclerOptions.Builder<Comment>().setQuery(query, Comment.class).build();



        FirestoreRecyclerAdapter<Comment, CommentHolder> mAdapter = new FirestoreRecyclerAdapter<Comment, CommentHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CommentHolder viewHolder, int position, @NonNull Comment model) {

                viewHolder.setComment(model.getComment());
                viewHolder.setTime(DateUtils.getRelativeTimeSpanString(model.getTimeCreated()));

                String userid = model.getUser();

                FirebaseFirestore.getInstance().collection("Comments").document(userid).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                        if(e!= null){
                            Toast.makeText(PostActivity.this, "Unable to get Users Info", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        UserForPost userForPost = documentSnapshot.toObject(UserForPost.class);


                        Glide.with(PostActivity.this)
                                .load(userForPost.getImage())
                                .into(viewHolder.commentOwnerDisplay);

                        viewHolder.usernameTextView.setText(userForPost.getName());
                    }
                });


            }

            @NonNull
            @Override
            public CommentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new CommentHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_comment, parent, false));

            }
        };



        commentRecyclerView.setAdapter(mAdapter);
    }

    private void initPost() {
        ImageView postOwnerDisplayImageView = (ImageView) findViewById(R.id.iv_post_owner_display);
        TextView postOwnerUsernameTextView = (TextView) findViewById(R.id.tv_post_username);
        TextView postTimeCreatedTextView = (TextView) findViewById(R.id.tv_time);
        ImageView postDisplayImageView = (ImageView) findViewById(R.id.iv_post_display);
        LinearLayout postLikeLayout = (LinearLayout) findViewById(R.id.like_layout);
        LinearLayout postCommentLayout = (LinearLayout) findViewById(R.id.comment_layout);
        TextView postNumLikesTextView = (TextView) findViewById(R.id.tv_likes);
        TextView postNumCommentsTextView = (TextView) findViewById(R.id.tv_comments);
        TextView postTextTextView = (TextView) findViewById(R.id.tv_post_text);

       postOwnerUsernameTextView.setText(mPost.getUser().getName());
        postTimeCreatedTextView.setText(DateUtils.getRelativeTimeSpanString(mPost.getTimeCreated()));


            postTextTextView.setText( mPost.getPostText());

        postNumLikesTextView.setText(String.valueOf(mPost.getNumLikes()));
        postNumCommentsTextView.setText(String.valueOf(mPost.getNumComments()));

        Glide.with(PostActivity.this)
                .load(mPost.getUser().getImage())
                .into(postOwnerDisplayImageView);

        if (mPost.getPostImageUrl() != null) {
            postDisplayImageView.setVisibility(View.VISIBLE);
//            StorageReference storageReference = FirebaseStorage.getInstance().getReference(mPost.getPostImageUrl());

            Glide.with(getApplicationContext())
                    /*.using(new FirebaseImageLoader())*/
                    .load(mPost.getPostImageUrl())
                    .into(postDisplayImageView);
        } else {
            postDisplayImageView.setImageBitmap(null);
            postDisplayImageView.setVisibility(View.GONE);
        }
    }

    private void init() {
        mCommentEditTextView = (EditText) findViewById(R.id.et_comment);
        findViewById(R.id.iv_send).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_send:
                sendComment();
        }
    }

    private void sendComment() {
        final ProgressDialog progressDialog = new ProgressDialog(PostActivity.this);
        progressDialog.setMessage("Sending comment..");
        progressDialog.setCancelable(true);
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        mComment = new Comment();

        String strComment = mCommentEditTextView.getText().toString();

        mComment.setUser(uid);
        mComment.setCommentId(mPost.getPostId());
        mComment.setComment(strComment);
        mComment.setTimeCreated(System.currentTimeMillis());
        Map<String, Object> map = new HashMap<>();
        map.put(mPost.getPostId(), mComment);

        FirebaseFirestore.getInstance().collection("comment").add(map).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                progressDialog.dismiss();
                Toast.makeText(PostActivity.this, "Comment as been sent!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public static class CommentHolder extends RecyclerView.ViewHolder {
        ImageView commentOwnerDisplay;
        TextView usernameTextView;
        TextView timeTextView;
        TextView commentTextView;

        public CommentHolder(View itemView) {
            super(itemView);
            commentOwnerDisplay = (ImageView) itemView.findViewById(R.id.iv_comment_owner_display);
            usernameTextView = (TextView) itemView.findViewById(R.id.tv_username);
            timeTextView = (TextView) itemView.findViewById(R.id.tv_time);
            commentTextView = (TextView) itemView.findViewById(R.id.tv_comment);
        }

        public void setUsername(String username) {
            usernameTextView.setText(username);
        }

        public void setTime(CharSequence time) {
            timeTextView.setText(time);
        }

        public void setComment(String comment) {
            commentTextView.setText(comment);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(BUNDLE_COMMENT, mComment);
        super.onSaveInstanceState(outState);
    }
}
