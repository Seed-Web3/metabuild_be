package com.seed.careerhub.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
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
public class Badge {
    @Id
    @GeneratedValue(generator = "badge-sequence-generator")
    @GenericGenerator(
            name = "badge-sequence-generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                @Parameter(name = "sequence_name", value = "badge_sequence")
            }
    )
    private Long id;

    private String name;
    private Long eventId;
    private Long userId;
    private String nftAccount;
    private String nftTokenId;
    private BagdeCategory category;

    public Badge(String name, Long eventId, Long userId) {
        this.name = name;
        this.eventId = eventId;
        this.userId = userId;
    }
}
