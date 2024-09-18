package com.example.SpringbootJavaProject.controller;

import com.example.SpringbootJavaProject.entitiy.*;
import com.example.SpringbootJavaProject.request.OrderRequest;
import com.example.SpringbootJavaProject.service.CartService;
import com.example.SpringbootJavaProject.service.MemberService;
import com.example.SpringbootJavaProject.service.OrderService;
import com.example.SpringbootJavaProject.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.security.Principal;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

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
        System.out.println("------------member-----------");
        Member member = memberService.findByLoginId(principal.getName());
        System.out.println("------------orders-----------");
        //List<Order> orders = orderService.findOrdersByMember(member);
        List<Order> orders = orderService.findAll();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        System.out.println("------------ model.addAttribute-----------");
        model.addAttribute("orders", orders);
        model.addAttribute("dateFormatter", formatter);
        return "/orders/orderList";
    }

    @GetMapping("/v1/orders")
    public String v1_listOrders(Principal principal, Model model) {
        Member member = memberService.findByLoginId(principal.getName());
        List<Order> orders = orderService.findAll();
        for(Order order : orders){
          order.getMember().getName();
          order.getOrderDate();
          order.getOrderItems().forEach(orderItem -> orderItem.getProduct().getName()); // Product 이름 강제 로딩
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        model.addAttribute("orders", orders);
        return "/orders/orderList";
    }
    @GetMapping("/v2/orders")
    public String v2_listOrders(Principal principal, Model model) {
        System.out.println("------------member-----------");
        Member member = memberService.findByLoginId(principal.getName());
        System.out.println("------------orders-----------");
        List<Order> orders = orderService.findAll();
        System.out.println("------------result-----------");
        List<OrderRequest> result = orders.stream()
                .map(o -> new OrderRequest(o))
                .collect(toList());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        System.out.println("------------model.addAttribute-----------");
        model.addAttribute("orders", result);
        model.addAttribute("dateFormatter", formatter);
        return "/orders/orderList_v2";
    }

    @GetMapping("/v3/orders")
    public String v3_listOrders(Principal principal, Model model) {
        System.out.println("------------member-----------");
        Member member = memberService.findByLoginId(principal.getName());
        System.out.println("------------orders-----------");
        List<Order> orders = orderService.findAllWithItem();
        System.out.println("------------result-----------");
        List<OrderRequest> result = orders.stream()
                .map(o -> new OrderRequest(o))
                .collect(toList());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        System.out.println("------------model.addAttribute-----------");
        model.addAttribute("orders", result);
        model.addAttribute("dateFormatter", formatter);
        return "/orders/orderList_v3";
    }

    @GetMapping("/v4/orders")
    public String v4_listOrders(@RequestParam(value = "page", defaultValue = "0") int page,
                                @RequestParam(value = "size", defaultValue = "2") int size,
                                Principal principal, Model model) {
        System.out.println("------------member-----------");
        Member member = memberService.findByLoginId(principal.getName());

        System.out.println("------------orders-----------");
        Pageable pageable = PageRequest.of(page, size); // 페이징 정보 설정
        Page<Order> orderPage = orderService.findAllWithItem2(pageable); // 페이징된 결과 가져오기

        System.out.println("------------result-----------");
        List<OrderRequest> result = orderPage.getContent().stream()
                .map(OrderRequest::new)
                .collect(Collectors.toList());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        System.out.println("------------model.addAttribute-----------");
        model.addAttribute("orders", result); // 주문 리스트
        model.addAttribute("dateFormatter", formatter); // 날짜 포맷 설정
        model.addAttribute("currentPage", page); // 현재 페이지 정보
        model.addAttribute("totalPages", orderPage.getTotalPages()); // 전체 페이지 수 정보
        model.addAttribute("hasNext", orderPage.hasNext()); // 다음 페이지 존재 여부
        model.addAttribute("hasPrevious", orderPage.hasPrevious()); // 이전 페이지 존재 여부

        return "/orders/orderList_v4"; // 뷰 리턴
    }



}
