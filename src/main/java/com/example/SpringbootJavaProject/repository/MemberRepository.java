package com.example.SpringbootJavaProject.repository;

import com.example.SpringbootJavaProject.entitiy.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByLoginId(String loginId);
    //Optional<Member> findByLoginId2(String loginId);
    Member findByEmail(String email);
    //Optional<Member> findByEmail2(String email);

}
