package com.sergiohscl.basic_concept_springboot.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sergiohscl.basic_concept_springboot.Service.ProductService;
import com.sergiohscl.basic_concept_springboot.data.dto.v1.ProductDTO;
import com.sergiohscl.basic_concept_springboot.model.Product;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productservice;

    public ProductController(ProductService productService) {
        this.productservice = productService;
    }

    @GetMapping
    public List<Product> listProducts() {
        return productservice.listProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> searchProduct(@PathVariable Long id) {
        ProductDTO product = productservice.searchById(id);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productservice.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("")
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO newProduct) {
        ProductDTO saved = productservice.saveProduct(newProduct);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productUpdated)  {
        ProductDTO updated  = productservice.updateProduct(id, productUpdated);      
        return ResponseEntity.ok(updated);
    }
    
}
