package com.kj.log;

import com.kj.log.dto.LogDto;
import com.kj.log.entity.Log;
import com.kj.log.repository.LogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LogService {
    private final LogRepository logRepository;

    public List<Log> findByLog(){
        LocalDateTime startDatetime = LocalDateTime.of(LocalDate.now(), LocalTime.of(0,0,0)); //오늘 00:00:00
        LocalDateTime endDatetime = LocalDateTime.of(LocalDate.now(), LocalTime.of(23,59,59)); //오늘 23:59:59
        log.info("startDatetime=={}",startDatetime);
        log.info("endDatetime=={}",endDatetime);
        List<Log> logList = logRepository.findByLoginDateBetween(startDatetime,endDatetime);
        return logList;
    }
}
