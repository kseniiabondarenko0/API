package org.example;
//package com.example.weatherapi;

public class WeatherData {
    private final double temperature;
    private final int humidity;
    private final double windSpeed;

    public WeatherData(double temperature, int humidity, double windSpeed) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
    }

    public double getTemperature() {
        return temperature;
    }

    public int getHumidity() {
        return humidity;
    }

    public double getWindSpeed() {
        return windSpeed;
    }
}
