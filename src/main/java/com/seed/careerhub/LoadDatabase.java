package com.seed.careerhub;

import com.seed.careerhub.domain.Badge;
import com.seed.careerhub.domain.Event;
import com.seed.careerhub.domain.User;
import com.seed.careerhub.jpa.BadgeRepository;
import com.seed.careerhub.jpa.EventRepository;
import com.seed.careerhub.jpa.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile({"default","dev"})
class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository,
            EventRepository eventRepository,
            BadgeRepository badgeRepository) {
        return args -> {

            // Create 3 users
            User user = new User();
            user.setId(1L);
            user.setName("NEAR Hacker");
            user.setHandle("anonymous");
            user.setBio("Web3 Hacker");
            user.setEmail("h@cker.com");
            user.setTwitter("https://twitter.com/sotcsa");
            user.setGithub("https://github.com/sotcsa");
            user.setLinkedin("https://www.linkedin.com/in/csabasoti/");
            user.setWebsite("");
            user.setMainSkill("DevOps");
            user.setOpenToJobOpportunity(true);
            user.setOpenToRemoteJob(true);
            user.setReceiveNewJobEmail(true);
            user.setShowLocation(true);
            user.setShowPublicAddress(true);
            log.info("Created new User: " + userRepository.save(user));
//
//            User user2 = new User("Poppy", "Middleton", "0xa485A768CB6DE1DE1e0Fc5AB2b93703a11615c1A");
//            log.info("Created new User: {}", userRepository.save(user2));
//
//            User user3 = new User("Melanie", "Kluge", "0xAB7ceDa29D292Dd83EC72342F86C3CEd573932cc");
//            log.info("Created new User: {}", userRepository.save(user3));
//
//
//
//            // Create an event
//            Event event = new Event(1L, "NearCon Event", "This event loaded from startup script", null, null);
//            log.info("Created new Event: " + eventRepository.save(event));
//
//
//
//            // Add 3 users to event 1
//            log.info("Created new Badge: {}", badgeRepository.save(
//                    new Badge( "Participated in NearCon", event.getId(), user.getId())));
//            log.info("Created new Badge: {}", badgeRepository.save(
//                    new Badge( "NEARCON", event.getId(), user2.getId())));
//            log.info("Created new Badge: {}", badgeRepository.save(
//                    new Badge( "", event.getId(), user3.getId())));
        };
    }

}