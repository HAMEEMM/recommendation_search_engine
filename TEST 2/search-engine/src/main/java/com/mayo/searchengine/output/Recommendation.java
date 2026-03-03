package com.mayo.searchengine.output;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class Recommendation {
    @JsonProperty("queryId")
    public String queryId;

    @JsonProperty("recommendedContentIds")
    public List<String> recommendedContentIds;

    public Recommendation(String queryId, List<String> recommendedContentIds) {
        this.queryId = queryId;
        this.recommendedContentIds = recommendedContentIds;
    }
}
