package com.kj.noticeComment.dto;

import com.kj.member.entity.Member;
import com.kj.notice.dto.NoticeDto;
import com.kj.notice.entity.Notice;
import com.kj.noticeComment.entity.Comment;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private Long id;
    private String commentContent;
    private LocalDateTime commentDate;
    private Long parentId;

    public static Comment toEntity(CommentDto commentDto, Member member, Notice notice) {
        return Comment.builder()
                .id(commentDto.getId())
                .commentDate(LocalDateTime.now())
                .commentContent(commentDto.getCommentContent())
                .writer(member)
                .notice(notice)
                .build();
    }
}
