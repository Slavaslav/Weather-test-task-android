package com.getweatherdatatesttask.Place;

import android.os.AsyncTask;

import com.getweatherdatatesttask.HttpRequestClient;
import com.getweatherdatatesttask.MapsActivity;
import com.getweatherdatatesttask.Weather.WeatherShowable;
import com.google.android.gms.maps.model.LatLng;

public class PlaceRequestCoordinatesTask extends AsyncTask<String, Void, LatLng> implements WeatherShowable {
    private MapsActivity activity;

    public PlaceRequestCoordinatesTask(MapsActivity activity) {
        this.activity = activity;
    }

    @Override
    protected LatLng doInBackground(String... query) {
        String placeDescriptionJSON = HttpRequestClient.getPlaceDescriptionByQuery(query[0]);
        LatLng latLng = null;
        if (!placeDescriptionJSON.isEmpty()) {
            latLng = PlaceJSONParser.parsePlaceCoordinatesFromJson(placeDescriptionJSON);
        }
        return latLng;
    }

    @Override
    protected void onPostExecute(LatLng latLng) {
        showWeather(latLng);
    }

    @Override
    public void showWeather(LatLng latLng) {
        activity.showWeather(latLng);
    }
}
