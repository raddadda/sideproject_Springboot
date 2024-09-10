package com.example.SpringbootJavaProject.request;

import com.example.SpringbootJavaProject.entitiy.Product;
import lombok.Data;


import java.time.LocalDateTime;

@Data
public class ProductRequest {
    private Long id;
    private String name;
    private String description;
    private int stock;
    private int price;
    private LocalDateTime createdDate;


    public Product toEntity(){
        return Product.builder()
                .id(this.id)
                .name(this.name)
                .description(this.description)
                .stock(this.stock)
                .price(this.price)
                .createdDate(this.createdDate)
                .build();
    }
}
