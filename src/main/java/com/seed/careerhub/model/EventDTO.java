package com.seed.careerhub.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EventDTO {
    private Long id;
    private String name;
    private String uuid;
    private String description;
    private String url;
    private Long startDate;
    private Long expiryDate;
    private String eventLink;
}
