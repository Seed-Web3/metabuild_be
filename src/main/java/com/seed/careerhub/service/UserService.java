package com.seed.careerhub.service;

import com.seed.careerhub.domain.NearNft;
import com.seed.careerhub.domain.User;
import com.seed.careerhub.exception.DataNotFound;
import com.seed.careerhub.jpa.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class UserService {
    private final UserRepository userRepository;

    @Qualifier("postgressJdbcTemplate")
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public UserService(UserRepository userRepository, NamedParameterJdbcTemplate jdbcTemplate) {
        this.userRepository = userRepository;
        this.jdbcTemplate = jdbcTemplate;
    }


    public User createUserWithEmail(String email) {
        User user = new User();
        user.setEmail(email);
        return userRepository.save(user);
    }

    public String getNearNftsByUsername(String username) {
        String nearAccount = userRepository.findOneByEmail(username).orElseThrow(DataNotFound::new).getNearAddress();

        final String query = "SELECT emitted_by_contract_account_id, token_id " +
                "FROM assets__non_fungible_token_events " +
                "WHERE token_new_owner_account_id = :nearAccount " +
                "AND event_kind = 'MINT'";
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("nearAccount", nearAccount);
        var nft =  jdbcTemplate.query(query, parameters,
                (rs, rowNum) -> new NearNft(rs.getString("emitted_by_contract_account_id"),
                        rs.getString("token_id")));

        return "";
    }
}
