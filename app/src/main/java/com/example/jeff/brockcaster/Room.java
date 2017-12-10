package com.example.jeff.brockcaster;

import java.util.List;

/**
 * Created by jeff on 07/12/2017.
 */

public class Room {

    public long roomID;
    public double radius;
    public double longitude;
    public double latitude;
    public List<ChatMessage> messagesList;
    public String roomName;

    public Room(long roomID, String roomname, double longitude, double latitude){
        this.roomID = roomID;
        this.roomName = roomname;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Room(){
    }

    public String getName(){return roomName;}
    public double getLongitude(){
        return longitude;
    }

    public double getLatitude(){
        return latitude;
    }

    public List<ChatMessage> getMessage(){
        return messagesList;
    }

}
