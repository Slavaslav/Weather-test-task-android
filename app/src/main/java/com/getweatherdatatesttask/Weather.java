package com.getweatherdatatesttask;

public class Weather {
    private float latitude;
    private float longitude;
    private String weatherDescription;
    private float temperature;
    private float pressure;
    private int humidity;
    private float windSpeed;
    private float windDegrees;

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public void setWeatherDescription(String weatherDescription) {
        this.weatherDescription = weatherDescription;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public void setPressure(float pressure) {
        this.pressure = pressure;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public void setWindSpeed(float windSpeed) {
        this.windSpeed = windSpeed;
    }

    public void setWindDegrees(float windDegrees) {
        this.windDegrees = windDegrees;
    }
}