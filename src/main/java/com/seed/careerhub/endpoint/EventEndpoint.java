package com.seed.careerhub.endpoint;

import com.seed.careerhub.domain.Badge;
import com.seed.careerhub.domain.Event;
import com.seed.careerhub.domain.User;
import com.seed.careerhub.model.EventDTO;
import com.seed.careerhub.model.EventRequest;
import com.seed.careerhub.service.EventService;
import com.seed.careerhub.util.EndpointUtil;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/event")
public class EventEndpoint {

    private final EventService eventService;

    /**
     * Constructor.
     *
     * @param eventService eventService
     */
    public EventEndpoint(EventService eventService) {
        this.eventService = eventService;
    }

    /**
     * @param eventRequest eventRequest
     * @return Event
     */
    @Operation(summary = "Saves event details")
    @PostMapping()
    public EventDTO save(@RequestBody EventRequest eventRequest) {
        log.debug("Save a new event with name {}", eventRequest.getName());
        Event event = new Event(eventRequest.getName(),
                eventRequest.getDescription(),
                eventRequest.getStartDate(),
                eventRequest.getExpiryDate());
        return eventService.save(event);
    }

    /**
     * Gets users who claimed badge for an event.
     *
     * @param eventId eventId
     * @return list of users
     */
    @Operation(summary = "Gets users who claimed badge for an event")
    @GetMapping("{eventId}/users")
    public List<User> getUsersForEvent(@PathVariable Long eventId) {
        log.debug("Get users who has claimed the Badge from an event[{}]", eventId);
        return eventService.findUsersByEventId(eventId);
    }

    /**
     * Creates a {@link com.seed.careerhub.domain.Badge)} with user who is
     * logged-in, and with eventId from teh parameter.
     *
     * @param eventId eventId
     * @return Badge
     */
    @Operation(summary = "Claims badge for an event (by logged-in user)")
    @PostMapping("{eventId}/user")
    public Badge saveUserClaimForEvent(@PathVariable Long eventId) {
        log.debug("Create user badge for an event[{}]", eventId);
        String address = EndpointUtil.getLoggedInAddress();
        return eventService.claim(eventId, address);
    }

    /**
     * It's helper method for admins
     * Creates a {@link com.seed.careerhub.domain.Badge)} with user and event from the parameters
     * UUID of event used in parameter, because the sent event link contains only this.
     *
     * @param eventUUID uuid of event
     * @param address wallet address of user
     * @return Badge
     */
    @Operation(summary = "Helper call for admins to claim badge with wallet address for an event")
    @PostMapping("{eventUUID}/user/{address}")
    public Badge saveUserClaimForEvent(@PathVariable String eventUUID, @PathVariable String address) {
        log.debug("Create user badge with address '{}' for an event '{}'", address, eventUUID);
        return eventService.claim(eventUUID, address);
    }

    /**
     * Gets all events.
     *
     * @return list of events
     */
    @Operation(summary = "Gets events")
    @GetMapping("all")
    public List<EventDTO> getAllEvents() {
        return eventService.findAll();
    }

    /**
     * Retrieve an event
     *
     * @return an events
     */
    @Operation(summary = "Gets event details")
    @GetMapping("{eventId}")
    public EventDTO getEvent(@PathVariable Long eventId) {
        return eventService.findById(eventId);
    }

}
