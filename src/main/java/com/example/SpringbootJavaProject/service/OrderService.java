package com.example.SpringbootJavaProject.service;

import com.example.SpringbootJavaProject.entitiy.*;
import com.example.SpringbootJavaProject.repository.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductService productService;

    public OrderService(OrderRepository orderRepository, ProductService productService) {
        this.orderRepository = orderRepository;
        this.productService = productService;
    }

    public void createOrder(Member member, Cart cart) {
        Order order = new Order();
        order.setMember(member);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("Pending");

        for (CartItem cartItem : cart.getItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setTotalPrice(cartItem.getProduct().getPrice() * cartItem.getQuantity());
            order.addOrderItem(orderItem);
            // 재고 감소
            productService.updateStock(cartItem.getProduct().getId(), cartItem.getQuantity());
        }

        orderRepository.save(order);
    }

    // 해당 회원의 주문 목록을 가져옴
    public List<Order> findOrdersByMember(Member member) {
        return orderRepository.findByMember(member);
    }

    // 모든 회원의 주문 목록을 가져옴
    public List<Order> findAll() {
        return orderRepository.findAll();
    }


    // 모든 회원의 주문 목록을 가져옴
    public List<Order> findAllWithItem() {
        return orderRepository.findAllWithItem();
    }
    public Page<Order> findAllWithItem2(Pageable pageable) {
        return orderRepository.findAllWithItem2(pageable);
    }
    public Page<Order> findAllWithItemByMember(Member member, Pageable pageable) {
        return orderRepository.findAllWithItemByMember(member, pageable);
    }


}
