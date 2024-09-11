package com.example.SpringbootJavaProject.controller;

import com.example.SpringbootJavaProject.entitiy.Cart;
import com.example.SpringbootJavaProject.entitiy.CartItem;
import com.example.SpringbootJavaProject.entitiy.Member;
import com.example.SpringbootJavaProject.entitiy.Order;
import com.example.SpringbootJavaProject.service.CartService;
import com.example.SpringbootJavaProject.service.MemberService;
import com.example.SpringbootJavaProject.service.OrderService;
import com.example.SpringbootJavaProject.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import java.security.Principal;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final CartService cartService;
    private final MemberService memberService;
    private final ProductService productService;
    public OrderController(OrderService orderService, CartService cartService, MemberService memberService,ProductService productService) {
        this.orderService = orderService;
        this.cartService = cartService;
        this.memberService = memberService;
        this.productService = productService;
    }

    @PostMapping("/create")
    public String createOrder(Principal principal) {
        Member member = memberService.findByLoginId(principal.getName());
        Cart cart = cartService.findByMember(member);
        orderService.createOrder(member, cart);

        // 장바구니의 각 아이템에 대해 재고 업데이트
        for (CartItem item : cart.getItems()) {
            productService.updateStock(item.getProduct().getId(), item.getQuantity());
        }

        // 장바구니 비우기 (선택 사항)
        //cartService.clearCart(member);

        return "redirect:/orders";
    }

    @GetMapping
    public String listOrders(Principal principal, Model model) {
        Member member = memberService.findByLoginId(principal.getName());
        List<Order> orders = orderService.findOrdersByMember(member);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        model.addAttribute("orders", orders);
        model.addAttribute("dateFormatter", formatter);
        return "/orders/orderList";
    }


}
