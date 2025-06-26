package com.pritam.journalApp.service;

import com.pritam.journalApp.api.response.WeatherResponse;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    private static String API_KEY(){
        Dotenv dotenv = Dotenv.configure()
                .ignoreIfMissing()    // ← skip if no .env
                .load();
        return dotenv.get("WEATHER_API_KEY");
    }

    private static final String API_KEY = API_KEY();

    private static final String API_URL = "http://api.weatherapi.com/v1/current.json?key=YOUR_API_KEY&q=CITY_NAME";

    @Autowired
    private RestTemplate restTemplate;

    public WeatherResponse getWeather(String city) {
        String finalApi=API_URL.replace("YOUR_API_KEY",API_KEY)
                                .replace("CITY_NAME",city);

        ResponseEntity<WeatherResponse> response =restTemplate.exchange(finalApi, HttpMethod.GET,null, WeatherResponse.class);
        return response.getBody();


    }
}
