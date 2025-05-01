package com.sergiohscl.basic_concept_springboot.Service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sergiohscl.basic_concept_springboot.Repository.UserRepository;
import com.sergiohscl.basic_concept_springboot.model.User;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private Logger logger = LoggerFactory.getLogger(UserService.class.getName());

    public UserService(UserRepository userRepository) {        
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public User registerUser(String username, String password) {
        logger.info("Registering Users");

        String passwordEncrypted = passwordEncoder.encode(password);
        User user = new User(null, username, passwordEncrypted);
        return userRepository.save(user);
    }

    public Optional<User> searchByUsername(String username) {
        logger.info("Finding one User!");
        
        return userRepository.findByUsername(username);
    }
}
