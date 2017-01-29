package com.getweatherdatatesttask.Weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WeatherJSONParser {
    public static Weather parseWeatherFromJson(String weatherTextJSON) {
        Weather weather = null;
        try {
            JSONObject jsonObject = new JSONObject(weatherTextJSON);

            JSONObject objectCoordinates = jsonObject.getJSONObject("coord");
            float latitude = (float) objectCoordinates.getDouble("lat");
            float longitude = (float) objectCoordinates.getDouble("lon");

            JSONArray arrayWeather = jsonObject.getJSONArray("weather");
            JSONObject objectWeather = arrayWeather.getJSONObject(0);
            String weatherDescription = objectWeather.getString("main");

            JSONObject objectMain = jsonObject.getJSONObject("main");
            float temperature = (float) objectMain.getDouble("temp");
            float pressure = (float) objectMain.getDouble("pressure");
            int humidity = objectMain.getInt("humidity");

            JSONObject objectWind = jsonObject.getJSONObject("wind");
            float windSpeed = (float) objectWind.getDouble("speed");
            float windDegrees = (float) objectWind.getDouble("deg");

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