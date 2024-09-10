package com.example.SpringbootJavaProject.entitiy;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private int stock;
    private int price;
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdDate;

    // 엔티티가 저장되기 전에 자동으로 createdDate를 설정
    @PrePersist
    public void prePersist() {
        this.createdDate = LocalDateTime.now();
    }
}