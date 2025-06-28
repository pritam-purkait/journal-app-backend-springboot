package com.pritam.journalApp.Cache;

import com.pritam.journalApp.entity.ConfigJournalAppEntity;
import com.pritam.journalApp.repository.ConfigJournalAppRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache {

    @Autowired
    private ConfigJournalAppRepository configJournalAppRepository;

    public Map<String, String> APP_CACHE ;

    @PostConstruct
    public void init(){
        APP_CACHE = new HashMap<>();
        List<ConfigJournalAppEntity> all =configJournalAppRepository.findAll();
        all.forEach(x->APP_CACHE.put(x.getKey(),x.getValue()));
    }
}
