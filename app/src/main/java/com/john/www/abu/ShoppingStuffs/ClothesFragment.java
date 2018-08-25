package com.john.www.abu.ShoppingStuffs;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;



public  class ClothesFragment extends ShopListFragment {


    public ClothesFragment() {
    }

    @Override
    public Query getQuery() {
        Query myTopPostsQuery= FirebaseFirestore.getInstance().collection("clothings");
        return myTopPostsQuery;
    }




}