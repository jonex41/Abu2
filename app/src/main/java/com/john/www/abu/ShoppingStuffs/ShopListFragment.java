package com.john.www.abu.ShoppingStuffs;



import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import com.john.www.abu.GlideApp;
import com.john.www.abu.R;
import com.john.www.abu.ShoppingStuffs.Models.ListOfShop;

import java.util.Collection;


public abstract class ShopListFragment extends Fragment {

    private RecyclerView recycler_for_posts;

    private FirestoreRecyclerAdapter<ListOfShop, ShopViewHolder> mAdapter;
    private String nCosts1;
    public String nName1;
    public String nMinimum_per_delivery1;
    private String imageUrl;

    public static final String NAME_OF_PRODUCT = "name_of_product";
    public static final String IMAGEURL = "image_url";
    public static final String COST_OF_PRODUCT = "cost_of_product";
    public static final String MINIMUM_NUMBEROF_PRODUCT = "minimum_numberof_product";


    public ShopListFragment() {
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.clothes_fragment, container, false);

        recycler_for_posts = (RecyclerView) rootView.findViewById(R.id.recycler_for_post);
        recycler_for_posts.setHasFixedSize(true);
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            recycler_for_posts.setLayoutManager(new GridLayoutManager(getContext(), 2));
        } else {
            recycler_for_posts.setLayoutManager(new GridLayoutManager(getContext(), 3));
        }

        setUpAdapter();
        recycler_for_posts.setAdapter(mAdapter);


        return rootView;
    }

    private void setUpAdapter() {

        Query query = getQuery();


        FirestoreRecyclerOptions<ListOfShop> options = new FirestoreRecyclerOptions.Builder<ListOfShop>().setQuery(query, ListOfShop.class).build();


        mAdapter = new FirestoreRecyclerAdapter<ListOfShop, ShopViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ShopViewHolder holder, int position, @NonNull ListOfShop model) {
                holder.setName(model.getNameOfItem());
                holder.setCostOfItem(model.getCostOfItem());
                holder.setMinimum_perdilivery(model.getMinimum_for_delivery());






                GlideApp.with(getActivity()).load(model.getImageUrl()).error(R.drawable.facebook_avatar).placeholder(R.drawable.facebook_avatar)
                        .into(holder.shop_image);

                holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int pos = holder.getAdapterPosition();

                        if(pos != RecyclerView.NO_POSITION) {

                            nCosts1 = model.getCostOfItem();
                            nName1 = model.getNameOfItem();
                            nMinimum_per_delivery1 = model.getMinimum_for_delivery();
                            imageUrl = model.getImageUrl();
                            String desc = model.getDescription();
                            String sellerid = model.getSellerid();

                            Intent intent = new Intent(getContext(), DetailShopActivity.class);
                            intent.putExtra(NAME_OF_PRODUCT, nName1);
                            intent.putExtra(MINIMUM_NUMBEROF_PRODUCT, nMinimum_per_delivery1);
                            intent.putExtra(COST_OF_PRODUCT, nCosts1);
                            intent.putExtra(IMAGEURL, imageUrl);
                            intent.putExtra("sellerid", sellerid);
                            intent.putExtra("desc", desc);
                            startActivity(intent);
                        }

                    }
                });
            }

            @NonNull
            @Override
            public ShopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new ShopViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragrance_card, parent, false));

            }
        };



    }



    @Override
    public void onStart() {
        super.onStart();
        if (mAdapter != null) {
            mAdapter.startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAdapter != null) {
            mAdapter.stopListening();
        }
    }

    class ShopViewHolder extends RecyclerView.ViewHolder {

       private View mView;
       private ImageView shop_image;
       private TextView mName;
        private TextView cost_of_production;
        private TextView minimum_for_delivery;
        private LinearLayout linearLayout;

        public ShopViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            shop_image = (ImageView) itemView.findViewById(R.id.thumbnail);
            mName = (TextView) itemView.findViewById(R.id.title);
            cost_of_production = (TextView) itemView.findViewById(R.id.costs_of_product);
            minimum_for_delivery = (TextView) itemView.findViewById(R.id.minimumto_ordertxt);

            linearLayout = (LinearLayout) itemView.findViewById(R.id.click_shop);






        }

        public void setName(String nameOfItem) {
            mName.setText(nameOfItem);
        }


        public void setCostOfItem(String nameOfItem) {
           /* String frt = nameOfItem.substring(0,1);
            String snd = nameOfItem.substring(1,2);
            Toast.makeText(getContext(), snd, Toast.LENGTH_SHORT).show();*/


                cost_of_production.setText(  nameOfItem);



        }

        public void setMinimum_perdilivery(String nameOfItem) {
            minimum_for_delivery.setText(nameOfItem);
        }

    }


    public abstract Query getQuery();
}
