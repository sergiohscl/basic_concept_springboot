package com.sergiohscl.basic_concept_springboot.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sergiohscl.basic_concept_springboot.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {}
