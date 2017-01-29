package com.getweatherdatatesttask.Weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WeatherJSONParser {

    public static final String COORD = "coord";
    public static final String LAT = "lat";
    public static final String LON = "lon";
    public static final String WEATHER = "weather";
    public static final String MAIN = "main";
    public static final String TEMP = "temp";
    public static final String PRESSURE = "pressure";
    public static final String HUMIDITY = "humidity";
    public static final String WIND = "wind";
    public static final String SPEED = "speed";
    public static final String DEG = "deg";

    public static Weather parseWeatherFromJson(String weatherTextJSON) {
        Weather weather = null;
        try {
            JSONObject jsonObject = new JSONObject(weatherTextJSON);

            JSONObject objectCoordinates = jsonObject.getJSONObject(COORD);
            float latitude = (float) objectCoordinates.getDouble(LAT);
            float longitude = (float) objectCoordinates.getDouble(LON);

            JSONArray arrayWeather = jsonObject.getJSONArray(WEATHER);
            JSONObject objectWeather = arrayWeather.getJSONObject(0);
            String weatherDescription = objectWeather.getString(MAIN);

            JSONObject objectMain = jsonObject.getJSONObject(MAIN);
            float temperature = (float) objectMain.getDouble(TEMP);
            float pressure = (float) objectMain.getDouble(PRESSURE);
            int humidity = objectMain.getInt(HUMIDITY);

            JSONObject objectWind = jsonObject.getJSONObject(WIND);
            float windSpeed = (float) objectWind.getDouble(SPEED);
            float windDegrees = (float) objectWind.getDouble(DEG);

            weather = new Weather();
            weather.setLatitude(latitude);
            weather.setLongitude(longitude);
            weather.setWeatherDescription(weatherDescription);
            weather.setTemperature(temperature);
            weather.setPressure(pressure);
            weather.setHumidity(humidity);
            weather.setWindSpeed(windSpeed);
            weather.setWindDegrees(windDegrees);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return weather;
    }
}