package com.john.www.abu.ShoppingStuffs.ShoppingActivities;


import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.john.www.abu.ShoppingStuffs.ShopListFragment;

public class AccessoryFragment extends ShopListFragment {

    public AccessoryFragment() {
    }

    @Override
    public Query getQuery() {
        Query myTopPostsQuery= FirebaseFirestore.getInstance().collection("accessories");
        return myTopPostsQuery;
    }
}
