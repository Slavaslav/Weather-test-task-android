package com.getweatherdatatesttask;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        initializeUIMap();
        moveCameraDefault();

        mMap = googleMap;
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                String currentPosition = String.format(Locale.getDefault(), "latitude = %f, longitude = %f", latLng.latitude, latLng.longitude);
                Toast toast = Toast.makeText(getApplicationContext(), currentPosition, Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }

    private void initializeUIMap() {
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(false);
    }

    private void moveCameraDefault() {
        LatLng kiev = new LatLng(50.431622, 30.516645);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(kiev));
    }
}