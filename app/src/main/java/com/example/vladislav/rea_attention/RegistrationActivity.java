package com.example.vladislav.rea_attention;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
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
    MainActivity main_Activity = new MainActivity();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        progressBar = (ProgressBar)findViewById(R.id.registrationProgressBar);
        email = (TextView) findViewById(R.id.registrationEmail);
        password = (TextView) findViewById(R.id.passwordRegistration);
        confirmPassword = (TextView) findViewById(R.id.passwordConfirmRegistration);

        mAuth = FirebaseAuth.getInstance();

       findViewById(R.id.registrationButtonActivity).setOnClickListener(this);
        findViewById(R.id.backButton).setOnClickListener(this);
    }

    public void onClick(View v) {
        if (v.getId() == R.id.registrationButtonActivity) {

            progressBar.setVisibility(View.VISIBLE);

            if(email.getText().toString().equals("") && password.getText().toString().equals("") && confirmPassword.getText().toString().equals("")){
                String passwordTest = password.getText().toString();
                String confirmPasswordTest = confirmPassword.getText().toString();
                if (password.getText().equals(confirmPassword.getText().toString())&& passwordTest.length()>=6) {
                    mAuth.createUserWithEmailAndPassword(email.getText().toString(), passwordTest).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(RegistrationActivity.this, "Registration complite", Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "Succesful registration");
                                Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                                startActivity(intent);
                                progressBar.setVisibility(View.INVISIBLE);
                            }
                        }
                    });
                } else {
                    Toast.makeText(RegistrationActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Passwords do not match");
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }else {
                Toast.makeText(RegistrationActivity.this, "Your email or password are empty. Try again!", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Error, any lines are empty");
                progressBar.setVisibility(View.INVISIBLE);
            }

        }
        else if(v.getId()==R.id.backButton){
            progressBar.setVisibility(View.VISIBLE);
            Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }
    }
