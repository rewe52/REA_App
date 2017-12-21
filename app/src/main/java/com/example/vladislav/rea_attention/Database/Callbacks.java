package com.example.vladislav.rea_attention.Database;

import java.util.ArrayList;

/**
 * Created by Vladislav on 05.10.2017.
 */

public class Callbacks {
    Callback callback;

    public void registerCallback(Callback callback){
        this.callback = callback;
    }

    public interface Callback{
        ArrayList<String> callingBack();
    }
}
