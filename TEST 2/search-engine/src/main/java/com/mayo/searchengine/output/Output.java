package com.mayo.searchengine.output;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class Output {
    @JsonProperty("results")
    public List<Result> results;

    @JsonProperty("recommendations")
    public List<Recommendation> recommendations;

    @JsonProperty("metrics")
    public Metrics metrics;

    public Output(List<Result> results, List<Recommendation> recommendations, Metrics metrics) {
        this.results = results;
        this.recommendations = recommendations;
        this.metrics = metrics;
    }
}
