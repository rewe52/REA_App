package com.example.vladislav.rea_attention;

import android.util.Log;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * Created by Vladislav on 07.09.2017.
 */

public class CheckInternetConnection {

    private static final String TAG = "ip_adress_exception_get";

    private static String getIpAdress() {
        String result = null;
                try {
                    for (Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces(); interfaces.hasMoreElements();) {
                        NetworkInterface iface = interfaces.nextElement();
                        for (Enumeration<InetAddress> adresses = iface.getInetAddresses(); adresses.hasMoreElements();) {
                            InetAddress ip = adresses.nextElement();
                            if (!ip.isLoopbackAddress())
                                result = ip.getHostAddress();
                        }
                    }
                } catch (SocketException e) {
                    Log.d(TAG, e.getMessage());
                }
        return result;
    }

    public boolean isReallyOnline(){
        if(!getIpAdress().isEmpty()){
            return true;
        }else
            return false;

    }

}