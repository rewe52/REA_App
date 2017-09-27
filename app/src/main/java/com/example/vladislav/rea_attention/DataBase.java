package com.example.vladislav.rea_attention;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Vladislav on 25.09.2017.
 */

public class DataBase {

    private final String USER_CHILD_NAME = "Users";
    private final String USER_TYPE_OF_PROBLEM = "Type Of Problem";

    FirebaseDatabase mDataBase = FirebaseDatabase.getInstance();
    DatabaseReference dRef = mDataBase.getReference();

    public void addUserToDataBase(String uid) {
             dRef.child(USER_CHILD_NAME).child(uid);
    }

    public void addMessageToUserDataBase(final String uid, final String message){
       new Thread(new Runnable() {
           @Override
           public void run() {
               dRef.child(USER_CHILD_NAME).child(uid).child(USER_TYPE_OF_PROBLEM).setValue(message);
           }
       });
    }

}
