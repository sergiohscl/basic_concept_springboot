package com.sergiohscl.basic_concept_springboot.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.stereotype.Repository;

import com.sergiohscl.basic_concept_springboot.model.User;


public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);    
}
