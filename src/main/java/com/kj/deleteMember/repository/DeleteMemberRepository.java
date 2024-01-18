package com.kj.deleteMember.repository;

import com.kj.deleteMember.entity.DeleteMember;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeleteMemberRepository extends JpaRepository<DeleteMember, Long> {
}
