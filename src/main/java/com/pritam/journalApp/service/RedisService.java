package com.pritam.journalApp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pritam.journalApp.api.response.WeatherResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedisService {

    @Autowired
    private RedisTemplate redisTemplate;

    public <T> T get(String key, Class<T> entityClass){
       try{
           Object o = redisTemplate.opsForValue().get(key);
           ObjectMapper mapper = new ObjectMapper();
           return mapper.readValue(o.toString(),entityClass);
       }catch(Exception e){
            log.error("Error while getting data from redis {} : {} " ,key , e.getMessage());
            return null;
       }
    }

    public void  set(String key, Object o , long timeToLive){
        try{
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(o);
            redisTemplate.opsForValue().set(key, json, timeToLive, TimeUnit.MINUTES);

        }catch(Exception e){
            log.error("Error while getting data from redis {} : {} " ,key , e.getMessage());
        }
    }


}
