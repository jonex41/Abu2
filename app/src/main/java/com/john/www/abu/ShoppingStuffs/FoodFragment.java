package com.john.www.abu.ShoppingStuffs;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


public class FoodFragment extends ShopListFragment {


    @Override
    public Query getQuery() {
        Query myTopPostsQuery= FirebaseFirestore.getInstance().collection("food");
        return myTopPostsQuery;
    }
}
