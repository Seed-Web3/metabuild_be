package com.seed.careerhub.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MagicLink {
    @Id
    @GeneratedValue(generator = "magiclink-sequence-generator")
    @GenericGenerator(
            name = "magiclink-sequence-generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "magiclink_sequence")
            }
    )
    private Long id;

    @Column(unique=true)
    private final String code;

    @Column(unique=true)
    private String email;

    private Date expiryDate;

    public MagicLink(String email, int expiresInMinute) {
        this.code = UUID.randomUUID().toString();
        this.email = email;
        this.expiryDate = DateUtils.addMinutes(new Date(), expiresInMinute);
    }

    /**
     * No-arg constructor (used by Spring)
     */
    private MagicLink() {
        this.code = UUID.randomUUID().toString();
    }

}
