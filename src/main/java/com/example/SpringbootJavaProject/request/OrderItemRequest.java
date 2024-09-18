package com.example.SpringbootJavaProject.request;

import com.example.SpringbootJavaProject.entitiy.OrderItem;
import lombok.Data;

@Data
public class OrderItemRequest {
    private Long id;
    private String ordername;
    private int quantity; // 주문 수량
    private int totalPrice; // 주문 가격
    public OrderItemRequest(OrderItem orderItem) {

        id = orderItem.getId();
        ordername = orderItem.getProduct().getName();
        quantity = orderItem.getQuantity();
        totalPrice = orderItem.getTotalPrice();
    }
}
