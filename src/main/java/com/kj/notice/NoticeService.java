package com.kj.notice;

import com.kj.faq.entity.FaqBoard;
import com.kj.member.dto.CustomUserDetails;
import com.kj.member.entity.Member;
import com.kj.notice.dto.NoticeDto;
import com.kj.notice.entity.Notice;
import com.kj.notice.repository.NoticeRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
        Notice notice = NoticeDto.toEntity(noticeDto,customUserDetails.getLoggedMember(),null);
        noticeRepository.save(notice);
    }

    public Page<Notice> noticeList(int page){
        Pageable pageable = PageRequest.of(page,5, Sort.by(Sort.Direction.DESC,"noticeDate"));
        Page<Notice> noticeList = noticeRepository.findByAll(pageable);
        return noticeList;
    }
    @Transactional
    public Page<Notice> viewNoticeList(int page, int no){
        Pageable pageable = PageRequest.of(0,3, Sort.by(Sort.Direction.DESC,"noticeDate"));
        List<Notice> viewNoticeList = noticeRepository.findByBetweenNum(no);
        Page<Notice> notices = new PageImpl<>(viewNoticeList,pageable, viewNoticeList.size());
        return notices;
    }
    @Transactional
    public Notice findByIdSearchPlusView(Long id,String category,String keyword, HttpServletRequest request, HttpServletResponse response) {
        Optional<Notice> notice = noticeRepository.findById(id);
        if (notice.isPresent()){
            Cookie oldCookie = null;
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("boardView")) {
                        oldCookie = cookie;
                    }
                }
            }
            if (oldCookie != null) {
                if (!oldCookie.getValue().contains("[" + id.toString() + "]")) {
                    oldCookie.setValue(oldCookie.getValue() + "_[" + id + "]");
                    oldCookie.setPath("/");
                    oldCookie.setMaxAge(60 * 60 * 24);
                    response.addCookie(oldCookie);
                    if (category.equals("noticeTitle")) {
                        int noticeView = notice.get().getNoticeView()+1;
                        Notice notice1 = notice.get().updateView(noticeView);
                        notice1.setNextNotice(noticeRepository.findSearchTitleNextNotice(keyword,id));
                        notice1.setPrevNotice(noticeRepository.findSearchTitleprevNotice(keyword,id));
                        return notice1;
                    }else if(category.equals("noticeCategory")) {
                        int noticeView = notice.get().getNoticeView()+1;
                        Notice notice1 = notice.get().updateView(noticeView);
                        notice1.setNextNotice(noticeRepository.findSearchCategoryNextNotice(keyword,id));
                        notice1.setPrevNotice(noticeRepository.findSearchCategoryprevNotice(keyword,id));
                        return notice1;
                    }else {
                        int noticeView = notice.get().getNoticeView()+1;
                        Notice notice1 = notice.get().updateView(noticeView);
                        notice1.setNextNotice(noticeRepository.findSearchAllNextNotice(keyword,id));
                        notice1.setPrevNotice(noticeRepository.findSearchAllprevNotice(keyword,id));
                        return notice1;
                    }

                }
            } else {
                Cookie newCookie = new Cookie("boardView","[" + id + "]");
                newCookie.setPath("/");
                newCookie.setMaxAge(60 * 60 * 24);
                response.addCookie(newCookie);
                if (category.equals("noticeTitle")) {
                    int noticeView = notice.get().getNoticeView()+1;
                    Notice notice1 = notice.get().updateView(noticeView);
                    notice1.setNextNotice(noticeRepository.findSearchTitleNextNotice(keyword,id));
                    notice1.setPrevNotice(noticeRepository.findSearchTitleprevNotice(keyword,id));
                    return notice1;
                }else if(category.equals("noticeCategory")) {
                    int noticeView = notice.get().getNoticeView()+1;
                    Notice notice1 = notice.get().updateView(noticeView);
                    notice1.setNextNotice(noticeRepository.findSearchCategoryNextNotice(keyword,id));
                    notice1.setPrevNotice(noticeRepository.findSearchCategoryprevNotice(keyword,id));
                    return notice1;
                }else {
                    int noticeView = notice.get().getNoticeView()+1;
                    Notice notice1 = notice.get().updateView(noticeView);
                    notice1.setNextNotice(noticeRepository.findSearchAllNextNotice(keyword,id));
                    notice1.setPrevNotice(noticeRepository.findSearchAllprevNotice(keyword,id));
                    return notice1;
                }

            }
            if (category.equals("noticeTitle")) {

                notice.get().setNextNotice(noticeRepository.findSearchTitleNextNotice(keyword,id));
                notice.get().setPrevNotice(noticeRepository.findSearchTitleprevNotice(keyword,id));
                return notice.get();
            }else if(category.equals("noticeCategory")) {
                notice.get().setNextNotice(noticeRepository.findSearchCategoryNextNotice(keyword,id));
                notice.get().setPrevNotice(noticeRepository.findSearchCategoryprevNotice(keyword,id));
                return notice.get();
            }else {
                notice.get().setNextNotice(noticeRepository.findSearchAllNextNotice(keyword,id));
                notice.get().setPrevNotice(noticeRepository.findSearchAllprevNotice(keyword,id));
                return notice.get();
            }

        }
        throw new RuntimeException("게시판이 없습니다.");
    }


    public Notice findById(Long id) {
        Optional<Notice> notice = noticeRepository.findById(id);
        if (notice.isPresent()){

            return notice.get();
        }
        throw new RuntimeException("게시판이 없습니다.");
    }

    @Transactional
    public Notice findByIdPlusView(Long id, HttpServletRequest request, HttpServletResponse response) {
        Optional<Notice> notice = noticeRepository.findById(id);
        if (notice.isPresent()){
                Cookie oldCookie = null;
                Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("boardView")) {
                        oldCookie = cookie;
                    }
                }
            }
            if (oldCookie != null) {
                if (!oldCookie.getValue().contains("[" + id.toString() + "]")) {
                    int noticeView = notice.get().getNoticeView()+1;
                    Notice notice1 = notice.get().updateView(noticeView);
                    notice1.setNextNotice(noticeRepository.findNextNotice(id));
                    notice1.setPrevNotice(noticeRepository.findPreNotice(id));
                    oldCookie.setValue(oldCookie.getValue() + "_[" + id + "]");
                    oldCookie.setPath("/");
                    oldCookie.setMaxAge(60 * 60 * 24);
                    response.addCookie(oldCookie);
                    return notice1;
                }
            } else {
                int noticeView = notice.get().getNoticeView()+1;
                Notice notice1 = notice.get().updateView(noticeView);
                notice1.setNextNotice(noticeRepository.findNextNotice(id));
                notice1.setPrevNotice(noticeRepository.findPreNotice(id));
                Cookie newCookie = new Cookie("boardView","[" + id + "]");
                newCookie.setPath("/");
                newCookie.setMaxAge(60 * 60 * 24);
                response.addCookie(newCookie);
                return notice1;
            }
            notice.get().setNextNotice(noticeRepository.findNextNotice(id));
            notice.get().setPrevNotice(noticeRepository.findPreNotice(id));
            return notice.get();
        }
        throw new RuntimeException("게시판이 없습니다.");
    }
    @Transactional
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

    public Page<Notice> searchList(String category, String keyword, int page) {
        Pageable pageable = PageRequest.of(page, 5, Sort.by(Sort.Direction.DESC, "noticeDate"));
        if (category.equals("noticeTitle")) {
            Page<Notice> notices = noticeRepository.findAllSearchTitle(keyword,pageable);
            return notices;
        } else if(category.equals("noticeCategory")) {
            Page<Notice> notices = noticeRepository.findAllSearchCategory(keyword,pageable);
            return notices;
        }else {
            //전체검색
            Page<Notice> notices = noticeRepository.findAllSearch(keyword,pageable);
            return notices;
        }
    }
}
