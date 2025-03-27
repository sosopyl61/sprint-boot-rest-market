package by.rymtsou.service;

import by.rymtsou.model.Product;
import by.rymtsou.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.getProductById(id);
    }

    public Optional<List<Product>> getAllProducts() {
        return productRepository.getAllProducts();
    }

    public Optional<Product> createProduct(Product product) {
        Optional<Long> productId = productRepository.createProduct(product);
        if (productId.isPresent()) {
            return getProductById(productId.get());
        }
        return Optional.empty();
    }

    public Optional<Product> updateProduct(Product product) {
        Boolean isProductUpdated = productRepository.updateProduct(product);
        if (isProductUpdated) {
            return getProductById(product.getId());
        }
        return Optional.empty();
    }

    public Boolean deleteProduct(Long id) {
        return productRepository.deleteProduct(id);
    }

}