package com.example.footballapp.ui.footballclubs;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import com.example.footballapp.BuildConfig;
import com.example.footballapp.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.footballapp.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private double latitude;
    private double longitude;
    private double mylatitude;
    private double mylongitude;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent mapIntent = getIntent();
        latitude = mapIntent.getDoubleExtra("latitude", 0);
        longitude = mapIntent.getDoubleExtra("longitude", 0);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            mylatitude = location.getLatitude();
                            mylongitude = location.getLongitude();

                            LatLng currentLocation = new LatLng(mylatitude, mylongitude);
                            mMap.addMarker(new MarkerOptions().position(currentLocation).title("You"));
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
                            LatLng city = new LatLng(latitude, longitude);
                            mMap.addMarker(new MarkerOptions().position(city).title("City of the club"));
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(city));
                            buildRoute(currentLocation, city);
                        }else{
                            LatLng currentLocation = new LatLng(50.4501, 30.5234);
                            mMap.addMarker(new MarkerOptions().position(currentLocation).title("You default"));
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
                            LatLng city = new LatLng(latitude, longitude);
                            mMap.addMarker(new MarkerOptions().position(city).title("City of the club"));
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(city));
                            buildRoute(currentLocation, city);
                        }
                    }
                });
    }


    private void buildRoute(LatLng myDestination, LatLng clubDestination) {
        String url = "https://maps.googleapis.com/maps/api/directions/json?origin="
                + myDestination.latitude + "," + myDestination.longitude
                + "&destination=" + clubDestination.latitude + "," + clubDestination.longitude
                + "&key=" + BuildConfig.MAPS_API_KEY;

        OkHttpClient client = new OkHttpClient();

        new Thread(() -> {
            try {
                Request request = new Request.Builder().url(url).build();
                Response response = client.newCall(request).execute();

                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    JSONObject json = new JSONObject(responseData);
                    JSONArray routes = json.getJSONArray("routes");

                    if (routes.length() > 0) {
                        JSONArray steps = routes.getJSONObject(0)
                                .getJSONArray("legs").getJSONObject(0)
                                .getJSONArray("steps");

                        ArrayList<LatLng> path = new ArrayList<>();

                        for (int i = 0; i < steps.length(); i++) {
                            JSONObject startLocation = steps.getJSONObject(i)
                                    .getJSONObject("start_location");
                            double lat = startLocation.getDouble("lat");
                            double lng = startLocation.getDouble("lng");
                            path.add(new LatLng(lat, lng));
                        }

                        runOnUiThread(() -> drawPath(path));
                    }
                }
            } catch (IOException | org.json.JSONException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void drawPath(ArrayList<LatLng> path) {
        PolylineOptions polylineOptions = new PolylineOptions()
                .addAll(path)
                .color(0xFF0000FF);

        mMap.addPolyline(polylineOptions);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            onMapReady(mMap);
        }
    }
}