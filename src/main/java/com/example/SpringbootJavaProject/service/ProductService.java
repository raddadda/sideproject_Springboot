package com.example.SpringbootJavaProject.service;


import com.example.SpringbootJavaProject.entitiy.Product;
import com.example.SpringbootJavaProject.repository.ProductRepository;
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

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    // 이름 오름차순으로 정렬된 제품 목록 조회
    public List<Product> findAllSortedByNameAsc() {
        return productRepository.findAllByOrderByNameAsc();
    }

    // 이름 내림차순으로 정렬된 제품 목록 조회
    public List<Product> findAllSortedByNameDesc() {
        return productRepository.findAllByOrderByNameDesc();
    }

    public List<Product> findAllSortedByPriceAsc() {
        return productRepository.findAllByOrderByPriceAsc();
    }

    public List<Product> findAllSortedByPriceDesc() {return productRepository.findAllByOrderByPriceDesc();}

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    public void updateStock(Long productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));

        // 재고 수량 업데이트
        product.setStock(product.getStock() - quantity);

        productRepository.save(product);
    }
}

