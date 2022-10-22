package com.seed.careerhub.model;

import lombok.Data;

@Data
public class EventRequest {
    String name;
    String description;
    Long startDate;
    Long expiryDate;
}
