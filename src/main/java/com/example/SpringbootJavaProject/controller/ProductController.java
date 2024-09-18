package com.example.SpringbootJavaProject.controller;

import com.example.SpringbootJavaProject.entitiy.Member;
import com.example.SpringbootJavaProject.entitiy.Product;
import com.example.SpringbootJavaProject.request.ProductRequest;
import com.example.SpringbootJavaProject.service.CartService;
import com.example.SpringbootJavaProject.service.MemberService;
import com.example.SpringbootJavaProject.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final MemberService memberService;
    private final CartService cartService;
    @Autowired
    public ProductController(ProductService productService,MemberService memberService,CartService cartService) {
        this.productService = productService;
        this.memberService = memberService;
        this.cartService = cartService;
    }

@GetMapping
public String listProducts(HttpSession session, Model model) {
    // 세션에서 정렬 기준을 가져옵니다.
    String sortOrder = (String) session.getAttribute("sortOrder");
    if (sortOrder == null) {
        sortOrder = "nameAsc"; // 기본 정렬 방식은 오름차순
    }

    // 정렬 기준에 따라 데이터를 불러옵니다.
    if ("nameDesc".equals(sortOrder)) {
        model.addAttribute("products", productService.findAllSortedByNameDesc());
    } else if("priceAsc".equals(sortOrder)) {
        model.addAttribute("products", productService.findAllSortedByPriceAsc());
    } else if("priceDesc".equals(sortOrder)) {
        model.addAttribute("products", productService.findAllSortedByPriceDesc());
    }  else {
        model.addAttribute("products", productService.findAllSortedByNameAsc());
    }

    // 현재 정렬 상태를 템플릿으로 전달합니다.
    model.addAttribute("sortOrder", sortOrder);

    return "/products/productList"; // products 폴더의 productList 템플릿 사용
}

    @GetMapping("/v1/products")
    public String v1_listProducts(HttpSession session, Model model) {
        // 세션에서 정렬 기준을 가져옵니다.
        String sortOrder = (String) session.getAttribute("sortOrder");
        if (sortOrder == null) {
            sortOrder = "nameAsc"; // 기본 정렬 방식은 오름차순
        }

        // 정렬 기준에 따라 데이터를 불러옵니다.
        if ("nameDesc".equals(sortOrder)) {

            model.addAttribute("products", productService.findAllSortedByNameDesc());



        } else if("priceAsc".equals(sortOrder)) {
            model.addAttribute("products", productService.findAllSortedByPriceAsc());
        } else if("priceDesc".equals(sortOrder)) {
            model.addAttribute("products", productService.findAllSortedByPriceDesc());
        }  else {
            model.addAttribute("products", productService.findAllSortedByNameAsc());
        }

        // 현재 정렬 상태를 템플릿으로 전달합니다.
        model.addAttribute("sortOrder", sortOrder);

        return "/products/productList"; // products 폴더의 productList 템플릿 사용
    }
    @PostMapping("/sort")
    public String setSortOrder(@RequestParam("sortOrder") String sortOrder, HttpSession session) {
        // 세션에 정렬 기준을 저장합니다.
        session.setAttribute("sortOrder", sortOrder);
        return "redirect:/products"; // 제품 목록 페이지로 리디렉션
    }



    @GetMapping("/{id}")
    public String viewProduct(@PathVariable("id") Long id, Model model) {
        Optional<Product> product = productService.findById(id);
        product.ifPresent(value -> model.addAttribute("product", value));
        return "/products/productDetail";
    }

    @GetMapping("/new")
    public String newProductForm(Model model) {
        model.addAttribute("product", new ProductRequest());

        return "/products/productForm";
    }

    @PostMapping
    public String saveProduct(@ModelAttribute Product product) {
        product.setCreatedDate(LocalDateTime.now());
        productService.save(product);
        return "redirect:/products";
    }

    @GetMapping("/{id}/edit")
    public String editProductForm(@PathVariable("id") Long id, Model model) {
        Optional<Product> product = productService.findById(id);
        product.ifPresent(value -> model.addAttribute("product", value));
        return "/products/productForm";
    }

    @PostMapping("/{id}/delete")
    public String deleteProduct(@PathVariable("id") Long id) {
        productService.deleteById(id);
        return "redirect:/products";
    }


    @GetMapping("/products")
    public String listProducts(Model model) {
        model.addAttribute("products", productService.findAll());
        return "/products/productList";
    }

    // 상품을 장바구니에 추가하는 메서드
    @PostMapping("/add/{productId}")
    public String addToCart(@PathVariable("productId") Long productId, @RequestParam("quantity") int quantity, Principal principal) {
        // 현재 로그인한 회원을 가져옴
        Member member = memberService.findByLoginId(principal.getName());
        //String loginId = member.getLoginId();

        // 해당 상품을 찾음
        Product product = productService.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));

        System.out.println("@@");
        // 장바구니에 상품 추가
        cartService.addToCart(member, quantity, product);


        return "redirect:/products";
    }


}
