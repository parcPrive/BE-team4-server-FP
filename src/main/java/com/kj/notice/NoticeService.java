package com.kj.notice;

import com.kj.faq.entity.FaqBoard;
import com.kj.member.dto.CustomUserDetails;
import com.kj.notice.dto.NoticeDto;
import com.kj.notice.entity.Notice;
import com.kj.notice.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeRepository noticeRepository;


    public void insertNotice(NoticeDto noticeDto, CustomUserDetails customUserDetails){
        Notice notice = NoticeDto.toEntity(noticeDto,customUserDetails.getLoggedMember());
        noticeRepository.save(notice);
    }

    public Page<Notice> noticeList(int page){
        Pageable pageable = PageRequest.of(page,5, Sort.by(Sort.Direction.DESC,"noticeDate"));
        Page<Notice> noticeList = noticeRepository.findByAll(pageable);
        return noticeList;
    }

    public Notice findById(Long id) {
        Optional<Notice> notice = noticeRepository.findById(id);
        if (notice.isPresent()){
            return notice.get();
        }
        throw new RuntimeException("게시판이 없습니다.");
    }

    public List<Notice> findByBetweenNum(int no) {
        List<Notice> noticeList = noticeRepository.findByBetweenNum(no);
        return noticeList;
    }
}
