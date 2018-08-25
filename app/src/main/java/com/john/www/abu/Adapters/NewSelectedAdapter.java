
package com.john.www.abu.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.john.www.abu.Activitiesss.SelectedMemberOfGroup;
import com.john.www.abu.DatabaseStuffs.sqlUsers.DatabaseHelper;
import com.john.www.abu.DatabaseStuffs.sqlUsers.UserDatabase;
import com.john.www.abu.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by MR AGUDA on 16-Nov-17.
 */
public class NewSelectedAdapter extends RecyclerView.Adapter<NewSelectedAdapter.RecyclerForFriendHolder> {

    private Context ctx;
    private List<UserDatabase> list;
    private String usersid;
    private String userimages;
    private String usernames;
    private String friends;

    private DatabaseHelper databaseHelper;
    private SelectedMemberOfGroup selectGroupMember;





    public NewSelectedAdapter(Context ctx, List<UserDatabase> list) {
        this.ctx = ctx;
        this.list = list;
    }

    @Override
    public RecyclerForFriendHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerForFriendHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.selected_member_rec, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerForFriendHolder holder,  int position) {

        holder.mName.setText(list.get(position).getName());
     //   holder..setText(list.get(position).getName());

       // holder.tvimage.s(list.get(position).getStatus());
      //  holder.checkbox.setVisibility(View.VISIBLE);
    //    final SelectedMemberOfGroup selectGroupMember = new SelectedMemberOfGroup();

        holder.tvimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getAdapterPosition();
              //  selectGroupMember.prepareSelection(v,holder.getAdapterPosition(), holder.);
            }
        });

        //  Glide.with(ctx).load(list.get(position).getImage()).crossFade().placeholder(R.drawable.facebook_avatar).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.tvimage);
     //   String imagess = list.get(position).getImage();
        //  setImage(holder.tvimage, holder);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int pos = holder.getAdapterPosition();
             //   userimages = list.get(pos).getImage();
                usernames = list.get(pos).getName();
                usersid = list.get(pos).getUserid();


            }
        });

    }


    @Override
    public int getItemCount() {

        if(list!= null){
            return list.size();}
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class RecyclerForFriendHolder extends RecyclerView.ViewHolder {


        private TextView mName;
       private CircleImageView tvimage;
        private View view;




        public RecyclerForFriendHolder(View itemView) {
            super(itemView);
            view = itemView;

            tvimage = (CircleImageView) itemView.findViewById(R.id.member_group);
            mName = (TextView) itemView.findViewById(R.id.textselectedgroup);




        }


    }
}



