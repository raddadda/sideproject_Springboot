package com.example.SpringbootJavaProject.request;

import com.example.SpringbootJavaProject.entitiy.Order;
import com.example.SpringbootJavaProject.entitiy.OrderItem;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Data
public class OrderRequest {
    private Long id;
    private LocalDateTime orderDate;
    private int totalPrice;
//    private String status;

    private List<OrderItemRequest> orderItems;


    public OrderRequest(Order order){

        id = order.getId();
        orderDate = order.getOrderDate();
        totalPrice = order.getTotalPrice();
        orderItems = order.getOrderItems().stream()
                .map(orderItem -> new OrderItemRequest(orderItem))
                .collect(toList());
    }
}
