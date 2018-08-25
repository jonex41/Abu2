package com.john.www.abu.Fragment.Post000;

import android.support.v4.app.Fragment;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.john.www.abu.PostStuffs.utils.Constants;
import com.john.www.abu.PostStuffs.utils.FirebaseUtils;

/**
 * Created by MR AGUDA on 10-Mar-18.
 */

public class PostFragment extends PostListFragment{


    public PostFragment() {
        // Required empty public constructor
    }



    @Override
    public Query getQuery() {
        Query myTopPostsQuery = FirebaseFirestore.getInstance().collection(Constants.POST_KEY);

        return myTopPostsQuery;
    }


    @Override
    public String getString(String values) {
        return null;
    }


}


