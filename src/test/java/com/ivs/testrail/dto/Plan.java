package com.ivs.testrail.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


public class Plan {

    public int id;
    public String name;
    @JsonProperty("milestone_id")
    public Integer milestoneId;
    public List<PlanEntry> entries;

}
