package com.example.SpringbootJavaProject.service;


import com.example.SpringbootJavaProject.entitiy.Product;
import com.example.SpringbootJavaProject.repository.ProductRepository;
import com.example.SpringbootJavaProject.request.ProductRequest;
import com.example.SpringbootJavaProject.request.ProductUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

//    public Product save(Product product) {
//        return productRepository.save(product);
//    }

    // DTO를 사용해 상품을 저장하는 메서드
    public Product save(ProductRequest productRequest) {
        // DTO에서 Product 엔티티로 변환
        Product product = new Product();
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setStock(productRequest.getStock());
        product.setCreatedDate(LocalDateTime.now());

        // Product 엔티티를 저장
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

    public void update(ProductRequest productRequest) {
        Product existingProduct = productRepository.findById(productRequest.getId())
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));

        // DTO로 받은 업데이트 내용을 기존 상품에 반영
        existingProduct.setName(productRequest.getName());
        existingProduct.setDescription(productRequest.getDescription());
        existingProduct.setPrice(productRequest.getPrice());
        existingProduct.setStock(productRequest.getStock());

        // 업데이트된 상품 정보 저장
        productRepository.save(existingProduct);
    }
}

