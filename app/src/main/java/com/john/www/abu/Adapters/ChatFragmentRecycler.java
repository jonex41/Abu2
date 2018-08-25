package com.john.www.abu.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.john.www.abu.Activitiesss.ChatActivity;
import com.john.www.abu.DatabaseStuffs.sqlUsers.DatabaseHelper;
import com.john.www.abu.DatabaseStuffs.sqlUsers.UserDatabase;
import com.john.www.abu.DownloadImage;
import com.john.www.abu.Fragment.PicturePreviewDialog;

import com.john.www.abu.GlideApp;
import com.john.www.abu.R;
import com.john.www.abu.helper.Constants;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by MR AGUDA on 16-Nov-17.
 */
public class ChatFragmentRecycler extends RecyclerView.Adapter<ChatFragmentRecycler.RecyclerForFriendHolder>   {

    private Context ctx;
    private ArrayList<UserDatabase> list;
    private String usersid;
    private String userimages;
    private String usernames;
    private String friends;
    private Bitmap bitmapp;

    private DatabaseHelper databaseHelper;
    private FirebaseFirestore firestore;
    private DocumentReference documentReference;
    private CollectionReference collectionReference;
    private String status;
    private byte[] bytesq;
    private byte[] bytesq2;
    private byte[] bytesq3;
    private String imagePath;


    public ChatFragmentRecycler(Context ctx, ArrayList<UserDatabase> list) {
        this.ctx = ctx;
        this.list = list;
    }

    @Override
    public RecyclerForFriendHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerForFriendHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.allusersview, parent, false));
    }


    @Override
    public void onBindViewHolder(final RecyclerForFriendHolder holder, final int position) {
        databaseHelper = new DatabaseHelper(ctx);
        UserDatabase userDatabase = list.get(position);
        userimages = userDatabase.getImage();
        usernames = userDatabase.getName();
        usersid = userDatabase.getUserid();
        status = userDatabase.getStatus();
        holder.mName.setText(list.get(position).getName());
        holder.mStatus.setText(list.get(position).getStatus());
      //  Glide.with(ctx).load(list.get(position).getImage()).crossFade().placeholder(R.drawable.facebook_avatar).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.tvimage);
        UserDatabase userDatabase1 = new UserDatabase(usernames, status, userimages, usersid);
        GlideApp.with(ctx).load(userimages).error(R.drawable.facebook_avatar).placeholder(R.drawable.facebook_avatar).into(holder.tvimage);

       /*  if(!databaseHelper.checkUser(usersid)) {



             databaseHelper.insertRecord(userDatabase1);

         }*/

           /* new DownloadImage().execute(userimages);

                if(!TextUtils.isEmpty(imagePath)){
                   // databaseHelper.insertRecord2(userDatabase1,imagePath );
                 //   GlideApp.with(ctx).load(imagePath).error(R.drawable.facebook_avatar).placeholder(R.drawable.facebook_avatar).into(holder.tvimage);

                    if(databaseHelper.checkUser(usersid)){

                        databaseHelper.UpdatesFriends2(userDatabase1, imagePath);
                    }else {
                        databaseHelper.insertRecord2(userDatabase1, imagePath);
                    }
                }*/
        DownloadImage.startActionFoo(ctx, userimages, usersid);



        holder.imagelayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int  pos = holder.getAdapterPosition();
                userimages = list.get(pos).getImage();
                usernames = list.get(pos).getName();
                usersid = list.get(pos).getUserid();
                status = list.get(pos).getStatus();

              /*  Bundle bundle = new Bundle();
                bundle.putString("image", userimages);

                PicturePreviewDialog picturePreview = new PicturePreviewDialog();
                picturePreview.setArguments(bundle);
                FragmentTransaction ft =((FragmentActivity)ctx).getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.parent_fragment_container, picturePreview);

                ft.addToBackStack(null);
                ft.commit();*/
                Bundle bundle = new Bundle();
              //  bundle.putByteArray("image", userimages);
                bundle.putString("name", usernames);
                bundle.putString("userid", usersid);
                bundle.putString("status", status);


                PicturePreviewDialog dialog = new PicturePreviewDialog();
                dialog.setArguments(bundle);
                dialog.show(((FragmentActivity)ctx).getSupportFragmentManager(), null);
            }
        });
        holder.chatlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int  pos = holder.getAdapterPosition();
                userimages = list.get(pos).getImage();
                usernames = list.get(pos).getName();
                usersid = list.get(pos).getUserid();


                Intent intent1 = new Intent(ctx, ChatActivity.class);
                intent1.putExtra(Constants.OTHERSKEY, usersid);
                intent1.putExtra(Constants.OTHERSIMAGE, userimages);
                intent1.putExtra(Constants.OTHERSNAME, usernames);
                ctx.startActivity(intent1);



            }
        });
        holder.chatlayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int pos = holder.getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(ctx);

                    builder.setMessage("Do you want to delete user")
                            .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    int pos = holder.getAdapterPosition();

                                    databaseHelper.deleteRecord(usersid);

                                    list.remove(pos);
                                    notifyItemRemoved(pos);
                                    notifyItemRangeChanged(pos, list.size());
                                    notifyDataSetChanged();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog dialog = builder.create();
                    dialog.setTitle("Delete");
                    dialog.show();
                }
                return true;
            }
        });
    }




    private class DownloadImaged extends AsyncTask<String, Void, Bitmap> {
        private String TAG = "DownloadImage";
        private Bitmap downloadImageBitmap(String sUrl) {
            Bitmap bitmap = null;
            try {
                InputStream inputStream = new URL(sUrl).openStream();   // Download Image from URL
                bitmap = BitmapFactory.decodeStream(inputStream);       // Decode Bitmap
                inputStream.close();
            } catch (Exception e) {
                Log.d(TAG, "Exception 1, Something went wrong!");
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            return downloadImageBitmap(params[0]);
        }

        protected void onPostExecute(Bitmap result) {
          imagePath = saveImage(ctx, result, usersid+".png");
        }
    }
    public String saveImage(Context context, Bitmap b, String imageName) {
        FileOutputStream foStream;
        try {
            foStream = context.openFileOutput(imageName, Context.MODE_PRIVATE);
            b.compress(Bitmap.CompressFormat.PNG, 100, foStream);
            foStream.close();
            Toast.makeText(ctx, "image is now saved", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.d("saveImage", "Exception 2, Something went wrong!");
            Toast.makeText(ctx, "error", Toast.LENGTH_SHORT).show();

            e.printStackTrace();
        }

        File file = ctx.getFileStreamPath(imageName);
        if(file.exists()) {
            String imageFullPath = file.getAbsolutePath();

            return imageFullPath;
        }
        return null;
    }




    public byte[] getByteFromUrl(String url) {
        try {
            URL url1 = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) url1.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            bitmapp = BitmapFactory.decodeStream(inputStream);

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmapp.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            return bos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Toast.makeText(ctx, "yes oo, i no dey, i no dey again oooooooo",Toast.LENGTH_LONG ).show();
        return null;
    }

        public static byte[] profileImage(Bitmap b){

       ByteArrayOutputStream bos = new ByteArrayOutputStream();
       b.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        return bos.toByteArray();

    }
    private Bitmap convertToBitmap(byte[] b){

        return BitmapFactory.decodeByteArray(b, 0, b.length);

    }
    private void showfragment(Fragment fragmentById) {
        FragmentTransaction ft =((FragmentActivity)ctx).getSupportFragmentManager().beginTransaction();
        ft.show(fragmentById);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.addToBackStack(null);
        ft.commit();
    }



    private class GetImageFromUrl extends AsyncTask<String, Void, Bitmap>{


        @Override
        protected Bitmap doInBackground(String... strings) {
            String url = strings[0];
            bitmapp = null;

           /* try{
                InputStream is = new java.net.URL(url).openStream();
                bitmapp = BitmapFactory.decodeStream(is);

                return bitmapp;
            }catch (Exception e){
                e.printStackTrace();
            }
*/


           try{
               URL url1 = new URL(url);
               HttpURLConnection connection = (HttpURLConnection) url1.openConnection();
               connection.setDoInput(true);
               connection.connect();
               InputStream inputStream = connection.getInputStream();
               bitmapp = BitmapFactory.decodeStream(inputStream);

            return bitmapp;
           }catch (Exception e){
               e.printStackTrace();
           }

            return bitmapp;
        }

        @Override
        protected void onPostExecute(Bitmap b) {
            super.onPostExecute(b);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            b.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bytesq =  bos.toByteArray();

        }
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



    private void DisplayDialog() {


      /*  Dialog d = new Dialog(ctx);
        d.setContentView(R.layout.image_dialog);
        d.setTitle("Picture");
        d.show();*/
    }




    class RecyclerForFriendHolder extends RecyclerView.ViewHolder  {

        private ImageView imageView;
        private
        CheckBox onlineimage;
        private TextView mName, mTime, mStatus;
        private ImageButton imageButton;
        private LinearLayout imagelayout, chatlayout, buttonlayout;
        CircleImageView tvimage;


        public RecyclerForFriendHolder(View itemView) {
            super(itemView);


            tvimage = (CircleImageView) itemView.findViewById(R.id.imageview_profile);
            mName = (TextView) itemView.findViewById(R.id.tv_name);
            mTime = (TextView) itemView.findViewById(R.id.tv_time);
            mStatus = (TextView) itemView.findViewById(R.id.tv_status);
            imagelayout = (LinearLayout) itemView.findViewById(R.id.imageLinearLayout);
            chatlayout = (LinearLayout) itemView.findViewById(R.id.chatlayout);
            buttonlayout = (LinearLayout) itemView.findViewById(R.id.buttonlayout);
            onlineimage = (CheckBox) itemView.findViewById(R.id.check_button);


        }



    }
}



