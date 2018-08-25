package com.john.www.abu.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.john.www.abu.GlideApp;
import com.john.www.abu.R;
import com.john.www.abu.ShoppingStuffs.DetailShopActivity;
import com.john.www.abu.ShoppingStuffs.Models.ListOfShop;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CartAapter extends RecyclerView.Adapter<CartAapter.CartViewHolder> {

   private Context context;
   private List<ListOfShop> userList;
    private String nCosts1,nName1,nMinimum_per_delivery1,imageUrl;


    public static final String NAME_OF_PRODUCT = "name_of_product";
    public static final String IMAGEURL = "image_url";
    public static final String COST_OF_PRODUCT = "cost_of_product";
    public static final String MINIMUM_NUMBEROF_PRODUCT = "minimum_numberof_product";

    public CartAapter(Context context, List<ListOfShop> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public CartAapter.CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new  CartAapter.CartViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cartsingle, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull CartAapter.CartViewHolder holder, int position) {
        ListOfShop model = userList.get(position);

        holder.mName.setText(model.getNameOfItem());
        holder.cost_of_production.setText(model.getCostOfItem());
      //  holder.minimum_for_delivery(model.getMinimum_for_delivery());






        GlideApp.with(context).load(model.getImageUrl()).error(R.drawable.facebook_avatar).placeholder(R.drawable.facebook_avatar)
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

                    Intent intent = new Intent(context, DetailShopActivity.class);
                    intent.putExtra(NAME_OF_PRODUCT, nName1);
                    intent.putExtra(MINIMUM_NUMBEROF_PRODUCT, nMinimum_per_delivery1);
                    intent.putExtra(COST_OF_PRODUCT, nCosts1);
                    intent.putExtra(IMAGEURL, imageUrl);
                    intent.putExtra("sellerid", sellerid);
                    intent.putExtra("desc", desc);
                    context.startActivity(intent);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return (userList != null)? userList.size() : 0;
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {



        private View mView;
        private CircleImageView shop_image;
        private TextView mName;
        private TextView cost_of_production;
      //  private TextView minimum_for_delivery;
        private LinearLayout linearLayout;


        public CartViewHolder(View itemView) {
            super(itemView);


            shop_image = (CircleImageView) itemView.findViewById(R.id.thumbnail);
            mName = (TextView) itemView.findViewById(R.id.title);
            cost_of_production = (TextView) itemView.findViewById(R.id.costs_of_product);
           // minimum_for_delivery = (TextView) itemView.findViewById(R.id.minimumto_ordertxt);

            linearLayout = (LinearLayout) itemView.findViewById(R.id.click_shop);
        }
    }
}
