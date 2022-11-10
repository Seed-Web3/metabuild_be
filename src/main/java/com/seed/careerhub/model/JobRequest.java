package com.seed.careerhub.model;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class JobRequest {
    String title;
    String description;
    String company;
    List<String> skills;
    Map<String, Object> salary;
    String location;
    String email;
    List<Object> socials;
    String logo;
    Map<String, Object> bounty;
}
