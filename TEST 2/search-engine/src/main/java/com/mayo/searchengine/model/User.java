package com.mayo.searchengine.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    public String userId;
    public List<String> preferredSpecialties;
    public List<String> recentContentIds;
    public String channelPreference;
}
