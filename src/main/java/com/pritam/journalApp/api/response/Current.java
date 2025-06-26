package com.pritam.journalApp.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Current{
    @JsonProperty(value = "last_updated_epoch")
    private int lastYUpdatedEpoch;
    @JsonProperty(value = "last_updated")
    private String lastUpdated;
    @JsonProperty(value = "temp_c")
    private double tempC;
    private int humidity;
    private int cloud;
    @JsonProperty(value = "feelslike_c")
    private double feelslike;
}
