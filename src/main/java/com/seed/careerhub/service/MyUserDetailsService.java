package com.seed.careerhub.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    /**
     * TODO need to refactor, dummy implementation
     *
     * @param username
     * @return UserDetails
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return loadUserByEthAddress(username);
    }

    /**
     * TODO need to refactor, dummy implementation
     *
     * @param publicAddress
     * @return UserDetails
     * @throws UsernameNotFoundException
     */
    public UserDetails loadUserByEthAddress(String publicAddress) throws UsernameNotFoundException {
        List<? extends GrantedAuthority> authorities = new ArrayList<>();
        return new User(publicAddress, encoder.encode(""), authorities);
    }
}
