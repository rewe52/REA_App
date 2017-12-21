package com.example.vladislav.rea_attention.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.example.vladislav.rea_attention.DataBase;
import com.example.vladislav.rea_attention.R;
import com.example.vladislav.rea_attention.UsersMessage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class sendEmailActivity extends AppCompatActivity {

    private static final String TAG = "ToChtoPrishloISFB";
    DatabaseReference mDataBase;
    FirebaseAuth mAuth;
    DataBase dataBaseFunctions = new DataBase();
    private EditText edit_message;
    private Button send_message;
    private Switch change_type_message;
    private ArrayList<String> type_of_problem;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_email);

        mDataBase = FirebaseDatabase.getInstance().getReferenceFromUrl("https://reaattention.firebaseio.com/");
        mAuth.getInstance();

        type_of_problem = new ArrayList<>();
        type_of_problem.add("Проблемы с учителями");
        type_of_problem.add("Проблемы в столовой");
        type_of_problem.add("Другое");

        edit_message = (EditText) findViewById(R.id.edit_message);
        send_message = (Button) findViewById(R.id.send_message);
        change_type_message = (Switch) findViewById(R.id.change_type_message);
        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, type_of_problem);
        spinner.setAdapter(adapter);


        send_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edit_message.getText().toString().equals("")) {
                    Toast.makeText(sendEmailActivity.this,
                            "Вы не можете отправлять пустое сообщение", Toast.LENGTH_LONG).show();
                } else {
                    String uniqeID = dataBaseFunctions.getUniqueKeyToMessage(mDataBase, mAuth);
                    String result = "";
                    if (change_type_message.isChecked()) {
                        result = "OPEN";
                    } else {
                        result = "CLOSE";
                    }
                    String message_text = edit_message.getText().toString();
                    UsersMessage message = new UsersMessage(uniqeID, result, message_text, type_of_problem.get(spinner.getSelectedItemPosition()));
                    if (dataBaseFunctions.sendDataToFireBase(message, mAuth, mDataBase)) {
                        startActivity(new Intent(sendEmailActivity.this, Categories.class));
                    }
                }
            }

        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(sendEmailActivity.this, Categories.class));
    }
}
