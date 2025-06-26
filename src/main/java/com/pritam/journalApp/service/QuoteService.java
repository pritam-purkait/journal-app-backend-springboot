package com.pritam.journalApp.service;

import com.pritam.journalApp.api.response.QuoteResponse;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class QuoteService {

    private static String API_URL(){
        Dotenv dotenv = Dotenv.configure()
                .ignoreIfMissing()    // ← skip if no .env
                .load();
        return dotenv.get("QUOTE_URL");
    }

    private static final String API_URL = API_URL();

    @Autowired
    private RestTemplate restTemplate;

    public QuoteResponse getQuote() {

        String finalApi=API_URL;
        ResponseEntity<QuoteResponse> response =restTemplate.exchange(finalApi, HttpMethod.GET,null, QuoteResponse.class );
        return response.getBody();

    }

}
