package com.seed.careerhub;

import com.seed.careerhub.domain.Badge;
import com.seed.careerhub.domain.Event;
import com.seed.careerhub.domain.Skill;
import com.seed.careerhub.domain.User;
import com.seed.careerhub.jpa.BadgeRepository;
import com.seed.careerhub.jpa.EventRepository;
import com.seed.careerhub.jpa.SkillRepository;
import com.seed.careerhub.jpa.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.util.*;

@Slf4j
@Configuration
@Profile({"default", "dev"})
class LoadDatabase {

    @Bean(name = "mariadbDatasource")
    @Primary
    @ConfigurationProperties(prefix="spring.datasource")
    public DataSource primaryDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "postgresDatasource")
    @ConfigurationProperties(prefix="spring.second-datasource")
    public DataSource secondaryDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "postgressJdbcTemplate")
    public NamedParameterJdbcTemplate postgresJdbcTemplate(
            @Qualifier("postgresDatasource") DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    List<String> baseSkills = Arrays.asList("Community Manager",
            "Business Development",
            "Copywriter",
            "Event Manager",
            "Analyst",
            "Design",
            "Branding",
            "Product Manager",
            "Project Manager",
            "Full Stack",
            "UI (Figma)",
            "UX",
            "ReactJS",
            "VueJS",
            "Java",
            "Python",
            "Backend Database",
            "Smart Contract (Rust)",
            "Smart Contract (Solidity)",
            "Junior",
            "Senior",
            "NFT",
            "Defi",
            "GameFi",
            "Metaverse",
            "Marketplace");

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository,
                                   EventRepository eventRepository,
                                   SkillRepository skillRepository,
                                   BadgeRepository badgeRepository) {
        return args -> {

            // Create users
            User user = new User();
            user.setId(1L);
            user.setName("NEAR Hacker");
            user.setNearAddress("sotcsa2.testnet");
            user.setHandle("hacker");
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

            for (String tag : baseSkills) {
                log.info("Created skill: " + skillRepository.save(new Skill(tag)));
            }

            User user2 = new User();
            user2.setId(2L);
            user2.setName("Poppy Middleton");
            user.setNearAddress("poppymiddleton.testnet");
            log.info("Created new User: {}", userRepository.save(user2));

            User user3 = new User();
            user2.setName("Melanie Kluge");
            user.setNearAddress("MelanieKluge.testnet");
            log.info("Created new User: {}", userRepository.save(user3));


            // Create an event
            Calendar date = Calendar.getInstance(TimeZone.getTimeZone("America/New_York"));
            date.set(2022, 8, 23, 10, 0, 0);
            long startDate = date.getTimeInMillis();
            date.set(2022, 10, 21, 17, 0, 0);
            long endDate = date.getTimeInMillis();

            Event event = new Event(1L,
                    "NEAR MetaBUILD III Hackathon",
                    "This event loaded from startup script",
                    "https://metabuild.devpost.com/",
                    startDate,
                    endDate);
            log.info("Created new Event: " + eventRepository.save(event));



            // Add 3 users to event 1
            log.info("Created new Badge: {}", badgeRepository.save(
                    new Badge( "Participated in NearCon 2022",
                            event.getId(),
                            user.getId())));
            log.info("Created new Badge: {}", badgeRepository.save(
                    new Badge( "METABUILD III",
                            event.getId(),
                            user2.getId())));
            log.info("Created new Badge: {}", badgeRepository.save(
                    new Badge( "NEAR Academy",
                            event.getId(),
                            user3.getId())));
        };
    }

}