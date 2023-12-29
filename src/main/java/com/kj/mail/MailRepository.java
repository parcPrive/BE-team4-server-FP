package com.kj.mail;

import com.kj.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MailRepository extends JpaRepository<Member,Long> {

    Optional<Member> findByEmail(String email);
}
