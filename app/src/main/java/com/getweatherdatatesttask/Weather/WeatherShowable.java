package com.getweatherdatatesttask.Weather;

import com.google.android.gms.maps.model.LatLng;

public interface WeatherShowable {
    void showWeather(LatLng latLng, RequestType requestType);

    enum RequestType {
        BY_COORDINATES, BY_CURRENT_LOCATION
    }
}