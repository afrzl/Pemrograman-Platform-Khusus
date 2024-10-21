package com.polstat.perpustakaan.repository;

import com.polstat.perpustakaan.entity.Member;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "member", path = "member")
public interface MemberRepository extends PagingAndSortingRepository<Member, Long>, CrudRepository<Member, Long> {
    List<Member> findByName(@Param("name") String name);
    List<Member> findByMemberID(@Param("member_id") String memberID);
}
