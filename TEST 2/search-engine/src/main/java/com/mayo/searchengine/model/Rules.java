package com.mayo.searchengine.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Rules {
    public Weights weights;
    public int topK;
    public int recommendationCount;
}
