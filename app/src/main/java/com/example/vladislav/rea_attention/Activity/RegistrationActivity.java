package com.example.vladislav.rea_attention.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vladislav.rea_attention.ChangeStateProgressBar;
import com.example.vladislav.rea_attention.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "registration_activity";
    private TextView email;
    private TextView password;
    private TextView confirmPassword;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    public String createdEmail = null;
    public String createdPassword = null;
    private ProgressBar progressBar;
    com.example.vladislav.rea_attention.ChangeStateProgressBar ChangeStateProgressBar = new ChangeStateProgressBar();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        //add AuthListener
        mAuth = FirebaseAuth.getInstance();

        // find all visual objects
        progressBar = (ProgressBar) findViewById(R.id.registrationProgressBar);
        email = (TextView) findViewById(R.id.registrationEmail);
        password = (TextView) findViewById(R.id.passwordRegistration);
        confirmPassword = (TextView) findViewById(R.id.passwordConfirmRegistration);
        findViewById(R.id.registrationButtonActivity).setOnClickListener(this);
        findViewById(R.id.backButton).setOnClickListener(this);

    }


    public void onClick(View v) {
        if (v.getId() == R.id.registrationButtonActivity) {

            ChangeStateProgressBar.showProgressBar(progressBar);

            if ((!email.getText().toString().equals(null))
                    && (!password.getText().toString().equals(null))
                    && (!confirmPassword.getText().toString().equals("") || !confirmPassword.getText().toString().equals(null))) {

                if (password.getText().toString().equals(confirmPassword.getText().toString())) {

                    createNewUser();

                } else if (v.getId() == R.id.backButton) {
                    ChangeStateProgressBar.showProgressBar(progressBar);

                    Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            } else {
                //make toast message and log message
                Toast.makeText(RegistrationActivity.this, "Your email or password are empty. Try again!", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Error, any lines are empty");

                // if user has an error
                // set pass and confirm pass rows as empty
                password.setText(null);
                confirmPassword.setText(null);

                //hide progress bar
                ChangeStateProgressBar.hideProgressBar(progressBar);
            }
        }
        if(v.getId() == R.id.backButton){
            ChangeStateProgressBar.showProgressBar(progressBar);
            Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
            startActivity(intent);
            ChangeStateProgressBar.hideProgressBar(progressBar);

        }
    }

    private void createNewUser() {
        mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegistrationActivity.this, "Registration complite", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "Succesful registration");

                            //signIn
                            signIn();
                        } else {
                            // make toast and log messages
                            Toast.makeText(RegistrationActivity.this, "Passwords do not match or any rows are empty", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "Passwords do not match");

                            //hide progress bar
                            ChangeStateProgressBar.hideProgressBar(progressBar);
                        }
                    }
                });
    }

    private void signIn() {
        mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Log.d(TAG, "Succesful enter");

                            Intent intent = new Intent(RegistrationActivity.this, Categories.class);
                            startActivity(intent);

                            ChangeStateProgressBar.hideProgressBar(progressBar);
                        }
                    }
                })

                // if user has error
                // catch this error and send toast
                // message to user screen
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegistrationActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                        //hide progress bar
                        ChangeStateProgressBar.hideProgressBar(progressBar);
                    }
                });
    }
}
