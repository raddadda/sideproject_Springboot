package com.example.SpringbootJavaProject.entitiy;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private int quantity;
    private int totalPrice;
    // 개별 항목의 총 금액 계산
    public int getTotalPrice() {
        return quantity * totalPrice;
    }

}
