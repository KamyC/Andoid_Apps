package com.jinghan.memo.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import java.io.IOException;
import java.util.List;

public class LocationUtil {

    public static double latitude = 0.0;
    public static double longitude = 0.0;
    public static LocationManager locationManager;
    public static Location location;
    private static String provider;

    public static void initLocation(Context context) {

        LocationListener locationListener = new LocationListener() {

            @Override
            public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onProviderEnabled(String arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onProviderDisabled(String arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onLocationChanged(Location arg0) {
                // TODO Auto-generated method stub
            }
        };

        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        List<String> list = locationManager.getProviders(true);

        if (list.contains(LocationManager.GPS_PROVIDER)) {
            provider = LocationManager.GPS_PROVIDER;
        } else if (list.contains(LocationManager.NETWORK_PROVIDER)) {
            provider = LocationManager.NETWORK_PROVIDER;
        } else {
            Toast.makeText(context, "Check if GPS is Turned On",
                    Toast.LENGTH_LONG).show();
            return;
        }

        if (Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        location = locationManager.getLastKnownLocation(provider);

        if (location != null) {
            String stringPosition = "Latitude" + location.getLatitude() + ", Longitude"
                    + location.getLongitude();
            longitude = location.getLongitude();
            latitude = location.getLatitude();
            Toast.makeText(context, stringPosition, Toast.LENGTH_LONG).show();
        }

        locationManager.requestLocationUpdates(provider, 2000, 2, locationListener);
    }

    public static String getAddress(Location location, Context context) throws IOException {
        if (location == null) {
            return "";
        }

        Geocoder geocoder = new Geocoder(context);
        boolean flag = Geocoder.isPresent();
        StringBuilder stringBuilder = new StringBuilder();
        try {

            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                    stringBuilder.append(address.getAddressLine(i)).append("\n");
                }
                stringBuilder.append(address.getCountryName()).append("_");
                stringBuilder.append(address.getFeatureName()).append("_");
                stringBuilder.append(address.getLocality()).append("_");
                stringBuilder.append(address.getPostalCode()).append("_");
                stringBuilder.append(address.getCountryCode()).append("_");
                stringBuilder.append(address.getAdminArea()).append("_");
                stringBuilder.append(address.getSubAdminArea()).append("_");
                stringBuilder.append(address.getThoroughfare()).append("_");
                stringBuilder.append(address.getSubLocality()).append("_");
                stringBuilder.append(address.getLatitude()).append("_");
                stringBuilder.append(address.getLongitude());
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}