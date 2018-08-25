package com.john.www.abu;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class DownloadImage extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO = "com.john.www.abu.action.FOO";
    private static final String ACTION_BAZ = "com.john.www.abu.action.BAZ";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "com.john.www.abu.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.john.www.abu.extra.PARAM2";
    private static final String BroadcastKey = "BroadcastKey";
    private static final String ImageURL = "image_url";

    public DownloadImage() {
        super("DownloadImage");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionFoo(Context context, String param1, String param2) {
        Intent intent = new Intent(context, DownloadImage.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, DownloadImage.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FOO.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
               Bitmap imageUrl =  handleActionFoo(param1, param2);
              /* String realPath  = saveImage(getApplicationContext(),imageUrl, )
                sendCashbackInfoToClient();*/
            } else if (ACTION_BAZ.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionBaz(param1, param2);
            }
        }
    }




    private void sendCashbackInfoToClient(String msg){
        Intent intent = new Intent();
        intent.setAction(BroadcastKey);
        intent.putExtra(ImageURL,msg);
        sendBroadcast(intent);
    }
    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private Bitmap handleActionFoo(String param1, String param2) {
        Bitmap bitmap = null;
        try {
            InputStream inputStream = new URL(param1).openStream();   // Download Image from URL
            bitmap = BitmapFactory.decodeStream(inputStream);       // Decode Bitmap
            inputStream.close();
        } catch (Exception e) {
            Log.d("TAG", "Exception 1, Something went wrong!");
            e.printStackTrace();
        }

        return bitmap;
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
