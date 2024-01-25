package com.kj.noticeComment.dto;

import com.kj.member.entity.Member;
import com.kj.notice.dto.NoticeDto;
import com.kj.notice.entity.Notice;
import com.kj.noticeComment.entity.Comment;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private Long id;
    private String commentContent;
    private int depth;
    private LocalDateTime commentDate;

    public static Comment toEntity(CommentDto commentDto, Member member, Notice notice, Comment parent, List<Comment> commentList) {
        return Comment.builder()
                .commentDate(LocalDateTime.now())
                .commentContent(commentDto.getCommentContent())
                .depth(commentDto.getDepth())
                .writer(member)
                .parent(parent)
                .children(commentList)
                .notice(notice)
                .build();
    }

}
