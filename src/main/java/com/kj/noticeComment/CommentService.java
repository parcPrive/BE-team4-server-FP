package com.kj.noticeComment;

import com.kj.member.dto.CustomUserDetails;
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
//import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {
    private final MemberRepository memberRepository;
    private final NoticeRepository noticeRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public Long insert(Long id, Long memberId, CommentDto commentDto)  {
        Member member =memberRepository.findById(memberId)
                .orElseThrow(()->new RuntimeException("존재x"));
        Notice notice =noticeRepository.findById(id)
                .orElseThrow(()->new RuntimeException("존재x"));
        List<Comment> commentList = commentRepository.findByNoticeIds(id);
        Comment comment = CommentDto.toEntity(commentDto,member,notice,null,commentList);

        return commentRepository.save(comment).getId();
    }
    @Transactional
    public Comment insertReply(Long id, Long noticeId, CommentDto commentDto, CustomUserDetails customUserDetails)  {
        Member member =memberRepository.findById(customUserDetails.getLoggedMember().getId())
                .orElseThrow(()->new RuntimeException("존재x"));
        Notice notice =noticeRepository.findById(noticeId)
                .orElseThrow(()->new RuntimeException("존재x"));
        Comment parent = commentRepository.findById(id)
                .orElseThrow(()->new RuntimeException("존재x"));
        Comment comment = CommentDto.toEntity(commentDto,member,notice,parent,null);
        log.info("=={}",comment.getCommentContent());
        log.info("=={}",comment.getNotice());
        Comment comment1 = commentRepository.save(comment);
        return comment1;
    }

    public List<Comment> commentList(Long id) {
        List<Comment> commentList = commentRepository.findByNoticeIds(id);
        return commentList;
    }

    public List<Comment> replyCommentList(Long id) {
        List<Comment> commentList = commentRepository.findByParentIds(id);
        log.info("commentList=={}",commentList.size());
        return commentList;
    }

    public boolean delete(Long id) {
        Optional<Comment> comment = commentRepository.findById(id);
        if (comment.isPresent()){
            commentRepository.delete(comment.get());
            return true;
        }else {
            return false;
        }
    }
    @Transactional
    public Comment update(Long id,CommentDto commentDto) {
       Comment comment = commentRepository.findById(id)
               .orElseThrow(()->new RuntimeException("존재하지 않습니다."));
       log.info("=={}",comment.getCommentContent());
       Comment updateComment = comment.update(commentDto);
        log.info("=={}",updateComment.getCommentContent());
       return updateComment;
    }
}
