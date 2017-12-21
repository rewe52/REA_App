package com.example.vladislav.rea_attention;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

/**
 * Created by Vladislav on 25.09.2017.
 */

public class DataBase{



    private final String USER_TYPE_OF_PROBLEM = "TypeOfProblem";
    private final String USERS_CHILD = "Users";
    private final String USER_MESSAGES = "Messages";
    private final String USER_MESSAGE_TYPE = "MessageType";
    private final String USER_MESSAGE_TEXT = "MessageText";
    public ArrayList<String> arrayList;
    DatabaseReference userRef;

    public boolean sendDataToFireBase(UsersMessage message, FirebaseAuth mAuth, DatabaseReference dRef) {
        try {
            String current_user = mAuth.getInstance().getCurrentUser().getUid();
            dRef.child(USERS_CHILD).child(current_user).child(USER_MESSAGES).child(message.id).child(USER_MESSAGE_TYPE).setValue(message.type);
            dRef.child(USERS_CHILD).child(current_user).child(USER_MESSAGES).child(message.id).child(USER_MESSAGE_TEXT).setValue(message.text);
            dRef.child(USERS_CHILD).child(current_user).child(USER_MESSAGES).child(message.id).child(USER_TYPE_OF_PROBLEM).setValue(message.typeOfProblem);
            return true;
        }catch (DatabaseException e){
            Log.d("DB Exception", e.getMessage());
            return false;
        }
    }

    public String getUniqueKeyToMessage(DatabaseReference dRef, FirebaseAuth mAuth){
        String UID = mAuth.getInstance().getCurrentUser().getUid();
        return dRef.child(USERS_CHILD).child(UID).child(USER_MESSAGES).push().getKey();
    }




}
