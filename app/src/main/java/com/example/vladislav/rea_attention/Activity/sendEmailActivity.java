package com.example.vladislav.rea_attention.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import com.example.vladislav.rea_attention.R;
import com.example.vladislav.rea_attention.UsersMessage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class sendEmailActivity extends AppCompatActivity {

    private SharedPreferences mSettings;
    private final String USER_PROBLEM = "CATEGORIES";

    private final String USERS_CHILD = "Users";
    private final String USER_MESSAGES= "Messages";
    private final String USER_MESSAGE_TYPE= "MessageType";
    private final String USER_MESSAGE_TEXT= "MessageText";

    private String[] categories = {
            "Проблемы с преподавателями",
            "Проблемы в столовой",
            "Проблемы с общежитием",
            "Другое",
            "Для проверки",
            "Для проверки",
            "Для проверки",
            "Для проверки",
            "Для проверки",
            "Для проверки",
            "Для проверки"
    };

    private EditText edit_message;
    private Button send_message;
    private Switch change_type_message;
    FirebaseDatabase mDataBase;
    DatabaseReference mRef;
    FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_email);

        mDataBase = FirebaseDatabase.getInstance();
        mRef = mDataBase.getReference();
        mAuth.getInstance();


        edit_message = (EditText)findViewById(R.id.edit_message);
        send_message = (Button)findViewById(R.id.send_message);
        change_type_message = (Switch)findViewById(R.id.change_type_message);


        send_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uniqeID = mRef.push().getKey().toString();
                String result = "";
                if(change_type_message.isChecked()) {
                    result = "OPEN";
                }else
                    {
                        result = "CLOSE";
                    }
                String message_text = edit_message.getText().toString();
                UsersMessage message = new UsersMessage(uniqeID, result, message_text);
                sendDataToFireBase(message, mAuth);
            }
        });

    }

    private void sendDataToFireBase(UsersMessage message, FirebaseAuth mAuth){

        String current_user = mAuth.getInstance().getCurrentUser().getUid();
                //mRef.child(USERS_CHILD).child(currentUser);
                //mRef.child(USERS_CHILD).child(currentUser).child(USER_MESSAGES).child(message.id);
                mRef.child(USERS_CHILD).child(current_user).child(USER_MESSAGES).child(message.id).child(USER_MESSAGE_TYPE).setValue(message.type);
                mRef.child(USERS_CHILD).child(current_user).child(USER_MESSAGES).child(message.id).child(USER_MESSAGE_TEXT).setValue(message.text);

        }

        private void takeDataFromFirebase(){
            Query my_message= mRef.child(USERS_CHILD)
                    .orderByChild(USER_MESSAGES);
            my_message.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

}
