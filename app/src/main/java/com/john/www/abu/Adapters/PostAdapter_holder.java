package com.john.www.abu.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Transaction;
import com.john.www.abu.Activitiesss.FriendFromPost;
import com.john.www.abu.DatabaseStuffs.sqlMessage.DatMessage;

import com.john.www.abu.DatabaseStuffs.sqlUsers.UserForPost;
import com.john.www.abu.DatabaseStuffs.sqlUsers.UserForVote;
import com.john.www.abu.DatabaseStuffs.sqlUsers.UserOnly;
import com.john.www.abu.GlideApp;
import com.john.www.abu.PostStuffs.PostActivity;
import com.john.www.abu.PostStuffs.models.PostModel;
import com.john.www.abu.PostStuffs.utils.Constants;
import com.john.www.abu.PostStuffs.utils.FirebaseUtils;
import com.john.www.abu.R;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;


public class PostAdapter_holder extends  RecyclerView.Adapter  {



    private static final int VIEW_TYPE_PICTURE =1;
    private static final int VIEW_TYPE_VIDEO = 2;

    private Context ctx;
    private List<PostModel> mMessageList;
    private DatMessage datMessage;
    private FirebaseAuth mAuth;
    private String otherskey;
    private String mykey;
    private String userChoosenTask;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String myId = FirebaseAuth.getInstance().getCurrentUser().getUid();

    public PostAdapter_holder(Context context, List<PostModel> messageList) {
        ctx = context;
        mMessageList = messageList;
    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }




    @Override
    public int getItemViewType(int position) {
        PostModel messagess = (PostModel) mMessageList.get(position);




        String type = messagess.getType().toString();



            if (type.contains("picture")) {
                return VIEW_TYPE_PICTURE;
            } else if (type.contains("video")) {
                return VIEW_TYPE_VIDEO;
            }



        return position;
    }


    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    // Inflates the appropriate layout according to the ViewType.
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;


        if( viewType==VIEW_TYPE_PICTURE) {

            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_post, parent, false);
            return new PictureViewHolder(view);

        }
        if( viewType==VIEW_TYPE_VIDEO) {

            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_video_single_stuff, parent, false);
            return new VideoViewHolder(view);

        }



        return null;
    }

    // Passes the message object to a ViewHolder so that the contents can be bound to UI.
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        PostModel message = (PostModel) mMessageList.get(position);

        switch (holder.getItemViewType()) {
            case VIEW_TYPE_PICTURE:
                ((PictureViewHolder) holder).bind(message);
                break;
            case VIEW_TYPE_VIDEO:
                ((VideoViewHolder) holder).bind(message);
                break;




        }


    }
    private void onLikeClick(final String postId) {

        final DocumentReference sfDocRef = db.collection(Constants.POST_KEY).document(postId);
        final DocumentReference sfDocRef2 = db.collection("likes").document(postId);
        final DocumentReference sfDocRef222 = db.collection(Constants.POST_KEY).document(postId);
        final CollectionReference doc2 = sfDocRef.collection(myId);
        Map<String, Object> map = new HashMap<>();
        map.put(myId, true);
        Toast.makeText(ctx, "like clicked", Toast.LENGTH_SHORT).show();
        doc2.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
               for(DocumentSnapshot documentSnapshot : task.getResult()){
                   if(documentSnapshot.exists()){
                       Toast.makeText(ctx, "like clicked", Toast.LENGTH_SHORT).show();
                       db.runTransaction(new Transaction.Function<Void>() {
                           @Override
                           public Void apply(Transaction transaction) throws FirebaseFirestoreException {
                               DocumentSnapshot snapshot = transaction.get(sfDocRef);

                               if (snapshot.getDouble(Constants.NUM_LIKES_KEY) == 0){

                                   double newPopulation = snapshot.getDouble(Constants.NUM_LIKES_KEY) - 1;
                                   transaction.update(sfDocRef,Constants.NUM_LIKES_KEY,newPopulation);
                                   sfDocRef2.update(myId, FieldValue.delete());


                               }
                               return null;
                           }
                       });

                   }else {
                       Toast.makeText(ctx, "unlike clicked", Toast.LENGTH_SHORT).show();
                       db.runTransaction(new Transaction.Function<Void>() {
                           @Override
                           public Void apply(Transaction transaction) throws FirebaseFirestoreException {
                               DocumentSnapshot snapshot = transaction.get(sfDocRef);


                               double newPopulation = snapshot.getDouble(Constants.NUM_LIKES_KEY) + 1;
                               transaction.update(sfDocRef, Constants.NUM_LIKES_KEY, newPopulation);


                               sfDocRef2.set(map);
                               return null;
                           }
                       });

                   }


               }


            }
        });

       /* doc2.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

               if(!queryDocumentSnapshots.){
                   db.runTransaction(new Transaction.Function<Void>() {
                       @Override
                       public Void apply(Transaction transaction) throws FirebaseFirestoreException {
                           DocumentSnapshot snapshot = transaction.get(sfDocRef);

                           if (snapshot.getDouble(Constants.NUM_LIKES_KEY) == 0){

                       double newPopulation = snapshot.getDouble(Constants.NUM_LIKES_KEY) - 1;
                           transaction.update(sfDocRef,Constants.NUM_LIKES_KEY,newPopulation);
                           sfDocRef2.update(myId, FieldValue.delete());
                   }
                           return null;
                       }
                   });

               }else {

                   db.runTransaction(new Transaction.Function<Void>() {
                       @Override
                       public Void apply(Transaction transaction) throws FirebaseFirestoreException {
                           DocumentSnapshot snapshot = transaction.get(sfDocRef);


                           double newPopulation = snapshot.getDouble(Constants.NUM_LIKES_KEY) + 1;
                           transaction.update(sfDocRef, Constants.NUM_LIKES_KEY, newPopulation);


                           sfDocRef2.set(map);
                           return null;
                       }
                   });
               }

            }

        });*/


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
        private FirebaseAuth mAuth;
        String userff;
        private FirebaseFirestore db;


        PictureViewHolder(View itemView) {
            super(itemView);

            mAuth  = FirebaseAuth.getInstance();
            userff = mAuth.getCurrentUser().getUid();
            db = FirebaseFirestore.getInstance();

            postOwnerDisplayImageView = (ImageView) itemView.findViewById(R.id.iv_post_owner_display);
            postOwnerUsernameTextView = (TextView) itemView.findViewById(R.id.tv_post_username);
            postTimeCreatedTextView = (TextView) itemView.findViewById(R.id.tv_time);
            postDisplayImageVIew = (ImageView) itemView.findViewById(R.id.iv_post_display);
            postLikeLayout = (LinearLayout) itemView.findViewById(R.id.like_layout);
            postCommentLayout = (LinearLayout) itemView.findViewById(R.id.comment_layout);
            postNumLikesTextView = (TextView) itemView.findViewById(R.id.tv_likes);
            postNumCommentsTextView = (TextView) itemView.findViewById(R.id.tv_comments);
            postTextTextView = (TextView) itemView.findViewById(R.id.tv_post_text);
            likeImage = (ImageView) itemView.findViewById(R.id.iv_like);
            moreOptions = (ImageView) itemView.findViewById(R.id.morevertsooo);
        }


        void bind(PostModel message) {

         if (message.getUserid() !=null){

             FirebaseFirestore.getInstance().collection("Users").document(message.getUserid()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                 @Override
                 public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                     if(e !=null){
                         Toast.makeText(ctx, "Error"+e, Toast.LENGTH_SHORT).show();
                         return;
                     }
                     UserForPost userForPost = documentSnapshot.toObject(UserForPost.class);
                     message.setUser(userForPost);
                     if(message.getUser().getName() != null) {
                         postOwnerUsernameTextView.setText(message.getUser().getName());
                     }

                     if(message.getUser().getImage() != null) {
                         Glide.with(ctx)
                                 .load(message.getUser().getImage())

                                 .into(postOwnerDisplayImageView);
                     }
                 }
             });

         }
          /*  FirebaseFirestore.getInstance().document("likes").collection(message.getPostId()).document(userff).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot qs, @Nullable FirebaseFirestoreException e) {
                    if(e !=null){
                        Toast.makeText(ctx, "Error"+e, Toast.LENGTH_SHORT).show();
                        return;
                    }


                    if(qs != null || !qs.exists()){
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            likeImage.setImageDrawable(ctx.getResources().getDrawable(R.drawable.love_image,ctx.getTheme()));
                        } else {
                            likeImage.setImageDrawable(ctx.getResources().getDrawable(R.drawable.love_image));
                        }
                    }else {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            likeImage.setImageDrawable(ctx.getResources().getDrawable(R.drawable.likelove,ctx.getTheme()));
                        } else {
                            likeImage.setImageDrawable(ctx.getResources().getDrawable(R.drawable.likelove));
                        }
                    }
                }
            });*/

          postOwnerDisplayImageView.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  Intent intent = new Intent(ctx, FriendFromPost.class);
                  intent.putExtra("postUser", message.getUserid());
                  ctx.startActivity(intent);
              }
          });

            postTimeCreatedTextView.setText(DateUtils.getRelativeTimeSpanString(message.getTimeCreated()));



            postNumLikesTextView.setText(String.valueOf(message.getNumLikes()));

            postNumCommentsTextView.setText(String.valueOf(message.getNumComments()));

            String text = message.getPostText();
            if(!TextUtils.isEmpty(text)) {
                postTextTextView.setVisibility(View.VISIBLE);
                postTextTextView.setText(text);
            }



            if (message.getPostImageUrl() != null) {
                postDisplayImageVIew.setVisibility(View.VISIBLE);
                //  StorageReference storageReference = FirebaseStorage.getInstance().getReference(model.getPostImageUrl());
                GlideApp.with(ctx).load(message.getPostImageUrl()).thumbnail(
                        Glide.with(ctx).load(message.getThumbIamgeUri())
                ).apply(new RequestOptions().override(800, 400)).into(postDisplayImageVIew);
            } else {
                postDisplayImageVIew.setImageBitmap(null);
                postDisplayImageVIew.setVisibility(View.GONE);
            }
            postLikeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    onLikeClick(message.getPostId());
                    /*postLikeLayout.setEnabled(false);
                    new android.os.Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            postLikeLayout.setEnabled(true);
                        }
                    }, 2000);*/

                }
            });


            moreOptions.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectImage();
                }
            });

            postCommentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ctx,PostActivity.class);
                    intent.putExtra(Constants.EXTRA_POST, message);
                    ctx.startActivity(intent);

                }
            });


        }

        private void selectImage() {
            final CharSequence[] items = { "Remove picture", "Save image","Cancel" };

            AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
          //  builder.setTitle("Add Photo!");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {


                    if (items[item].equals("Take Photo")) {
                        userChoosenTask="Take Photo";
                       /* boolean result= Utility.checkPermission(ProfileActivity.this);
                        if(result) {
                            //  cameraIntent();
                        }*/
                    } else if (items[item].equals("Choose from Library")) {
                      /*  userChoosenTask="Choose from Library";
                        boolean result= Utility.checkPermission(ProfileActivity.this);
                        if(result) {
                            //   galleryIntent();
                        }*/
                    } else if (items[item].equals("Cancel")) {
                        dialog.dismiss();
                    }
                }
            });
            builder.show();
        }
    }

    private class VideoViewHolder extends RecyclerView.ViewHolder {

        private View view;


        VideoViewHolder(View itemView) {
            super(itemView);
            view = itemView;




        }






        void bind(PostModel message) {

        }




    }




}
