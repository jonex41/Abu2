package com.john.www.abu.Fragment;



import android.app.Dialog;

import android.support.v4.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;

import android.support.annotation.NonNull;




import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.john.www.abu.Activitiesss.JustPicture;
import com.john.www.abu.GlideApp;
import com.john.www.abu.R;

/**
 * Created by MR AGUDA on 20-Apr-18.
 */

public class PicturePreviewDialog extends DialogFragment implements View.OnClickListener {


    private View mRootView;
    private String image;
    private String name;
    private String userid;
    private String status;
    private String thumbImage;


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());



        mRootView = getActivity().getLayoutInflater().inflate(R.layout.picturepreview_frag, null);



        ImageView imageView = (ImageView) mRootView.findViewById(R.id.displayfriendprof);
        TextView textView = (TextView) mRootView.findViewById(R.id.dialognamestuff);
         image=this.getArguments().getString("image").toString();
         thumbImage=this.getArguments().getString("thumbImage").toString();
        name=this.getArguments().getString("name").toString();
        status=this.getArguments().getString("status").toString();

        textView.setText(name);
        userid=this.getArguments().getString("userid").toString();


        GlideApp.with(getActivity()).load(image).placeholder(R.drawable.facebook_avatar).thumbnail(
                Glide.with(getActivity()).load(thumbImage)
        ).into(imageView);
       // GlideApp.with(getActivity()).load(image).error(R.drawable.facebook_avatar).placeholder(R.drawable.facebook_avatar).into(imageView);
        imageView.setOnClickListener(this);
        builder.setView(mRootView);
        return builder.create();
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.displayfriendprof:
               // startActivity(new Intent(getActivity(), FriendProfile.class));
                    dismiss();
                Intent intent = new Intent(getActivity(), JustPicture.class);
               intent.putExtra("image", image );
                intent.putExtra("thmbimage", thumbImage );
                intent.putExtra("name", name );
                intent.putExtra("userid", userid );
                intent.putExtra("status", status );


               startActivity(intent);
                break;


        }
    }


}
