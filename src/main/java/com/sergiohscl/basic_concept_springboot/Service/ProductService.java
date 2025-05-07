package com.sergiohscl.basic_concept_springboot.Service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sergiohscl.basic_concept_springboot.Repository.ProductRepository;
import com.sergiohscl.basic_concept_springboot.data.dto.v1.ProductDTO;
import com.sergiohscl.basic_concept_springboot.exceptions.ResourceBadRequestException;
import com.sergiohscl.basic_concept_springboot.exceptions.ResourceNotFoundException;
import com.sergiohscl.basic_concept_springboot.model.Product;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private Logger logger = LoggerFactory.getLogger(ProductService.class.getName());

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> listProducts() {
        logger.info("Finding all Products!");
        return productRepository.findAll();
    }

    public ProductDTO searchById(Long id) {
        logger.info("Finding one Product!");
        Product prod =  productRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Product with ID "+id+" not found!"));
        return toDTO(prod);
    }
    
    public ProductDTO saveProduct(ProductDTO dto) {
        logger.info("Creating one Product!");
        if (dto.getName() == null || dto.getName().trim().isEmpty()) {
            throw new ResourceBadRequestException("The product name cannot be empty.");
        }
        if (dto.getPrice() == null || dto.getPrice() < 0) {
            throw new ResourceBadRequestException("The price of the product must be ≥ 0.");
        }

        dto.setId(null);

        Product saved = productRepository.save(toEntity(dto));
        return toDTO(saved);
    }
    

    public ProductDTO updateProduct(Long id, ProductDTO dto) {
        logger.info("Updating one Product!");
        Product updated = productRepository.findById(id)
            .map(existing -> {
                existing.setName(dto.getName());
                existing.setPrice(dto.getPrice());
                return productRepository.save(existing);
            })
            .orElseThrow(() -> new ResourceNotFoundException("Product with ID "+id+" not found!"));
        return toDTO(updated);
    }

    public void deleteProduct(Long id) {
        logger.info("Deleting one Product!");
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product with ID "+id+" not found!");
        }
        productRepository.deleteById(id);
    }

    private ProductDTO toDTO(Product p) {
        return new ProductDTO(p.getId(), p.getName(), p.getPrice());
    }

    private Product toEntity(ProductDTO dto) {
        Product p = new Product();
        p.setId(dto.getId());
        p.setName(dto.getName());
        p.setPrice(dto.getPrice());
        return p;
    }
  
}
