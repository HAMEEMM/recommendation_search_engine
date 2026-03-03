package com.mayo.searchengine.output;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class Result {
    @JsonProperty("queryId")
    public String queryId;

    @JsonProperty("rankedContentIds")
    public List<String> rankedContentIds;

    public Result(String queryId, List<String> rankedContentIds) {
        this.queryId = queryId;
        this.rankedContentIds = rankedContentIds;
    }
}
