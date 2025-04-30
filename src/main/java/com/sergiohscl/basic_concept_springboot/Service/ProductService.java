package com.sergiohscl.basic_concept_springboot.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sergiohscl.basic_concept_springboot.Repository.ProductRepository;
import com.sergiohscl.basic_concept_springboot.exceptions.ResourceBadRequestException;
import com.sergiohscl.basic_concept_springboot.exceptions.ResourceNotFoundException;
import com.sergiohscl.basic_concept_springboot.model.Product;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> listProducts() {
        return productRepository.findAll();
    }

    public Product searchById(Long id) {
        return productRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Product with ID "+id+" not found!"));
    }

    public Product saveProduct(Product product) {
        if (product.getName() == null || product.getName().trim().isEmpty()) {
            throw new ResourceBadRequestException("The product name cannot be empty.");
        }
        if (product.getPrice() == null || product.getPrice() < 0) {
            throw new ResourceBadRequestException("The price of the product must be greater than or equal to zero.");
        }
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product product) {
        return productRepository.findById(id)
            .map(existingProduct -> {
                existingProduct.setName(product.getName());
                existingProduct.setPrice(product.getPrice());

                return productRepository.save(existingProduct);
            })
            .orElseThrow(() -> new ResourceNotFoundException("Product with ID "+id+" not found!"));
    }

    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product with ID "+id+" not found!");
        }
        productRepository.deleteById(id);
    }
}
