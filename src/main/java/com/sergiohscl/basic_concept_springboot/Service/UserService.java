package com.sergiohscl.basic_concept_springboot.Service;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sergiohscl.basic_concept_springboot.Repository.UserRepository;
import com.sergiohscl.basic_concept_springboot.model.User;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public User registerUser(String username, String password) {
        String passwordEncrypted = passwordEncoder.encode(password);
        User user = new User(username, passwordEncrypted);
        return userRepository.save(user);
    }

    public Optional<User> searchByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
