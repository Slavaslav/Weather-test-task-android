package com.getweatherdatatesttask.Weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class WeatherJSONParser {

    private static final String COORD = "coord";
    private static final String LAT = "lat";
    private static final String LON = "lon";
    private static final String WEATHER = "weather";
    private static final String MAIN = "main";
    private static final String TEMP = "temp";
    private static final String PRESSURE = "pressure";
    private static final String HUMIDITY = "humidity";
    private static final String WIND = "wind";
    private static final String SPEED = "speed";
    private static final String DEG = "deg";

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