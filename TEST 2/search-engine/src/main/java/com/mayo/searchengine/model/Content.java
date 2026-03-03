package com.mayo.searchengine.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Content {
    public String contentId;
    public String title;
    public List<String> tags;
    public String specialty;
    public String channel;
    public double popularityScore;
    public double freshnessDays;
}
