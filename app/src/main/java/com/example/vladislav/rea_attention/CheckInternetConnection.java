package com.example.vladislav.rea_attention;

import android.app.Activity;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by Vladislav on 07.09.2017.
 */

public class CheckInternetConnection {
    private boolean isOnline;

     /*   new Thread(new Runnable() {
            @Override
            public void run() {
                Boolean result = false;
                HttpURLConnection con = null;
                try {
                    con = (HttpURLConnection) new URL("http://google.com/").openConnection();
                    con.setRequestMethod("HEAD");
                    result = (con.getResponseCode() == HttpURLConnection.HTTP_OK);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (con != null) {
                        try {
                            con.disconnect();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                if(result){
                     isOnline = true;
                }else
                    isOnline = false;
            }
        });

        if(isOnline){
            return true;
        }
        else{
            return false;
        }
    }

    */

}
