package com.example.jeff.brockcaster;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class chatActivity extends AppCompatActivity  implements ActivityCompat.OnRequestPermissionsResultCallback {

    private FirebaseListAdapter<ChatMessage> adapter;
    private int id = 1;
    private DatabaseReference Database;
    private static final String TAG = "Chat";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        id = Integer.parseInt(getIntent().getStringExtra("ID"));

        Log.v(TAG, getIntent().getStringExtra("ID"));
        // locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        Intent service = new Intent(getApplicationContext(), GPSService.class);
        this.startService(service);

        if(FirebaseAuth.getInstance().getCurrentUser() == null) {
            // Start sign in/sign up activity
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .build(),
                    1
            );
        } else {

            Toast.makeText(this,
                    "Welcome " + FirebaseAuth.getInstance()
                            .getCurrentUser()
                            .getDisplayName(),
                    Toast.LENGTH_LONG)
                    .show();

            displayChatMessages();
        }

        FloatingActionButton fab =
                (FloatingActionButton)findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText input = (EditText) findViewById(R.id.input);
                FirebaseDatabase.getInstance()
                        .getReference().child("rooms"+id).child("messagesList")
                        .push()
                        .setValue(new ChatMessage(input.getText().toString(),
                                FirebaseAuth.getInstance()
                                        .getCurrentUser()
                                        .getDisplayName())
                        );
                input.setText("");
            }

            // ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        });
    }

    private void displayChatMessages() {
        Log.v(TAG, "MADE IT to DISPLAYCHATMESSAGES");
        ListView listOfMessages = (ListView)findViewById(R.id.list_of_messages);

        Database = FirebaseDatabase.getInstance().getReference().child("rooms"+id).child("messagesList");



        Log.v(TAG, Database.toString());
        adapter = new FirebaseListAdapter<ChatMessage>(this, ChatMessage.class,
                R.layout.messagelayout, Database) {

            @Override
            protected void populateView(View v, ChatMessage model, int position) {
                // Get references to the views of message.xml
                TextView messageText = (TextView)v.findViewById(R.id.message_text);
                TextView messageUser = (TextView)v.findViewById(R.id.message_user);
                TextView messageTime = (TextView)v.findViewById(R.id.message_time);
                Log.v(TAG, "THIS IS THE MESSAGE "+model.getMessageText());
                // Set their text
                messageText.setText(model.getMessageText());
                messageUser.setText(model.getMessageUser());

                // Format the date before showing it
                messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",
                        model.getMessageTime()));
            }
        };



        listOfMessages.setAdapter(adapter);

        Log.v(TAG, "MADE IT HERE too");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1) {
            if(resultCode == RESULT_OK) {
                Toast.makeText(this,
                        "Successfully signed in. Welcome!",
                        Toast.LENGTH_LONG)
                        .show();
                displayChatMessages();
            } else {
                Toast.makeText(this,
                        "We couldn't sign you in. Please try again later.",
                        Toast.LENGTH_LONG)
                        .show();

                // Close the app
                finish();
            }
        }

    }




}
