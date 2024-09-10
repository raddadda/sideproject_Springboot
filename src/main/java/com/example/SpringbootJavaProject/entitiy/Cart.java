package com.example.SpringbootJavaProject.entitiy;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Setter
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private List<CartItem> items = new ArrayList<>();

    @OneToOne(mappedBy = "cart")
    private Member member;

    // 총 금액 계산 등 추가적인 로직을 포함할 수 있음
    public void addItem(CartItem item) {
        items.add(item);
        item.setCart(this);
    }

    public double getTotalPrice() {
        return items.stream().mapToDouble(CartItem::getTotalPrice).sum();
    }
}
