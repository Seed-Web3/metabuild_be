package com.seed.careerhub.jpa;

import com.seed.careerhub.domain.Event;
import com.seed.careerhub.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {

    @Query("select u from Badge b left join User u on b.userId = u.id where b.eventId = ?1 ")
    List<User> findUsersByEventId(Long eventId);

    Optional<Event> findOneByUuid(String uuid);
}
