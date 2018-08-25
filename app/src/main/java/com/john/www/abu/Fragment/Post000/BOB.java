package com.john.www.abu.Fragment.Post000;


import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.john.www.abu.PostStuffs.utils.Constants;
import com.john.www.abu.PostStuffs.utils.FirebaseUtils;

/**
 * Created by MR AGUDA on 10-Mar-18.
 */

public class BOB  extends PostListFragment {



    @Override
    public Query getQuery() {
       CollectionReference databaseReference = FirebaseFirestore.getInstance().collection(Constants.POST_KEY);
        Query myTopPostsQuery = databaseReference.orderBy("numLikes", Query.Direction.DESCENDING).limit(30);

        // [END my_top_posts_query]

        return myTopPostsQuery;
    }

    @Override
    public String getString(String values) {
        return null;
    }

}
