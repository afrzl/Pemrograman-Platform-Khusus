package com.polstat.perpustakaan.controller;

import com.polstat.perpustakaan.dto.BookDto;
import com.polstat.perpustakaan.entity.Member;
import com.polstat.perpustakaan.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class MemberGraphqlController {
    @Autowired
    private MemberRepository memberRepository;

    @QueryMapping
    public List<Member> members() {
        return (List<Member>) memberRepository.findAll();
    }

    @QueryMapping
    public List<Member> memberById(@Argument String memberID) {
        return memberRepository.findByMemberID(memberID);
    }

    @MutationMapping
    public Member createMember(@Argument String memberID,
                               @Argument String name,
                               @Argument String address,
                               @Argument String phoneNumber) {
        Member member = Member.builder()
                .memberID(memberID)
                .name(name)
                .address(address)
                .phoneNumber(phoneNumber)
                .build();

        return memberRepository.save(member);
    }

    @MutationMapping
    public Member updateMember(@Argument String id,
                               @Argument String memberID,
                               @Argument String name,
                               @Argument String address,
                               @Argument String phoneNumber) {
        Member existingMember = memberRepository.findById(Long.parseLong(id))
                .orElseThrow(() -> new RuntimeException("Member not found with id: " + id));

        if (memberID != null) existingMember.setMemberID(memberID);
        if (name != null) existingMember.setName(name);
        if (address != null) existingMember.setAddress(address);
        if (phoneNumber != null) existingMember.setPhoneNumber(phoneNumber);

        return memberRepository.save(existingMember);
    }

    @MutationMapping
    public Member deleteMember(@Argument String id) {
        Member existingMember = memberRepository.findById(Long.parseLong(id))
                .orElseThrow(() -> new RuntimeException("Member not found with id: " + id));

        memberRepository.delete(existingMember);

        return existingMember;
    }
}
