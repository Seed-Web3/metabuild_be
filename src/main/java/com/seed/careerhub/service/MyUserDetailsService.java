package com.seed.careerhub.service;

import com.seed.careerhub.jpa.UserRepository;
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
    private final UserRepository userRepository;

    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

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

    /**
     * TODO need to refactor, dummy implementation
     *
     * @param email
     * @return UserDetails
     * @throws UsernameNotFoundException
     */
    public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {
        List<? extends GrantedAuthority> authorities = new ArrayList<>();
        // to verify user already exists
        userRepository.findOneByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found by email " + email));
        return new User(email, encoder.encode(""), authorities);
    }

}
