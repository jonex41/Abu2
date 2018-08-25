package com.john.www.abu.helper;

import android.app.Service;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by MR AGUDA on 08-Mar-18.
 */

public class CheckConnection {


    private Context context;

    public CheckConnection(Context context) {
        this.context = context;
    }

    public   boolean isConnected(){


        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Service.CONNECTIVITY_SERVICE);
        if(connectivityManager !=null){
            NetworkInfo info = connectivityManager.getActiveNetworkInfo();

            //you can use info.getTypeName(), to get the real type, maybe wifi or data.
            if(info !=null){

                if(info.getState() == NetworkInfo.State.CONNECTED){

                    return true;
                }
            }
        }
        return false;
    }
}
