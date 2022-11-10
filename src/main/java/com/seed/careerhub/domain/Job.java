package com.seed.careerhub.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Job {
    @Id
    @GeneratedValue(generator = "job-sequence-generator")
    @GenericGenerator(
            name = "job-sequence-generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "job_sequence")
            }
    )
    private Long id;

    private String title;
    private String description;
    private String company;
    private String skills;
//    private Map salary;
    private Integer salaryMin;
    private Integer salaryMax;
    private String salaryCurrency;
    private String location;
    private String email;
    private String socials;
    private String logo;
//    private Map bounty;
    private Integer bountyAmount;
    private String bountyCurrency;

}
