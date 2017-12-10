package com.example.jeff.brockcaster;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import com.firebase.ui.database.FirebaseListAdapter;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity  implements ActivityCompat.OnRequestPermissionsResultCallback {

    private FirebaseListAdapter<ChatMessage> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);


        startActivity(new Intent(MainActivity.this, chatrooms.class));


    }





}


