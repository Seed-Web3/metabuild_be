package com.seed.careerhub.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

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
    @GeneratedValue(generator = "event-sequence-generator")
    @GenericGenerator(
            name = "event-sequence-generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "magic_link_sequence")
            }
    )
    private Long id;
    private final String uuid;
    private Date expiryDate;

    public MagicLink(int expiresInMinute) {
        this.uuid = UUID.randomUUID().toString();
        this.expiryDate = DateUtils.addMinutes(new Date(), expiresInMinute);
    }

    /**
     * No-arg constructor (used by Spring)
     */
    private MagicLink() {
        this.uuid = UUID.randomUUID().toString();
    }

}
