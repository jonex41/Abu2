package com.john.www.abu.Fragment.AbuGIstooo;


import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.john.www.abu.Fragment.Post000.PostListFragment;


/**
 * Created by MR AGUDA on 10-Mar-18.
 */

public class Gist  extends PostListFragment {


    @Override
    public Query getQuery() {
     CollectionReference databaseReference = FirebaseFirestore.getInstance().collection("Gists");
        Query myTopPostsQuery = databaseReference;

        // [END my_top_posts_query]

        return myTopPostsQuery;
    }

    @Override
    public String getString(String values) {
        return "Gists";
    }
}
