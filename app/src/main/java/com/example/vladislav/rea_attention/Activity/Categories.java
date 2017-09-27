package com.example.vladislav.rea_attention.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.vladislav.rea_attention.R.id;
import static com.example.vladislav.rea_attention.R.layout;

public class Categories extends AppCompatActivity{


    private static final String TAG = "CATEGORIES";



    FirebaseAuth mAuth;

    private ListView all_problems_list_view;
    private Button add_problem_button, signOut;

    FirebaseDatabase mDataBase;
    DatabaseReference dRef;
    private static long back_pressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_categories);

        mAuth = FirebaseAuth.getInstance();


        mDataBase = FirebaseDatabase.getInstance();
        dRef = mDataBase.getReference();

        //region add all items

        all_problems_list_view = (ListView) findViewById(id.all_problems_list_view);
        add_problem_button = (Button) findViewById(id.add_problem_button);
        signOut = (Button) findViewById(id.SignOutBut);
        //endregion

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
        //endregion


     //   List<String> all_open_questions = new ArrayList<String>();

       // ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, all_open_questions);

        //all_problems_list_view.setAdapter(adapter);


        /*//region add array list

        final ArrayAdapter<String> adapter_add_problem_list = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, list_of_categories);
        add_problem_list_view.setAdapter(adapter_add_problem_list);
        //endregion



        dRef.child(CHILD_MESSAGE).child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UsersMessage message = dataSnapshot.getChildren(UsersMessage.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
*/
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
