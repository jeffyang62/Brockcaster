package com.example.jeff.brockcaster;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.view.View.generateViewId;

public class chatrooms extends AppCompatActivity {
    private DatabaseReference Database;
    private static final String TAG = "Chatrooms";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatrooms);
        Intent intent = getIntent();
        //intent.getDataString();
       // String message = intent.getStringExtra(MainActivity.class.);

        // Capture the layout's TextView and set the string as its text
        Button button = (Button) findViewById(R.id.room1);
        button.setTag(1);

        Intent service = new Intent(getApplicationContext(), GPSService.class);
        this.startService(service);


    }

    public void createRoom(View view){
       // Database = FirebaseDatabase.getInstance().getReference();
      //  int id = generateViewId();
        // need to find latest id
       // Room room = new Room(id);

        //Database.child("rooms"+id).setValue(room);


        //Button button = (Button) findViewById(R.id.room1);
        //button.setTag(id);

        Intent intent = new Intent(getBaseContext(), roomCreation.class);
        intent.putExtra("ID", "10");

        startActivity(intent);

    }

    public void openChat(View view){
        Log.v(TAG, String.valueOf(view.getId()));

        //Button button = (Button) findViewById(R.id.room1);

        Intent intent = new Intent(getBaseContext(), chatActivity.class);
        intent.putExtra("ID", String.valueOf(view.getTag()));
        startActivity(intent);


    }
}
