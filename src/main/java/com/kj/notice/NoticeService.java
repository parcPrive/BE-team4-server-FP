package com.kj.notice;

import com.kj.faq.entity.FaqBoard;
import com.kj.member.dto.CustomUserDetails;
import com.kj.notice.dto.NoticeDto;
import com.kj.notice.entity.Notice;
import com.kj.notice.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public Page<Notice> viewNoticeList(int page, int no){
        Pageable pageable = PageRequest.of(0,3, Sort.by(Sort.Direction.DESC,"noticeDate"));
        List<Notice> viewNoticeList = noticeRepository.findByBetweenNum(no);
        Page<Notice> notices = new PageImpl<>(viewNoticeList,pageable, viewNoticeList.size());
        return notices;
    }

    public Notice findById(Long id) {
        Optional<Notice> notice = noticeRepository.findById(id);
        if (notice.isPresent()){
            return notice.get();
        }
        throw new RuntimeException("게시판이 없습니다.");
    }

    @Transactional
    public Notice findByIdPlusView(Long id) {
        Optional<Notice> notice = noticeRepository.findById(id);
        if (notice.isPresent()){
            int noticeView = notice.get().getNoticeView()+1;
            Notice notice1 = notice.get().updateView(noticeView);
            return notice1;
        }
        throw new RuntimeException("게시판이 없습니다.");
    }

    public Notice updateNotice(NoticeDto noticeDto) {
        Optional<Notice> notice = noticeRepository.findById(noticeDto.getId());
        if (notice.isPresent()){
            return notice.get().update(noticeDto);
        }
        throw new  RuntimeException("존재하지 않습니다.");
    }

    public boolean deleteById(Long id) {
        Optional<Notice> notice = noticeRepository.findById(id);
        if (notice.isPresent()){
            noticeRepository.delete(notice.get());
            return true;
        }
        return false;
    }
}
