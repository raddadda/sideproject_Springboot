package com.example.SpringbootJavaProject.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProductUpdateRequest {
    private Long id;
    private String name;
    private String description;
    private int price;
    private int stock;
}