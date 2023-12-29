package com.kj.member.repository;

import com.kj.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {

    Member findByuserIdAndPassword(String userId, String password);

    Optional<Member> findByUserId(String userId);
}
