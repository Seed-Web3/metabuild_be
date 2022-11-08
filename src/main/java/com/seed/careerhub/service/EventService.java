package com.seed.careerhub.service;

import com.seed.careerhub.domain.Badge;
import com.seed.careerhub.domain.Event;
import com.seed.careerhub.domain.User;
import com.seed.careerhub.exception.DataNotFound;
import com.seed.careerhub.jpa.BadgeRepository;
import com.seed.careerhub.jpa.EventRepository;
import com.seed.careerhub.jpa.UserRepository;
import com.seed.careerhub.model.EventDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService {

    @Value("${frontendUri}")
    private String FRONTEND_URI;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final BadgeRepository badgeRepository;

    public EventService(EventRepository eventRepository, UserRepository userRepository, BadgeRepository badgeRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.badgeRepository = badgeRepository;
    }

    public EventDTO findById(Long id) {
        Event event = eventRepository.findById(id).orElseThrow(DataNotFound::new);
        return getEventDTO(event);
    }

    public List<EventDTO> findAll() {
        return eventRepository.findAll().stream()
                .map(this::getEventDTO)
                .collect(Collectors.toList());
    }

    public EventDTO save(Event event) {
        Event saved = eventRepository.save(event);
        return findById(saved.getId());
    }

    public List<User> findUsersByEventId(Long eventId) {
        return eventRepository.findUsersByEventId(eventId);
    }

    public Badge claim(String eventUUID, String address) {
        Event event = eventRepository.findOneByUuid(eventUUID).orElseThrow(DataNotFound::new);
        return claim(event.getId(), address);
    }

    public Badge claim(Long eventId, String address) {
        User user = userRepository.findByNearAddress(address);
        Badge badge = new Badge("", eventId, user.getId());
        return badgeRepository.save(badge);
    }

    private EventDTO getEventDTO(Event event) {
        String eventLink = FRONTEND_URI + "/#/event/" + event.getUuid() + "#/";
        return new EventDTO(event.getId(),
                event.getName(),
                event.getUuid(),
                event.getDescription(),
                event.getUrl(),
                event.getStartDate(),
                event.getExpiryDate(),
                eventLink);
    }
}
