package com.getweatherdatatesttask;

import com.google.android.gms.maps.model.LatLng;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;

public class WeatherRequest {
    private static final String QUERY_BY_COORDINATES_URL = "http://api.openweathermap.org/data/2.5/weather?lat=%f&lon=%f&units=metric&lang=ru";
    private static final String API_KEY = "&APPID=230817152f335fdaca4e9ba99a186825";

    public String getWeatherDataByCoordinates(LatLng latLng) {
        double latitude = latLng.latitude;
        double longitude = latLng.longitude;
        String urlText = String.format(Locale.getDefault(), QUERY_BY_COORDINATES_URL, latitude, longitude);

        HttpURLConnection connection = null;
        BufferedReader reader = null;
        String responseTextJSON = null;
        try {
            URL url = new URL(urlText.concat(API_KEY));
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                responseTextJSON = readAllLine(reader);
            } else {
                throw new UnsupportedOperationException();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return responseTextJSON;
    }

    private String readAllLine(BufferedReader reader) throws IOException {
        String line;
        StringBuilder stringBuilder = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }
        return stringBuilder.toString();
    }

    public enum WeatherRequestType {
        BY_COORDINATES, BY_PLACE
    }
}