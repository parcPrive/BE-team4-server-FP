package com.kj.member.repository;

import com.kj.log.entity.Log;
import com.kj.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {

    Member findByuserIdAndPassword(String userId, String password);

    Optional<Member> findByUserId(String userId);

    //탈퇴 회원이 아닌 전체 맴버 중 아이디 일치 여부 즉 로그인시 사용
    @Query("select b from Member b where b.levels < '6' and b.userId = :userId")
    Optional<Member> findByUserIdNotDelete(@Param("userId")String userId);

    Optional<Member> findByNickName(String nickName);

    @Query("select b from Member b where b.levels < '5'")
    Page<Member> findByAll(Pageable pageable);
    List<Member> findByRegisterDateBetween(LocalDateTime startDatetime, LocalDateTime endDatetime);
    @Query("select b from Member b where b.levels = '5'")
    Page<Member> findByBlack(Pageable pageable);
    @Query(value = "SELECT * FROM MEMBER m LEFT JOIN delete_member dm ON m.ID =dm.member_id WHERE m.LEVELS ='6'",nativeQuery = true)
    Page<Member> findByDelete(Pageable pageable);

    @Query("select b from Member b where b.userId like %:keyword% and b.levels < '5'")
    Page<Member> findByUserId(@Param("keyword") String keyword, Pageable pageable);
    @Query("select b from Member b where b.userId like %:keyword% and b.levels = '5'" )
    Page<Member> findByBlackUserId(@Param("keyword") String keyword, Pageable pageable);
    @Query("select b from Member b where b.userName like %:keyword% and b.levels < '5'")
    Page<Member> findByUserName(@Param("keyword") String keyword,Pageable pageable);
    @Query("select b from Member b where b.userName like %:keyword% and b.levels = '5'")
    Page<Member> findByBlackUserName(String keyword, Pageable pageable);

    @Query("select b from Member b where b.nickName like %:keyword% and b.levels < '5'")
    Page<Member> findByNickName(@Param("keyword") String keyword,Pageable pageable);

    @Query("select b from Member b where b.nickName like %:keyword% and b.levels = '5'")
    Page<Member> findByBlackNickName(@Param("keyword") String keyword,Pageable pageable);

    @Query("select b from Member b where b.levels like %:keyword%")
    Page<Member> findByLevels(@Param("keyword") String keyword,Pageable pageable);

    @Query(value = "select b from Member b where b.levels <'5' and " +
            "(b.userId like %:keyword% or " +
            "b.nickName like %:keyword% or " +
            "b.userName like %:keyword%)")
    Page<Member> findByAllCategory(@Param("keyword") String keyword,Pageable pageable);
    @Query(value = "select b from Member b where b.levels ='5' and " +
            "(b.userId like %:keyword% or " +
            "b.nickName like %:keyword% or " +
            "b.userName like %:keyword%)")
    Page<Member> findByBlackAllCategory(@Param("keyword") String keyword,Pageable pageable);

    @Query("select b from Member b where b.id in (:id)")
    List<Member> findLevelAll(@Param("id") List<Long> id);



   /* @Query("select b from Member m where c.id IN (:id)")
    Optional<Member> findLevelAll(@Param("id") Long[] id);*/
}
