package com.example.SpringbootJavaProject.entitiy;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    private LocalDateTime orderDate;
    private String status;
    // 주문 생성 시 현재 시간을 설정하는 생성자 또는 메서드 추가
    public Order() {
        this.orderDate = LocalDateTime.now(); // 주문 생성 시 현재 시간으로 설정
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }
    // 총 금액 계산 메서드
    public int getTotalPrice() {
        return orderItems.stream()
                .mapToInt(OrderItem::getTotalPrice)
                .sum();
    }
//    public int getTotalPrice() {
//        return quantity * totalPrice;
//    }
}