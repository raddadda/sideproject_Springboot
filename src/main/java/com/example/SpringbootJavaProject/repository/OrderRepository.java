package com.example.SpringbootJavaProject.repository;

import com.example.SpringbootJavaProject.entitiy.Member;
import com.example.SpringbootJavaProject.entitiy.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByMember(Member member);
}
