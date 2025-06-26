package com.pritam.journalApp.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class QuoteResponse{
    public String text;
    public String author;
    public ArrayList<String> tags;
    public int id;
    @JsonProperty(value = "authorId")
    public String authorId;
}

