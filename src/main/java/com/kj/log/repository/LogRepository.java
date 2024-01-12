package com.kj.log.repository;

import com.kj.log.entity.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface LogRepository extends JpaRepository<Log,Long> {

    List<Log> findByLoginDateBetween(LocalDateTime startDatetime,LocalDateTime endDatetime);
    //Long logCount();


}
