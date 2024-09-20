package com.example.SpringbootJavaProject.repository;

import com.example.SpringbootJavaProject.entitiy.Member;
import com.example.SpringbootJavaProject.entitiy.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByMember(Member member);
    List<Order> findAll();
    // JPQL을 사용하여 fetch join을 적용한 쿼리
    @Query("select distinct o from Order o" +
            " join fetch o.member m" +
            " join fetch o.orderItems oi" +
            " join fetch oi.product p")
    List<Order> findAllWithItem();

    // v4 - 회원 전체 조회
    @Query("select o from Order o" +
            " join fetch o.member m")
    Page<Order> findAllWithItem2(Pageable pageable);

    // - 특정 회원 조회
    @Query("select o from Order o" +
            " join fetch o.member m" +
            " where m = :member")
    Page<Order> findAllWithItemByMember(@Param("member") Member member, Pageable pageable);

}
