package com.example.vladislav.rea_attention;

import android.view.View;
import android.widget.ProgressBar;

/**
 * Created by Vladislav on 17.09.2017.
 */

public class ChangeStateProgressBar {
    public void showProgressBar(ProgressBar progressBar){
        progressBar.setVisibility(View.VISIBLE);
    }
    public void hideProgressBar(ProgressBar progressBar){
        progressBar.setVisibility(View.INVISIBLE);
    }
}
