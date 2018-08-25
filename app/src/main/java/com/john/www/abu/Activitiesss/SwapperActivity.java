package com.john.www.abu.Activitiesss;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.john.www.abu.Adapters.MessageAdapterYes;
import com.john.www.abu.GlideApp;
import com.john.www.abu.R;
import com.john.www.abu.ShoppingStuffs.DetailShopActivity;
import com.john.www.abu.ShoppingStuffs.Models.ListOfShop;
import com.john.www.abu.ShoppingStuffs.ShoppingActivities.AddGoodsActivity;

import java.util.ArrayList;
import java.util.List;

public class SwapperActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private MessageAdapterYes messageAdater;
    private List<ListOfShop> messagessre = new ArrayList<ListOfShop>();
    private FirestoreRecyclerAdapter<ListOfShop, ShopViewHolder> mAdapter;



    public static final String NAME_OF_PRODUCT = "name_of_product";
    public static final String IMAGEURL = "image_url";
    public static final String COST_OF_PRODUCT = "cost_of_product";
    public static final String MINIMUM_NUMBEROF_PRODUCT = "minimum_numberof_product";
    private String nCosts1, nName1, imageUrl;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swapper);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarswapper);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabswapper);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddGoodsActivity.class);
                intent.putExtra("mangochop", "mangochop");
                startActivity(intent);

            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.recyclerChat);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this );

        recyclerView.setLayoutManager(linearLayoutManager);


    }

    @Override
    protected void onStart() {
        super.onStart();

        Query query = FirebaseFirestore.getInstance().collection("SwappersItems");
        FirestoreRecyclerOptions<ListOfShop> options = new FirestoreRecyclerOptions.Builder<ListOfShop>().setQuery(query, ListOfShop.class).build();


        mAdapter = new FirestoreRecyclerAdapter<ListOfShop, ShopViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ShopViewHolder holder, int position, @NonNull ListOfShop model) {
                holder.setName(model.getNameOfItem());
                holder.setCostOfItem(model.getCostOfItem());
                holder.setMinimum_perdilivery(model.getMinimum_for_delivery());






                GlideApp.with(getApplicationContext()).load(model.getImageUrl()).error(R.drawable.facebook_avatar).placeholder(R.drawable.facebook_avatar)
                        .into(holder.shop_image);

                holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int pos = holder.getAdapterPosition();

                        if(pos != RecyclerView.NO_POSITION) {

                            nCosts1 = model.getCostOfItem();
                            nName1 = model.getNameOfItem();
                            //  nMinimum_per_delivery1 = model.getMinimum_for_delivery();
                            imageUrl = model.getImageUrl();
                            String desc = model.getDescription();
                            String sellerid = model.getSellerid();

                            Intent intent = new Intent(getApplicationContext(), DetailShopActivity.class);
                            intent.putExtra(NAME_OF_PRODUCT, nName1);
                            //  intent.putExtra(MINIMUM_NUMBEROF_PRODUCT, nMinimum_per_delivery1);
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
        recyclerView.setAdapter(mAdapter);
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
}
