package com.example.SpringbootJavaProject.service;

import com.example.SpringbootJavaProject.entitiy.Member;
import com.example.SpringbootJavaProject.repository.MemberRepository;
import com.example.SpringbootJavaProject.request.JoinRequest;
import com.example.SpringbootJavaProject.request.LoginRequest;
import com.example.SpringbootJavaProject.request.MemberUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    public MemberService(MemberRepository memberRepository,BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.memberRepository = memberRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void join(JoinRequest joinRequest){

        if(ValidDuplicateMemberByLoginId(joinRequest.getLoginId())){
            throw new IllegalArgumentException("아이디가 이미 사용 중입니다.");
        }

        joinRequest.setPassword(bCryptPasswordEncoder.encode(joinRequest.getPassword()));
        memberRepository.save(joinRequest.toEntity());
    }
    public Member login(LoginRequest loginRequest){
        Member findMember = memberRepository.findByLoginId(loginRequest.getLoginId());
        return findMember;
    }
    private boolean ValidDuplicateMemberByLoginId(String loginId){
        Member member = memberRepository.findByLoginId(loginId);
        if(member == null){
            return false;
        }
        return true;
    }

    public Member getMemberInfoByLoginId(String loginId){

        Member member = memberRepository.findByLoginId(loginId);

        return member;
    }
    public Member findByLoginId(String loginId){

        Member member = memberRepository.findByLoginId(loginId);

        return member;
    }

    public void updateMemberInfo(MemberUpdateRequest memberUpdateRequest, Member member) {
        member.setName(memberUpdateRequest.getName());
        member.setEmail(memberUpdateRequest.getEmail());
        memberRepository.save(member); // 수정된 회원 정보 저장
    }




}
