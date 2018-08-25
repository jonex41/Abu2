package com.john.www.abu.PostStuffs.utils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Collection;


/**
 * Created by brad on 2017/02/05.
 */

public class FirebaseUtils {
    //I'm creating this class for similar reasons as the Constants class, and to make my code a bit
    //cleaner and more well managed.

    public static DocumentReference getUserRef(String email){
        return FirebaseFirestore.getInstance()
                .document(Constants.USERS_KEY+"/"+email);

    }

    public static CollectionReference getPostRef(){
        return FirebaseFirestore.getInstance()
                .collection(Constants.POST_KEY);
    }

    public static DocumentReference getPostLikedRef(){
        return FirebaseFirestore.getInstance()
                .document(Constants.POST_LIKED_KEY);
    }

    public static CollectionReference getPostLikedRef2(){
        return FirebaseFirestore.getInstance()
                .collection("likes");
    }

    public static DocumentReference getPostLikedRef(String postId){
        return getPostLikedRef2().document(postId+"/"+getCurrentUser().getUid());
    }

    public static FirebaseUser getCurrentUser(){
        return FirebaseAuth.getInstance().getCurrentUser();
    }

   /* public static String getUid(){
        String path = FirebaseFirestore.getInstance().collection().toString();
        return path.substring(path.lastIndexOf("/") + 1);
    }*/

    public static StorageReference getImageSRef(){
        return FirebaseStorage.getInstance().getReference(Constants.POST_IMAGES);
    }

    public static CollectionReference getMyPostRef(){
        return FirebaseFirestore.getInstance().
                collection(Constants.MY_POSTS+"/"+getCurrentUser().getEmail().replace(".",","));
    }

    public static CollectionReference getCommentRef(String postId){
        return FirebaseFirestore.getInstance().collection(Constants.COMMENTS_KEY+"/"+postId);
    }

    public static CollectionReference getMyRecordRef(){
        return FirebaseFirestore.getInstance().collection(Constants.USER_RECORD+"/"+
                getCurrentUser().getEmail().replace(".",","));
    }

   /* public static void addToMyRecord(String node, final String id){
        getMyRecordRef().child(node).runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                ArrayList<String> myRecordCollection;
                if(mutableData.getValue() == null){
                    myRecordCollection = new ArrayList<String>(1);
                    myRecordCollection.add(id);
                }else{
                    myRecordCollection = (ArrayList<String>) mutableData.getValue();
                    myRecordCollection.add(id);
                }

                mutableData.setValue(myRecordCollection);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {

            }
        });}*/

}
