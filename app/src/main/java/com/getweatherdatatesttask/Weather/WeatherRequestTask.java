package com.getweatherdatatesttask.Weather;

import android.os.AsyncTask;

import com.getweatherdatatesttask.HttpRequestClient;
import com.getweatherdatatesttask.MapsActivity;
import com.google.android.gms.maps.model.LatLng;

public class WeatherRequestTask extends AsyncTask<Void, Void, Weather> {

    private final WeatherShowable.RequestType type;
    private final LatLng latLng;
    private MapsActivity.UIupdateable updateUI;

    public WeatherRequestTask(WeatherShowable.RequestType type, LatLng latLng, MapsActivity.UIupdateable updateUI) {
        this.type = type;
        this.latLng = latLng;
        this.updateUI = updateUI;
    }

    @Override
    protected Weather doInBackground(Void... voids) {
        Weather weather = null;
        String weatherJSON = HttpRequestClient.getWeatherDataByCoordinates(latLng);
        // if weatherJSON is empty - something went wrong
        if (!weatherJSON.isEmpty()) {
            weather = WeatherJSONParser.parseWeatherFromJson(weatherJSON);
        }
        return weather;
    }

    @Override
    protected void onPostExecute(Weather weather) {
        super.onPostExecute(weather);
        updateUI.updateUI(weather, type, latLng);
    }
}