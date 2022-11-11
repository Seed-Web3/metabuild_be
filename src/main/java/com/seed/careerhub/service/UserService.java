package com.seed.careerhub.service;

import com.seed.careerhub.domain.Badge;
import com.seed.careerhub.domain.BagdeCategory;
import com.seed.careerhub.domain.User;
import com.seed.careerhub.exception.DataNotFound;
import com.seed.careerhub.jpa.BadgeRepository;
import com.seed.careerhub.jpa.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class UserService {
    private final UserRepository userRepository;
    private final BadgeRepository badgeRepository;

    @Qualifier("postgressJdbcTemplate")
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public UserService(UserRepository userRepository, BadgeRepository badgeRepository, NamedParameterJdbcTemplate jdbcTemplate) {
        this.userRepository = userRepository;
        this.badgeRepository = badgeRepository;
        this.jdbcTemplate = jdbcTemplate;
    }


    public User createUserWithEmail(String email) {
        User user = new User();
        user.setEmail(email);
        return userRepository.save(user);
    }

    public List<Badge> getNearNftsByUsername(String username) {
        User user = userRepository.findOneByEmail(username).orElseThrow(DataNotFound::new);
        String nearAccount = userRepository.findOneByEmail(username).orElseThrow(DataNotFound::new).getNearAddress();

        final String query = "SELECT emitted_by_contract_account_id, token_id " +
                "FROM assets__non_fungible_token_events " +
                "WHERE token_new_owner_account_id = :nearAccount " +
                "AND event_kind = 'MINT'";
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("nearAccount", nearAccount);
        var badges = jdbcTemplate.query(query, parameters, (rs, rownumber) -> {
            String nftAccountId = rs.getString(1);
            String nftTokenId = rs.getString(2);
            Badge badge = new Badge();
            badge.setUserId(user.getId());
            badge.setNftAccount(nftAccountId);
            badge.setNftTokenId(nftTokenId);
            badge.setCategory(BagdeCategory.UNCATEGORIZED);
            return badge;
        });
        for (Badge badge : badges) {
            if (badgeRepository.findByNftAccountAndNftTokenId(badge.getNftAccount(), badge.getNftTokenId()).isEmpty()) {
                log.info("Saving NFT https://wallet.testnet.near.org/nft-detail/{}/{}", badge.getNftAccount(), badge.getNftTokenId());
                badgeRepository.saveAndFlush(badge);
            }
        }
        return badgeRepository.findByUserId(user.getId());
    }
}
