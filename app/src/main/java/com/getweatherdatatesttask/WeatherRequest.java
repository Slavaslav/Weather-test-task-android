package com.getweatherdatatesttask;

import com.google.android.gms.maps.model.LatLng;

public class WeatherRequest {
    private static final String QUERY_BY_COORDINATES_URL = "api.openweathermap.org/data/2.5/weather?";

    public String getWeatherDataByCoordinates(LatLng latLng) {
        return null;
    }

    public enum WeatherRequestType {
        BY_COORDINATES, BY_PLACE
    }
}