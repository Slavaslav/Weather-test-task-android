package com.getweatherdatatesttask;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Marker marker;

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
        mMap = googleMap;
        initializeUIMap();
        moveCameraDefault();
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                addMarkerToMapOnClick(latLng);
                showWeatherData(latLng);
            }
        });
    }

    private void initializeUIMap() {
        UiSettings uiSettings = mMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(true);
        uiSettings.setMapToolbarEnabled(false);
    }

    private void moveCameraDefault() {
        LatLng kiev = new LatLng(50.431622, 30.516645);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(kiev));
    }

    private void addMarkerToMapOnClick(LatLng latLng) {
        String currentPosition = String.format(Locale.getDefault(), "%f, %f", latLng.latitude, latLng.longitude);
        if (marker == null) {
            marker = mMap.addMarker(new MarkerOptions().position(latLng));
        } else {
            marker.setPosition(latLng);
        }
        marker.setTitle(currentPosition);
        marker.showInfoWindow();
    }

    private void showWeatherData(LatLng latLng) {
        WeatherRequestTask weatherRequestTask = new WeatherRequestTask(WeatherHttpRequest.RequestType.BY_COORDINATES);
        weatherRequestTask.execute(latLng);
    }

    private class WeatherRequestTask extends AsyncTask<Object, Void, Weather> {

        private WeatherHttpRequest.RequestType requestType;

        WeatherRequestTask(WeatherHttpRequest.RequestType requestType) {
            this.requestType = requestType;
        }

        @Override
        protected Weather doInBackground(Object... objects) {
            Weather weather = null;
            if (requestType == WeatherHttpRequest.RequestType.BY_COORDINATES) {
                String weatherJSON = WeatherHttpRequest.getWeatherDataByCoordinates((LatLng) objects[0]);
                weather = WeatherJSONParser.parseWeatherFromJson(weatherJSON);
            }
            return weather;
        }

        @Override
        protected void onPostExecute(Weather weather) {
            super.onPostExecute(weather);
            showPopupWindow();
        }
    }

    private void showPopupWindow() {
        View popupView = getLayoutInflater().inflate(R.layout.weather_popup_window, null);
        View rootElement = popupView.findViewById(R.id.map_root_element);
        PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.showAsDropDown(rootElement, 200, 200);
    }
}