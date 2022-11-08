package com.seed.careerhub.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Event {
    @Id
    @GeneratedValue(generator = "event-sequence-generator")
    @GenericGenerator(
            name = "event-sequence-generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                @Parameter(name = "sequence_name", value = "event_sequence")
            }
    )
    private Long id;

    private String name;
    private final String uuid;
    private String description;
    private String url;
    private Long startDate;
    private Long expiryDate;

    /**
     * No-arg constructor (used by Spring)
     */
    private Event() {
        this.uuid = generateUUID();
    }

    /**
     * Useful constructor when id is not yet known.
     *
     * @param name
     */
    public Event(String name, String description, Long startDate, Long expiryDate) {
        this.uuid = generateUUID();
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.expiryDate = expiryDate;
    }

    /**
     * Useful constructor when id is not yet known.
     *
     * @param name
     */
    public Event(Long id, String name, String description, String url, Long startDate, Long expiryDate) {
        this.uuid = generateUUID();
        this.id = id;
        this.name = name;
        this.description = description;
        this.url = url;
        this.startDate = startDate;
        this.expiryDate = expiryDate;
    }

    private String generateUUID() {
        return UUID.randomUUID().toString();
    }

}
