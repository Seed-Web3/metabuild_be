package com.seed.careerhub.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;

@Entity
@Table(name = "USERS")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    @Id
    @GeneratedValue(generator = "user-sequence-generator")
    @GenericGenerator(
            name = "user-sequence-generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                @Parameter(name = "sequence_name", value = "user_sequence")
            }
    )
    private Long id;

    private String name;

    @Column(unique=true)
    private String handle;
    private String bio;

    @Column(unique=true)
    private String nearAddress;

    @Column(unique=true)
    private String email;

    private String twitter;
    private String github;
    private String linkedin;
    private String website;

    private String mainSkill;
//    private String skills;

    private boolean openToJobOpportunity;
    private boolean openToRemoteJob;
    private boolean receiveNewJobEmail;

    private boolean showPublicAddress;
    private boolean showLocation;
}
