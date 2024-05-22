package org.example;


import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;

public class WeatherApiClient {
    private static final String API_KEY = "b0a2a0d6d05a640a93ae2499cef16636";
    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/weather";
    private final OkHttpClient httpClient;
    private final Gson gson;

    public WeatherApiClient() {
        this.httpClient = new OkHttpClient();
        this.gson = new Gson();
    }

    public WeatherData getWeather(String city) throws IOException {
        String url = String.format("%s?q=%s&appid=%s&units=metric", BASE_URL, city, API_KEY);
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            String responseData = response.body().string();
            JsonObject jsonObject = gson.fromJson(responseData, JsonObject.class);
            return parseWeatherData(jsonObject);
        }
    }

    private WeatherData parseWeatherData(JsonObject jsonObject) {
        JsonObject main = jsonObject.getAsJsonObject("main");
        JsonObject wind = jsonObject.getAsJsonObject("wind");

        double temperature = main.get("temp").getAsDouble();
        int humidity = main.get("humidity").getAsInt();
        double windSpeed = wind.get("speed").getAsDouble();

        return new WeatherData(temperature, humidity, windSpeed);
    }

    public static void main(String[] args) {
        WeatherApiClient client = new WeatherApiClient();
        try {
            WeatherData weather = client.getWeather("London");
            System.out.println("Temperature: " + weather.getTemperature() + "°C");
            System.out.println("Humidity: " + weather.getHumidity() + "%");
            System.out.println("Wind Speed: " + weather.getWindSpeed() + " m/s");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}