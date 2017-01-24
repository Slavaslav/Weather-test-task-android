package com.getweatherdatatesttask;

import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

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
        String currentPosition = String.format(Locale.getDefault(), "%.2f, %.2f", latLng.latitude, latLng.longitude);
        if (marker == null) {
            marker = mMap.addMarker(new MarkerOptions().position(latLng));
        } else {
            marker.setPosition(latLng);
        }
        marker.setTitle(currentPosition);
    }

    private void showWeatherData(LatLng latLng) {
        WeatherRequestTask weatherRequestTask = new WeatherRequestTask(WeatherHttpRequest.RequestType.BY_COORDINATES);
        weatherRequestTask.execute(latLng);
    }

    private void showPopupWindow(Weather weather) {
        View popupView = getLayoutInflater().inflate(R.layout.weather_popup_window, null);
        View rootElement = popupView.findViewById(R.id.map_root_element);

        TextView latitudeTextView = (TextView) popupView.findViewById(R.id.latitude_text_view);
        TextView longitudeTextView = (TextView) popupView.findViewById(R.id.longitude_text_view);
        TextView weatherDescriptionTextView = (TextView) popupView.findViewById(R.id.weather_description_text_view);
        TextView temperatureTextView = (TextView) popupView.findViewById(R.id.temperature_text_view);
        TextView pressureTextView = (TextView) popupView.findViewById(R.id.pressure_text_view);
        TextView humidityTextView = (TextView) popupView.findViewById(R.id.humidity_text_view);
        TextView windSpeedTextView = (TextView) popupView.findViewById(R.id.wind_speed_text_view);
        TextView windDegreesTextView = (TextView) popupView.findViewById(R.id.wind_degrees_text_view);
        Button closePopupButton = (Button) popupView.findViewById(R.id.close_popup_button);

        latitudeTextView.setText(String.format(Locale.getDefault(), "%.2f", weather.getLatitude()));
        longitudeTextView.setText(String.format(Locale.getDefault(), "%.2f", weather.getLongitude()));
        weatherDescriptionTextView.setText(weather.getWeatherDescription());
        temperatureTextView.setText(String.format(Locale.getDefault(), "%.1f", weather.getTemperature()));
        pressureTextView.setText(String.format(Locale.getDefault(), "%.0f", weather.getPressure()));
        humidityTextView.setText(String.valueOf(weather.getHumidity()));
        windSpeedTextView.setText(String.format(Locale.getDefault(), "%.1f", weather.getWindSpeed()));
        windDegreesTextView.setText(String.format(Locale.getDefault(), "%.0f", weather.getWindDegrees()));

        final PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        // setBackgroundDrawable and setOutsideTouchable for close popup when click outside
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAtLocation(rootElement, Gravity.CENTER, 0, 0);

        closePopupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
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
            showPopupWindow(weather);
        }
    }
}