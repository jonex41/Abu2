package com.john.www.abu.Fragment.AbuGIstooo;


import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class MaleFragment extends ListOfMaleFemale {

    @Override
    public Query getQuery( ) {

        CollectionReference databaseReference = FirebaseFirestore.getInstance()
                .collection("vote").document("contestants").collection("male");
        Query myTopPostsQuery = databaseReference;


        // [END my_top_posts_query]

        return myTopPostsQuery;
    }
}
