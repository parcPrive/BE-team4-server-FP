package com.kj.deleteMember.repository;

import com.kj.deleteMember.entity.DeleteMember;
import com.kj.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeleteMemberRepository extends JpaRepository<DeleteMember, Long> {


    @Query(value = "select d from DeleteMember d where d.member.id = :id")
    Optional<DeleteMember> findByMember(@Param("id") Long id);

    @Query(value = "select d from DeleteMember d")
    Page<DeleteMember> findByDeleteMember(Pageable pageable);

    @Query("select d from DeleteMember d where d.member.id in (:id)")
    List<DeleteMember> findByDeletelAll(@Param("id") List<Long> id);

    @Query("select d from DeleteMember d where d.member.userId like %:keyword%")
    Page<DeleteMember> findByDeleteMemberUserId(@Param("keyword") String keyword, Pageable pageable);
    @Query("select d from DeleteMember d where d.member.userName like %:keyword%")
    Page<DeleteMember> findByDeleteMemberUserName(@Param("keyword") String keyword, Pageable pageable);
    @Query("select d from DeleteMember d where d.member.nickName like %:keyword%")
    Page<DeleteMember> findByDeleteMemberNickName(@Param("keyword") String keyword, Pageable pageable);
    @Query(value = "select d from DeleteMember d where" +
            "(d.member.userId like %:keyword% or " +
            "d.member.nickName like %:keyword% or " +
            "d.member.userName like %:keyword%)")
    Page<DeleteMember> findByDeleteAllCategory(@Param("keyword") String keyword,Pageable pageable);

}
