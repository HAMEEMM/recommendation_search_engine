package com.mayo.searchengine.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Weights {
    public double keywordMatch;
    public double tagMatch;
    public double specialtyBoost;
    public double channelBoost;
    public double popularityWeight;
    public double freshnessWeight;
}
