package com.kj.noticeComment;

import com.kj.member.entity.Member;
import com.kj.member.repository.MemberRepository;
import com.kj.notice.entity.Notice;
import com.kj.notice.repository.NoticeRepository;
import com.kj.noticeComment.dto.CommentDto;
import com.kj.noticeComment.dto.CommentRequestDto;
import com.kj.noticeComment.entity.Comment;
import com.kj.noticeComment.repoistory.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {
    private final MemberRepository memberRepository;
    private final NoticeRepository noticeRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public Comment insert(Long id, Long memberId, CommentDto commentDto)  {
        Member member =memberRepository.findById(memberId)
                .orElseThrow(()->new RuntimeException("존재x"));
        Notice notice =noticeRepository.findById(id)
                .orElseThrow(()->new RuntimeException("존재x"));
        Comment comment = CommentDto.toEntity(commentDto,member,notice);
        return commentRepository.save(comment);
    }

    public List<Comment> commentList(Long id) {
        List<Comment> commentList = commentRepository.findByNoticeId(id);
        return commentList;
    }
}
