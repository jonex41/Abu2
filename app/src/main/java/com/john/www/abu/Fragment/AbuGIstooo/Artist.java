package com.john.www.abu.Fragment.AbuGIstooo;


import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.john.www.abu.Fragment.Post000.PostListFragment;
import com.john.www.abu.R;

/**
 * Created by MR AGUDA on 10-Mar-18.
 */

public class Artist  extends PostListFragment {



    @Override
    public Query getQuery() {
    CollectionReference databaseReference= FirebaseFirestore.getInstance().collection("Artists");
        Query myTopPostsQuery = databaseReference;

        // [END my_top_posts_query]

        return myTopPostsQuery;
    }

    @Override
    public String getString(String values) {
        return "Artists";
    }
}
