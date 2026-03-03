package com.mayo.searchengine.output;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Metrics {
    @JsonProperty("queriesProcessed")
    public int queriesProcessed;

    @JsonProperty("topK")
    public int topK;

    @JsonProperty("recommendationCount")
    public int recommendationCount;

    public Metrics(int queriesProcessed, int topK, int recommendationCount) {
        this.queriesProcessed = queriesProcessed;
        this.topK = topK;
        this.recommendationCount = recommendationCount;
    }
}
