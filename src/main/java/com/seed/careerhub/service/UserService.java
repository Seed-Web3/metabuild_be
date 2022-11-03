package com.seed.careerhub.service;

import com.seed.careerhub.domain.User;
import com.seed.careerhub.jpa.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUserWithEmail(String email) {
        User user = new User();
        user.setEmail(email);
        return userRepository.save(user);
    }
}
