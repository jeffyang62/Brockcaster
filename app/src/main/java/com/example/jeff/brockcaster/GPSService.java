package com.example.jeff.brockcaster;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.util.Log;
/**
 * Created by jeff on 07/12/2017.
 */

public class GPSService extends Service {

    public LocationManager locationManager;
    public double longitudeGPS, latitudeGPS;
    public LocationListener locationListener;

    public Context context = this;
    private static final String TAG = "GPSSystem";
    
    @Override
    public void onCreate() {
        Log.v(TAG, "Starting Thread");
        if (locationManager == null) {
            locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        }
        locationListener = new LocationListener(LocationManager.GPS_PROVIDER);
        GPSStart();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {

                }
                return;
            }
        }
    }

    private boolean checkLocation() {
        if (!isLocationEnabled()) {
            showAlert();
        }
        return isLocationEnabled();
    }

    private boolean isLocationEnabled() {
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

            return true;
        } else return false;
    }


    public boolean checkLocationPermission() {
        String permission = "android.permission.ACCESS_FINE_LOCATION";
        int res = this.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }


    private void showAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Enable Location")
                .setMessage("Your Locations Settings is set to 'Off'." +
                        "\nPlease Enable Location to " + "use this app")
                .setPositiveButton("Location Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    }
                });
        dialog.show();
    }

    public void GPSStart() {
        if (!checkLocation()) {
            return;
        } else {
            try {
                if (checkLocationPermission()) {
                    locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER, 5000, 10f, locationListener);
                }
            } catch (SecurityException e) {
            }
        }
    }

        private class LocationListener implements android.location.LocationListener {

            Location mLastLocation;

            public LocationListener(String provider)
            {
                Log.e(TAG, "LocationListener " + provider);
                mLastLocation = new Location(provider);
            }

            public void onLocationChanged(Location location) {
                longitudeGPS = location.getLongitude();
                latitudeGPS = location.getLatitude();

                mLastLocation.set(location);


                Log.v(TAG, String.valueOf(longitudeGPS));
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        }

}
