package com.example.vladislav.rea_attention.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vladislav.rea_attention.ChangeStateProgressBar;
import com.example.vladislav.rea_attention.DataBase;
import com.example.vladislav.rea_attention.EditSharedPreferences;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.example.vladislav.rea_attention.R.id;
import static com.example.vladislav.rea_attention.R.layout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = "activity_main";
    private static final String APP_PREFERENCES_PASSWORD = "Password";
    public static final String APP_PREFERENCES_USER = "User";

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListner;


    private TextView email;
    private TextView password;
    private CheckBox remember_me;
    private ProgressBar progressBar;


    SharedPreferences mSettings;

    EditSharedPreferences editSharedPreferences = new EditSharedPreferences();

    ChangeStateProgressBar changeStateProgressBar = new ChangeStateProgressBar();

    //CheckInternetConnection checkInternetConnection = new CheckInternetConnection();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);


        mSettings = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);

        mAuth = FirebaseAuth.getInstance();     // get instance of fireBase

        DataBase db = new DataBase();

        if(mAuth.getCurrentUser()!=null){
            db.connectToDataBase();
            if(checkAdmin(mAuth.getCurrentUser().getEmail())){
                Log.d(TAG, "Переход на страницу администратора");
                Intent intent = new Intent(MainActivity.this, admin_Activity.class);
                startActivity(intent);
            }else{
                Log.d(TAG,"Переход на страницу пользователя");
                Intent intent = new Intent(MainActivity.this, Categories.class);
                startActivity(intent);
            }
        }



        progressBar = (ProgressBar)findViewById(id.progressBar);
        email = (TextView) findViewById(id.email);
        password = (TextView) findViewById(id.password);
        remember_me = (CheckBox)findViewById(id.Remember_Me);


        // check file APP_PREFERENCES. If it consist
        // APP_PREFERENCES_USER code need to set email text view
        if(mSettings.contains(APP_PREFERENCES_USER)){
            Log.d(TAG, "Save User login into the mSettings/APP_PREFERENCES_USER");
            email.setText(mSettings.getString(APP_PREFERENCES_USER, ""));
        }

        if(mSettings.contains(APP_PREFERENCES_PASSWORD)) {
            Log.d(TAG, "Save User password into the mSettings/APP_PREFERENCES_PASSWORD");
            password.setText(mSettings.getString(APP_PREFERENCES_PASSWORD, ""));
        }

        // if user connect or disconnect listener recieves
        // an answer of instance
        mAuthListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d(TAG, "Sign_in" + user.getUid());
                } else {
                    Log.d(TAG, "Sign_out");
                }
            }
        };


        //find buttons
        findViewById(id.sign_in_Button).setOnClickListener(this);
        findViewById(id.registration_Button).setOnClickListener(this);

    }


    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListner);
    }

    public void onStop() {
        super.onStop();

        //must to remove of listener when
        // app closed
        if (mAuthListner != null) {
            mAuth.removeAuthStateListener(mAuthListner);
        }
    }


    @Override
    public void onClick(View v) {
             if (v.getId() == id.sign_in_Button) {

                 //check email and password rows for filling
                 // if any rows an empty we take an
                 // toast error
                 signIn();
             }

             // if we press registration button

            if (v.getId() == id.registration_Button) {
            // show progress bar

                changeStateProgressBar.showProgressBar(progressBar);

                //start registration activity
                Intent intent = new Intent(MainActivity.this, RegistrationActivity.class);
                startActivity(intent);

                changeStateProgressBar.hideProgressBar(progressBar);

            }
    }



    private void signIn(){
        if ((!email.getText().toString().equals(null)
                && !email.getText().toString().equals(""))
                || (!password.getText().toString().equals(null)
                && !password.getText().toString().equals(""))) {

            //start progress bar visibility, when we do
            // hard piece of code
            changeStateProgressBar.showProgressBar(progressBar);

            // try to sign in with email and password

            mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())

                    // need to add complete listener to track of successful doing
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            //if task is successfull need to go to another intent
                            if (task.isSuccessful()) {



                                //delete from settings
                                editSharedPreferences.deletePreferences(mSettings, APP_PREFERENCES_PASSWORD);
                                editSharedPreferences.deletePreferences(mSettings,APP_PREFERENCES_USER);

                                if(remember_me.isChecked()) {
                                    // remember me
                                    editSharedPreferences.addPreferences(mSettings, APP_PREFERENCES_USER, email.getText().toString());
                                    editSharedPreferences.addPreferences(mSettings, APP_PREFERENCES_PASSWORD, password.getText().toString());
                                }
                                // check email for administrator
                                // email, if administrator entered
                                // start entent Administrator activity
                                if (checkAdmin(email.getText().toString())) {

                                    //start admin activity
                                    Intent intent = new Intent(MainActivity.this, admin_Activity.class);
                                    startActivity(intent);

                                    // hide progress bar
                                    changeStateProgressBar.hideProgressBar(progressBar);

                                    email.setText("");
                                    password.setText("");

                                } else{

                                    // if not admin entered, start personal activity
                                    Log.d(TAG, "Succesful enter");

                                    //start new activity
                                    Intent intent = new Intent(MainActivity.this, Categories.class);
                                    startActivity(intent);

                                    // HIDE PROGRESS bar
                                    changeStateProgressBar.hideProgressBar(progressBar);

                                    email.setText("");
                                    password.setText("");
                                }
                            }
                        }
                    })

                    // if in process of entering
                    // we have an errors we must
                    // send toast with code of error
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // make toast message if there is anyobe exception
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            password.setText("");
                            changeStateProgressBar.hideProgressBar(progressBar);
                        }
                    });
        }else{
            // make toast message if pass or email rows is empty
            Toast.makeText(MainActivity.this, "Password or email is empty", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkAdmin(String email){
        if(email.equals("admin@rea.ru")){

            return true;
        }else{

            return false;
        }

    }


}

