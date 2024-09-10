package com.example.SpringbootJavaProject.repository;

import com.example.SpringbootJavaProject.entitiy.Product;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // 이름을 기준으로 오름차순 정렬
    List<Product> findAllByOrderByNameAsc();

    // 이름을 기준으로 내림차순 정렬
    List<Product> findAllByOrderByNameDesc();
    // 가격을 기준으로 오름차순 정렬
    List<Product> findAllByOrderByPriceAsc();
    // 이름을 기준으로 내림차순 정렬
    List<Product> findAllByOrderByPriceDesc();

}
