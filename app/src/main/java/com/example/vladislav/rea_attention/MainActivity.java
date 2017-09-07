package com.example.vladislav.rea_attention;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.LongSparseArray;
import android.view.View;

import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.view.View.VISIBLE;
import static com.example.vladislav.rea_attention.R.*;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = "activity_main";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListner;
    private TextView email;
    private TextView password;
    private boolean isOnline;
    private CheckInternetConnection checkInternet = new CheckInternetConnection();
    private ProgressBar progressBar;
    @Override



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);

        progressBar = (ProgressBar)findViewById(id.progressBar);


        email = (TextView) findViewById(id.email);
        password = (TextView) findViewById(id.password);


        mAuth = FirebaseAuth.getInstance();     // get instance of fireBase

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

        findViewById(id.sign_in_Button).setOnClickListener(this);
        findViewById(id.registration_Button).setOnClickListener(this);

    }

    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListner);
    }

    public void onStop() {
        super.onStop();
        if (mAuthListner != null) {
            mAuth.removeAuthStateListener(mAuthListner);
        }
    }

    @Override
    public void onClick(View v) {
        progressBar.setVisibility(ProgressBar.VISIBLE);
             if (v.getId() == id.sign_in_Button) {
                 Handler singingHandler = new Handler();
                 Thread singingThread = new Thread();
                 mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                         .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                             @Override
                             public void onComplete(@NonNull Task<AuthResult> task) {
                                 if (task.isSuccessful()) {
                                     Log.d(TAG, "Succesful enter");
                                     Intent intent = new Intent(MainActivity.this, personalActivity.class);
                                     startActivity(intent);
                                 }
                             }
                         });
                 progressBar.setVisibility(View.INVISIBLE);
             }


                     /*            Toast.makeText(MainActivity.this, "Successful enter", Toast.LENGTH_SHORT).show();
                             } else {
                                 Toast.makeText(MainActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                             }
                         }
                     });
                 }catch (Exception t){
                     Toast.makeText(getApplicationContext(), "ошибка", Toast.LENGTH_SHORT).show();
                 }*/

            if (v.getId() == id.registration_Button) {
                progressBar.setVisibility(View.VISIBLE);
                Intent intent = new Intent(MainActivity.this, RegistrationActivity.class);
                startActivity(intent);
                progressBar.setVisibility(View.INVISIBLE);
            }
    }

  /*  public void isReallyOnline() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Runtime runtime = Runtime.getRuntime();
                    Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
                    int exitValue = ipProcess.waitFor();
                    isOnline = true;
                }catch (InterruptedException e) {
                    e.printStackTrace();
                    isOnline = false;
                } catch (IOException e) {
                    e.printStackTrace();
                    isOnline=false;
                }
                isOnline = false;
            }
        });
    }

*/

}

