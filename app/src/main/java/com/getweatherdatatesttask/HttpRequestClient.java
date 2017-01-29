package com.getweatherdatatesttask;

import com.google.android.gms.maps.model.LatLng;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;

public class HttpRequestClient {

    public static final String ERROR = "error";
    private static final String GOOGLE_API_URL = "https://maps.googleapis.com/maps/api/place/";
    private static final String GOOGLE_API_KEY = "&key=AIzaSyByodZEsDBTC-J3brJ39JiYTkqbtJhlSKo";
    private static final String WEATHER_API_URL = "http://api.openweathermap.org/data/2.5/weather?lat=%f&lon=%f&units=metric&APPID=9d600ee30b100b0a83a006ebdedd14ad";


    public static String getPlacesDataByQuery(String query) {
        String urlRequest = String.format("%sautocomplete/json?input=%s&types=geocode%s", GOOGLE_API_URL, query, GOOGLE_API_KEY);
        return getJsonTextFromURL(urlRequest);
    }

    public static String getPlaceDescriptionByQuery(String query) {
        String urlRequest = String.format("%sdetails/json?placeid=%s%s", GOOGLE_API_URL, query, GOOGLE_API_KEY);
        return getJsonTextFromURL(urlRequest);
    }

    public static String getWeatherDataByCoordinates(LatLng latLng) {
        double latitude = latLng.latitude;
        double longitude = latLng.longitude;
        // Locale.US for show a dot between the integer and decimal part not comma
        String urlRequest = String.format(Locale.US, WEATHER_API_URL, latitude, longitude);
        return getJsonTextFromURL(urlRequest);
    }

    private static String getJsonTextFromURL(String urlRequest) {
        HttpURLConnection httpConnection = null;
        BufferedReader reader = null;
        String responseTextJSON = "";
        try {
            URL url = new URL(urlRequest);
            httpConnection = (HttpURLConnection) url.openConnection();
            int responseCode = httpConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                reader = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
                responseTextJSON = readAllLine(reader);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (httpConnection != null) {
                httpConnection.disconnect();
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

    private static String readAllLine(BufferedReader reader) throws IOException {
        String line;
        StringBuilder stringBuilder = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }
        return stringBuilder.toString();
    }
}