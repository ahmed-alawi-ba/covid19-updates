package com.alawi.ahmed.covid19updates;


import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;

import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.net.InetAddress;


public class Network {


    Context context;



    public Network(Context context) {
        this.context = context;
    }


    ////////     Methods to check internet connection  /////////////

    public boolean checkInternetConnection() {
        boolean connected = false;
        try {
            if (isNetworkConnected() && isConnected()) {
                connected = true;
            } else {
                Toast.makeText(context, "Cannot establish an internet connection. Check your connection and try again."
                        , Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
//            Toast.makeText(context, "Cannot establish an internet connection. Check your connection and try again. "
//                    + e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.i("checkInternetConnection", "checkInternetConnection: " + e.getMessage());
        }

        return connected;
    }

    private boolean isNetworkConnected() {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    public boolean isConnected() throws InterruptedException, IOException {
        final String command = "ping -c 1 google.com";
        boolean success;
        success = Runtime.getRuntime().exec(command).waitFor() == 0;
        return success;
    }

    public boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com");
            //You can replace it with your name
            return !ipAddr.equals("");

        } catch (Exception e) {
            return false;
        }
    }
}

