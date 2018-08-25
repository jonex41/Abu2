
package com.john.www.abu.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.john.www.abu.Activitiesss.SelectedMemberOfGroup;
import com.john.www.abu.DatabaseStuffs.sqlUsers.DatabaseHelper;
import com.john.www.abu.DatabaseStuffs.sqlUsers.UserDatabase;
import com.john.www.abu.Interfaces.OnItemclicklistener;
import com.john.www.abu.R;


import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by MR AGUDA on 16-Nov-17.
 */
public class UserAdapterSelecting extends RecyclerView.Adapter<UserAdapterSelecting.RecyclerForFriendHolder> {

    private Context ctx;
    private ArrayList<UserDatabase> list;
    private String usersid;
    private String userimages;
    private String usernames;
    private String friends;
    private FirebaseFirestore firestore;
    private CollectionReference collectionReference;
    private DatabaseHelper databaseHelper;
    private SelectedMemberOfGroup selectGroupMember;
    private OnItemclicklistener itemClickListener;




    public UserAdapterSelecting(Context ctx, ArrayList<UserDatabase> list) {
        this.ctx = ctx;
        this.list = list;
    }

    @Override
    public RecyclerForFriendHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.allusersview, parent, false);

        RecyclerForFriendHolder recyclerForFriendHolder = new RecyclerForFriendHolder(view);
        return recyclerForFriendHolder;
        //return new RecyclerForFriendHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.allusersview, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerForFriendHolder holder,  int position) {

        holder.mName.setText(list.get(position).getName());
        holder.mStatus.setText(list.get(position).getStatus());
        holder.checkbox.setVisibility(View.VISIBLE);
        final SelectedMemberOfGroup selectGroupMember = new SelectedMemberOfGroup();


    }


    @Override
    public int getItemCount() {

        if(list!= null){
            return list.size();}
        return 0;

        /*return cities == null ? 0 : cities.size();*/
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void setClickListener(OnItemclicklistener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }




    class RecyclerForFriendHolder extends RecyclerView.ViewHolder{

        private ImageView imageView;
        private CheckBox checkbox;
        private TextView mName, mTime, mStatus;
        private ImageButton imageButton;
        private LinearLayout linearLayout;
        CircleImageView tvimage;
        private View view;
        private SelectedMemberOfGroup selectGroupMember;




        public RecyclerForFriendHolder(View itemView) {
            super(itemView);
            view = itemView;


            tvimage = (CircleImageView) itemView.findViewById(R.id.imageview_profile);
            mName = (TextView) itemView.findViewById(R.id.tv_name);
            mStatus = (TextView) itemView.findViewById(R.id.tv_status);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.chatlayout);
            checkbox = (CheckBox) itemView.findViewById(R.id.check_button);

                    checkbox.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (itemClickListener != null) itemClickListener.Onclick(v, getAdapterPosition());
                        }
                    });

          //  itemView.setTag(checkbox);



        }



    }
}



