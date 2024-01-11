package com.kj.member.repository;

import com.kj.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {

    Member findByuserIdAndPassword(String userId, String password);

    Optional<Member> findByUserId(String userId);

    Optional<Member> findByNickName(String nickName);

    @Query("select b from MEMBER b where b.userId like %:keyword%")
    Page<Member> findByUserId(@Param("keyword") String keyword, Pageable pageable);
    @Query("select b from MEMBER b where b.userName like %:keyword%")
    Page<Member> findByUserName(@Param("keyword") String keyword,Pageable pageable);

    @Query("select b from MEMBER b where b.nickName like %:keyword%")
    Page<Member> findByNickName(@Param("keyword") String keyword,Pageable pageable);

    @Query("select b from member b where b.levels like %:keyword%")
    Page<Member> findByLevels(@Param("keyword") String keyword,Pageable pageable);

    /*@Query(value = "select b from member b where " +
            "b.userId like %:keyword% or " +
            "b.nickName like %:keyword% or " +
            "b.userName like %:keyword%  or " +
            "b.levels like %:keyword%" )
    Page<Member> findByAllCategory(@Param("keyword") String keyword,Pageable pageable);*/

}
