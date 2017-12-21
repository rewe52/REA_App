package com.example.vladislav.rea_attention.Database;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Vladislav on 05.10.2017.
 */

public class GetMessagesFromFirebase{

    private final String USERS_CHILD = "Users";
    private final String USER_MESSAGES = "Messages";
    private final String USER_MESSAGE_TYPE = "MessageType";
    private final String USER_MESSAGE_TEXT = "MessageText";

    public ArrayList<String> arrayList;
    DatabaseReference userRef;



     public void startDownloadMessages(DatabaseReference databaseReference) throws InterruptedException {
        arrayList = new ArrayList<>();
        userRef = databaseReference.child(USERS_CHILD);
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String uid = ds.getKey();
                    userRef.child(uid).child(USER_MESSAGES).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot dsn : dataSnapshot.getChildren()) {
                                if (dsn.child(USER_MESSAGE_TYPE).getValue(String.class).equals("OPEN")) {
                                    String message = dsn.child(USER_MESSAGE_TEXT).getValue(String.class);
                                    arrayList.add(message);
                                    Log.d("array", message);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //handler.obtainMessage(1, arrayList).sendToTarget();
            }
        });
         while(arrayList.size()==0){
             wait(1000);
         }
    }

    public ArrayList<String> getArrayList() {
        return arrayList;
    }
}
