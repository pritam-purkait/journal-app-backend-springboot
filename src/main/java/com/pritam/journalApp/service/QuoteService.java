package com.pritam.journalApp.service;

import com.pritam.journalApp.api.response.QuoteResponse;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
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

    @Autowired
    private RedisService redisService;

    public QuoteResponse getQuote() {
        QuoteResponse quoteResponse = redisService.get("today's_quote", QuoteResponse.class);
        if(quoteResponse != null){
            return quoteResponse;
        }else{
            String finalApi=API_URL;
            ResponseEntity<QuoteResponse> response =restTemplate.exchange(finalApi, HttpMethod.GET,null, QuoteResponse.class );
            QuoteResponse body = response.getBody();
            if(body != null){
                redisService.set("today's_quote", body, 1440);
            }
            return body;
        }
    }

}
