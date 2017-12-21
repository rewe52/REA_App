package com.example.vladislav.rea_attention.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.vladislav.rea_attention.DataBase;
import com.example.vladislav.rea_attention.Database.GetMessagesFromFirebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.vladislav.rea_attention.R.id;
import static com.example.vladislav.rea_attention.R.layout;




public class Categories extends AppCompatActivity {

    private static final String TAG = "CATEGORIES";
    private static long back_pressed;
    private final String USERS_CHILD = "Users";
    private final String USER_MESSAGES = "Messages";
    private final String USER_MESSAGE_TYPE = "MessageType";
    private final String USER_MESSAGE_TEXT = "MessageText";
    private final String USER_TYPE_OF_PROBLEM = "TypeOfProblem";
    int DATA_DOWNLOADED;
    FirebaseAuth mAuth;
    ArrayList<String> all_problems_array_list;
    ArrayAdapter<String> adapter;
    String typeOfProblem;
    DatabaseReference mDataBase;
    DatabaseReference userRef;
    DataBase dataBaseFunctions = new DataBase();
    GetMessagesFromFirebase getMessagesFromFirebase = new GetMessagesFromFirebase();
    private ListView all_problems_list_view;
    private Button add_problem_button, signOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_categories);


        mAuth = FirebaseAuth.getInstance();
        mDataBase = FirebaseDatabase.getInstance().getReferenceFromUrl("http://reaattention.firebaseio.com/");
        userRef = mDataBase.child(USERS_CHILD);
        //region add all items



        //find view
        all_problems_list_view = (ListView) findViewById(id.all_problems_list_view);
        add_problem_button = (Button) findViewById(id.add_problem_button);
        signOut = (Button) findViewById(id.SignOutBut);
        //endregion

        all_problems_array_list = new ArrayList<>();

        //region set onClick to buttons
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        add_problem_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Categories.this, sendEmailActivity.class));
            }
        });


        //endregiond



        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, all_problems_array_list);
        all_problems_list_view.setAdapter(adapter);


        //add autodownloader values from database
         userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                        String uid = ds.getKey();
                        userRef.child(uid).child(USER_MESSAGES).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for(DataSnapshot dsn: dataSnapshot.getChildren()){
                                    if(dsn.child(USER_MESSAGE_TYPE).getValue(String.class).equals("OPEN")) {
                                        String message = dsn.child(USER_MESSAGE_TEXT).getValue(String.class);
                                        typeOfProblem = dsn.child(USER_TYPE_OF_PROBLEM).getValue(String.class);
                                        all_problems_array_list.add(message);
                                        adapter.notifyDataSetChanged();
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

            }
        });
    }


    private void logout(){
        mAuth.signOut();
        Intent intent = new Intent(Categories.this, MainActivity.class);
        startActivity(intent);
        Toast.makeText(this, "Успешный выход!", Toast.LENGTH_SHORT).show();
        }

    public void onBackPressed(){
        if (back_pressed + 1000 > System.currentTimeMillis()) {
            logout();
        } else {
            Toast.makeText(this, "Вы уверены, что хотите выйти?", Toast.LENGTH_SHORT).show();
        }
        back_pressed = System.currentTimeMillis();

    }

}
