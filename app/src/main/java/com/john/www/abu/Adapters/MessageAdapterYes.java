package com.john.www.abu.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;


import com.google.firebase.auth.FirebaseAuth;
import com.john.www.abu.DatabaseStuffs.sqlMessage.DatMessage;
import com.john.www.abu.DatabaseStuffs.sqlMessage.Manss;
import com.john.www.abu.GlideApp;
import com.john.www.abu.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



/**
 * Created by MR AGUDA on 19-Apr-18.
 */

    public class MessageAdapterYes extends RecyclerView.Adapter {
        private static final int VIEW_TYPE_MESSAGE_SENT = 1;
        private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;
    private static final int VIEW_TYPE_TEXT = 3;

    private static final int VIEW_TYPE_MESSAGE_VIDEO = 5;
    private static final int VIEW_TYPE_MESSAGE_AUDIO = 6;
    private static final int VIEW_TYPE_MESSAGE_PDF =7;
    private static final int VIEW_TYPE_PICTURE =7;

    private static final int VIEW_TYPE_TEXT2 = 8;
    private static final int VIEW_TYPE_PICTURE2 =9;
    private static final int VIEW_TYPE_MESSAGE_VIDEO2 = 10;
    private static final int VIEW_TYPE_MESSAGE_AUDIO2 = 11;
    private static final int VIEW_TYPE_MESSAGE_PDF2 = 12;


        private Context ctx;
        private List<Manss> mMessageList;
        private DatMessage datMessage;
    private FirebaseAuth mAuth;
    private String otherskey;
    private String mykey;

    public MessageAdapterYes(Context context, List<Manss> messageList) {
            ctx = context;
            mMessageList = messageList;
        }

        @Override
        public int getItemCount() {
            return mMessageList.size();
        }




   @Override
    public int getItemViewType(int position) {
       Manss messagess = (Manss) mMessageList.get(position);


       mAuth = FirebaseAuth.getInstance();
       String uidme = mAuth.getCurrentUser().getUid();
       String friendid = messagess.getSenderId();
       String myId = messagess.getRecieverId();

       String type = messagess.getType().toString();

       if (friendid.equals(uidme)) {

           if (type.contains("text")) {
               return VIEW_TYPE_TEXT;
           } else if (type.contains("picture")) {
               return VIEW_TYPE_PICTURE;
           }else if(type.contains("audio")){
               return VIEW_TYPE_MESSAGE_AUDIO;
           }


       } else {
           if (type.contains("text")) {
               return VIEW_TYPE_TEXT2;
           }
           else if (type.contains("picture")) {

               return VIEW_TYPE_PICTURE2;
           }else if(type.contains("audio")){
               return VIEW_TYPE_MESSAGE_AUDIO2;
           }
       }
       return 0;
       }







        // Inflates the appropriate layout according to the ViewType.
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view;


               if( viewType==VIEW_TYPE_TEXT) {

                   view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_recyclerstructure, parent, false);
                   return new SentMessageHolder(view);

               }

                 if (viewType==VIEW_TYPE_TEXT2) {
                    view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chatrecycler2, parent, false);
                    return new ReceivedMessageHolder(view);
                }
            if (viewType==VIEW_TYPE_PICTURE) {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_picture_singlelayout1, parent, false);
                return new SentPictureHolder(view);
            }
            if (viewType==VIEW_TYPE_PICTURE2) {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_picture_single_layout2, parent, false);
                return new ReceivedPicture2Holder(view);
            }



            if (viewType==VIEW_TYPE_MESSAGE_AUDIO) {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_for_audio_chat1, parent, false);
                return new SendAudioHolder1(view);
            }
            if (viewType==VIEW_TYPE_MESSAGE_AUDIO2) {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_for_audio_chat1, parent, false);
                return new ReceivedAudioHolder2(view);
            }



                return null;
        }

        // Passes the message object to a ViewHolder so that the contents can be bound to UI.
        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            Manss message = (Manss) mMessageList.get(position);

            switch (holder.getItemViewType()) {
                case VIEW_TYPE_TEXT:
                    ((SentMessageHolder) holder).bind(message);
                    break;
                case VIEW_TYPE_TEXT2:
                    ((ReceivedMessageHolder) holder).bind(message);
                    break;
                case VIEW_TYPE_PICTURE:
                    ((SentPictureHolder) holder).bind(message);
                    break;
                case VIEW_TYPE_PICTURE2:
                    ((ReceivedPicture2Holder) holder).bind(message);
                    break;
                case VIEW_TYPE_MESSAGE_AUDIO:
                    ((SendAudioHolder1) holder).bind(message);
                    break;
                case VIEW_TYPE_MESSAGE_AUDIO2:
                    ((ReceivedAudioHolder2) holder).bind(message);
                    break;


            }


        }

    private class SentPictureHolder extends RecyclerView.ViewHolder {
        TextView timeText;
        ImageView imageView;
        LinearLayout relativeLayout;

        SentPictureHolder(View itemView) {
            super(itemView);

            timeText = (TextView) itemView.findViewById(R.id.mytime);
            relativeLayout = (LinearLayout) itemView.findViewById(R.id.deleteMessage);
            imageView = (ImageView) itemView.findViewById(R.id.my_picture_sent);
        }


        void bind(Manss message) {
           // messageText.setText(message.getMessage());
            /*RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.drawable.image_placeholder);*/
            byte[] bytes = message.getSendImage();

            if(bytes != null) {
                Bitmap bp = convertToBitmap(message.getSendImage());
                GlideApp.with(ctx).load(bp).into(imageView);
            }
            relativeLayout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);

                        builder.setMessage("Do you want to delete message")
                                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        int pos = getAdapterPosition();
                                        datMessage = new DatMessage(ctx);
                                        mykey = mMessageList.get(pos).getSenderId();
                                        otherskey = mMessageList.get(pos).getRecieverId();
                                        String usersid = mykey + otherskey;
                                        datMessage.deleteMessage(usersid);

                                        mMessageList.remove(pos);
                                        notifyItemRemoved(pos);
                                        notifyItemRangeChanged(pos, mMessageList.size());
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
                        dialog.setTitle("Delete message");
                        dialog.show();
                    }

                    return true;
                }
            });
            // Format the stored timestamp into a readable String using method.
            //  timeText.setText(Utils.formatDateTime(message.getCreatedAt()));
        }
        private Bitmap convertToBitmap(byte[] b){

            return BitmapFactory.decodeByteArray(b, 0, b.length);

        }
    }

    private class ReceivedPicture2Holder extends RecyclerView.ViewHolder {
        TextView timeText;
        ImageView imageView;
        LinearLayout relativeLayout;

        ReceivedPicture2Holder(View itemView) {
            super(itemView);

           imageView = (ImageView) itemView.findViewById(R.id.picture_received);
             timeText = (TextView) itemView.findViewById(R.id.mytime);
            relativeLayout = (LinearLayout) itemView.findViewById(R.id.deleteMessage);
        }


        void bind(Manss message) {
          //  messageText.setText(message.getMessage());
            /*RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.drawable.image_placeholder);*/
            byte[] bytes = message.getSendImage();

            if(bytes != null) {
                Bitmap bp = convertToBitmap(message.getSendImage());
                GlideApp.with(ctx).load(bp).into(imageView);
            }
            relativeLayout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);

                        builder.setMessage("Do you want to delete message")
                                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        int pos = getAdapterPosition();
                                        datMessage = new DatMessage(ctx);
                                        mykey = mMessageList.get(pos).getSenderId();
                                        otherskey = mMessageList.get(pos).getRecieverId();
                                        String usersid = mykey + otherskey;
                                        datMessage.deleteMessage(usersid);

                                        mMessageList.remove(pos);
                                        notifyItemRemoved(pos);
                                        notifyItemRangeChanged(pos, mMessageList.size());
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
                        dialog.setTitle("Delete message");
                        dialog.show();
                    }

                    return true;
                }
            });
            // Format the stored timestamp into a readable String using method.
            //  timeText.setText(Utils.formatDateTime(message.getCreatedAt()));
        }

        private Bitmap convertToBitmap(byte[] b){

            return BitmapFactory.decodeByteArray(b, 0, b.length);

        }
    }


        private class SentMessageHolder extends RecyclerView.ViewHolder {
            TextView messageText, timeText;
            RelativeLayout relativeLayout;
            ImageView imageView;

            SentMessageHolder(View itemView) {
                super(itemView);


                messageText = (TextView) itemView.findViewById(R.id.mymessage);
              //  timeText = (TextView) itemView.findViewById(R.id.text_message_time);
                 relativeLayout = (RelativeLayout) itemView.findViewById(R.id.deleteMessage);
            }


            void bind(Manss message) {

                    String messagesd = message.getMessage().toString().trim();
                    if(!messagesd.contains(" ")) {
                        messageText.setText(messagesd);
                    }




                relativeLayout.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        int pos = getAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION) {

                            AlertDialog.Builder builder = new AlertDialog.Builder(ctx);

                            builder.setMessage("Do you want to delete message")
                                    .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            int pos = getAdapterPosition();
                                            datMessage = new DatMessage(ctx);
                                            mykey = mMessageList.get(pos).getSenderId();
                                            otherskey = mMessageList.get(pos).getRecieverId();
                                            String usersid = mykey + otherskey;
                                            datMessage.deleteMessage(usersid);

                                            mMessageList.remove(pos);
                                            notifyItemRemoved(pos);
                                            notifyItemRangeChanged(pos, mMessageList.size());
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
                            dialog.setTitle("Delete message");
                            dialog.show();
                        }

                        return true;
                    }
                });
                // Format the stored timestamp into a readable String using method.
              //  timeText.setText(Utils.formatDateTime(message.getCreatedAt()));
            }
        }

        private class ReceivedMessageHolder extends RecyclerView.ViewHolder {
            TextView messageText, timeText, nameText;
            ImageView profileImage;
            RelativeLayout relativeLayout1;
            ImageView imageView;

            ReceivedMessageHolder(View itemView) {
                super(itemView);

                imageView = (ImageView) itemView.findViewById(R.id.picture_received);
                messageText = (TextView) itemView.findViewById(R.id.mymessage);
              //  timeText = (TextView) itemView.findViewById(R.id.text_message_time);
             //   nameText = (TextView) itemView.findViewById(R.id.text_message_name);
              profileImage = (ImageView) itemView.findViewById(R.id.otherImage);
              relativeLayout1 = (RelativeLayout) itemView.findViewById(R.id.deleteMessage);
            }

            void bind(Manss message) {

                String messagesd = message.getMessage().toString().trim();
                if(!messagesd.contains(" ")) {
                    messageText.setText(messagesd);
                }
                relativeLayout1.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {

                        int pos = getAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION) {

                            AlertDialog.Builder builder = new AlertDialog.Builder(ctx);

                            builder.setMessage("Do you want to delete message")
                                    .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            int pos = getAdapterPosition();
                                            datMessage = new DatMessage(ctx);
                                            mykey = mMessageList.get(pos).getSenderId();
                                            otherskey = mMessageList.get(pos).getRecieverId();
                                            String usersid = mykey + otherskey;
                                            datMessage.deleteMessage(usersid);

                                            mMessageList.remove(pos);
                                            notifyItemRemoved(pos);
                                            notifyItemRangeChanged(pos, mMessageList.size());
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
                            dialog.setTitle("Delete message");
                            dialog.show();
                        }

                        return true;
                }
                });
                // Format the stored timestamp into a readable String using method.
             //   timeText.setText(Utils.formatDateTime(message.getCreatedAt()));

             //   nameText.setText(message.getSender().getNickname());

                // Insert the profile image from the URL into the ImageView.
             //   Utils.displayRoundImageFromUrl(mContext, message.getSender().getProfileUrl(), profileImage);
            }
        }

    private class SendAudioHolder1 extends RecyclerView.ViewHolder {

        private ImageView play_audio;
        private SeekBar seekBar_progress;
        private TextView total_length, progress_audio;
        private MediaPlayer mediaPlayer;
        private Handler handler;
        private Runnable runnable;


        SendAudioHolder1(View itemView) {
            super(itemView);

            play_audio = (ImageView) itemView.findViewById(R.id.play_audio);
            seekBar_progress = (SeekBar) itemView.findViewById(R.id.my_audio_seekbar);
            total_length = (TextView) itemView.findViewById(R.id.total_length);
            progress_audio = (TextView) itemView.findViewById(R.id.progress_audio);

            handler = new Handler();
            mediaPlayer = new MediaPlayer();


        }

        void bind(Manss message) {

            try {
              //  Uri uri = Uri.fromFile(new File(message.getSend_audio()));
              //  total_length.setText(mediaPlayer.getDuration());
                mediaPlayer.setDataSource(ctx,  Uri.parse(message.getSend_audio()));
                seekBar_progress.setProgress(mediaPlayer.getCurrentPosition());

                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {

                        changeseekbar();
                    }
                });
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
seekBar_progress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        if(b){
            mediaPlayer.seekTo(i);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
});


            play_audio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mediaPlayer.isPlaying()){
                        mediaPlayer.pause();
                        play_audio.setImageResource(R.drawable.video_pause);
                    }else {
                        mediaPlayer.start();
                        play_audio.setImageResource(R.drawable.video_play);
                        changeseekbar();
                    }
                }
            });

        }
        private void changeseekbar() {

            if(mediaPlayer.isPlaying()){

                runnable = new Runnable() {
                    @Override
                    public void run() {
                    //  progress_audio.setText(mediaPlayer.getDuration()-mediaPlayer.getCurrentPosition());
                        changeseekbar();
                    }
                };
            handler.postDelayed(runnable,  1000);
            }

        }
    }



    private class ReceivedAudioHolder2 extends RecyclerView.ViewHolder {


        ReceivedAudioHolder2(View itemView) {
            super(itemView);


        }

        void bind(Manss message) {


        }
    }

}
