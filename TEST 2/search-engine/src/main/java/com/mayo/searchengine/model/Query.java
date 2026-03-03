package com.mayo.searchengine.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Query {
    public String queryId;
    public String userId;
    public String queryText;
}
