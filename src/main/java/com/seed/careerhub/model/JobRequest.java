package com.seed.careerhub.model;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class JobRequest {
    String title;
    String description;
    String company;
    List skills;
    Map salary;
//    String min;
//    String max;
//    String currency;
    String location;
    String email;
    List socials;
    String logo;
    Map bounty;
//    String amount;
//    String currency;
}
