package com.seed.careerhub.model;

import lombok.Data;

@Data
public class UserRequest {
    private String name;
    private String handle;
    private String bio;
    private String location;
    private String twitter;
    private String github;
    private String linkedin;
    private String website;
    private String mainSkill;
    private String skills;
    private boolean openToJobOpportunity;
    private boolean openToRemoteJob;
    private boolean receiveNewJobEmail;
    private boolean showPublicAddress;
    private boolean showLocation;
}
