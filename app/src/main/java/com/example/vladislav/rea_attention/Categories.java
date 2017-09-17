package com.example.vladislav.rea_attention;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

import static com.example.vladislav.rea_attention.R.id;
import static com.example.vladislav.rea_attention.R.layout;

public class Categories extends AppCompatActivity implements View.OnClickListener {

    private EditSharedPreferences editSharedPreferences;
    private static final String APP_PREFERENCES_USER = "User";
    private static final String APP_PREFERENCES_PASSWORD = "Password";

    SharedPreferences mSettings;

    Button problemsWithTeachers, signOut;
    private static final String TAG = "CATEGORIES";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_categories);

        mSettings = PreferenceManager.getDefaultSharedPreferences(Categories.this);

        problemsWithTeachers = (Button)findViewById(id.SignOutBut);
        signOut = (Button)findViewById(id.problemsWithTeachersBut);
        signOut.setOnClickListener(this);
        problemsWithTeachers.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == id.SignOutBut){
            logout();

        }else if(v.getId() == id.problemsWithTeachersBut){
            signOut.setVisibility(View.GONE);
        }

    }


    private void logout(){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
        editSharedPreferences.deletePreferences(mSettings, APP_PREFERENCES_USER);
        editSharedPreferences.deletePreferences(mSettings,APP_PREFERENCES_PASSWORD);
        Intent intent = new Intent(Categories.this, MainActivity.class);
        startActivity(intent);
        }

    }
