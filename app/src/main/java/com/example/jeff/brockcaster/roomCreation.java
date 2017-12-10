package com.example.jeff.brockcaster;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class roomCreation extends AppCompatActivity {
    int id;
    private DatabaseReference Database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_creation);

        id = Integer.parseInt(getIntent().getStringExtra("ID"));


    }

    public void createRoom(View view){
        Database = FirebaseDatabase.getInstance().getReference();
        EditText nameText = (EditText) findViewById(R.id.nameText);
        EditText longitude = (EditText) findViewById(R.id.longitude);
        EditText latitude = (EditText) findViewById(R.id.latitude);

        Room room = new Room(id, nameText.getText().toString(), Double.parseDouble(longitude.getText().toString()), Double.parseDouble(latitude.getText().toString()));

        Database.child("rooms"+id).setValue(room);


        // if successful return to chats page.
        startActivity(new Intent(getBaseContext(), chatrooms.class));
    }


}
