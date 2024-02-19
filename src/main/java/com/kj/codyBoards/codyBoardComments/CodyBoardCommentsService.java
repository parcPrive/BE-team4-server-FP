package com.kj.codyBoards.codyBoardComments;

import com.kj.codyBoards.codyBoard.Repository.CodyBoardRepository;
import com.kj.codyBoards.codyBoard.entiry.CodyBoard;
import com.kj.codyBoards.codyBoardComments.dto.CodyBoardCommentInputDto;
import com.kj.codyBoards.codyBoardComments.dto.CodyBoardCommentReturnDto;
import com.kj.codyBoards.codyBoardComments.dto.CodyBoardReplyInputDto;
import com.kj.codyBoards.codyBoardComments.dto.CodyBoardReplyReturnDto;
import com.kj.codyBoards.codyBoardComments.entity.CodyBoardComment;
import com.kj.codyBoards.codyBoardComments.repository.CodyBoardCommentsRepository;
import com.kj.member.entity.Member;
import com.kj.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CodyBoardCommentsService {
    private final CodyBoardCommentsRepository codyBoardCommentsRepository;
    private final CodyBoardRepository codyBoardRepository;
    private final MemberRepository memberRepository;

    // 댓글 추가
    @Transactional
    public CodyBoardCommentReturnDto insertCodyBaordComments(CodyBoardCommentInputDto codyboardCommentInputDto){
        log.info("유저 네임안에는??? ====>>> {} ",codyboardCommentInputDto);
        Optional<CodyBoard> codyBoard = codyBoardRepository.findById(codyboardCommentInputDto.getCodyBoardId());
        Optional<Member> member = memberRepository.findByNickName(codyboardCommentInputDto.getUserName());
        int sortNum = Math.toIntExact(codyBoardCommentsRepository.commentMax());

        if(codyBoard.isPresent() && member.isPresent()){
            CodyBoardComment insertCodyBoardComment = new CodyBoardComment(codyboardCommentInputDto, sortNum, codyBoard.get(), member.get());
            CodyBoardComment resultCodyComment = codyBoardCommentsRepository.save(insertCodyBoardComment);
            CodyBoardCommentReturnDto codyBoardCommentReturnDto = new CodyBoardCommentReturnDto(resultCodyComment);
            return codyBoardCommentReturnDto;
        }else{
            throw new RuntimeException("게시물이 존재하지않습니다.");
        }
    }

    //대댓글 추가
    @Transactional
    public CodyBoardReplyReturnDto insertCodyBoardReply(CodyBoardReplyInputDto codyBoardReplyInputDto) {
        Optional<CodyBoardComment> codyBoardComment =  codyBoardCommentsRepository.findById(codyBoardReplyInputDto.getCommentId());
        Optional<Member> member =  memberRepository.findByNickName(codyBoardReplyInputDto.getUserName());
        Optional<CodyBoard> codyBoard = codyBoardRepository.findById(codyBoardReplyInputDto.getCodyBoardId());
        if(codyBoardComment.isPresent() && member.isPresent() && codyBoard.isPresent()){
            CodyBoardComment insertCodyBoardComment = new CodyBoardComment(codyBoardReplyInputDto,codyBoardComment.get(),codyBoard.get(),member.get());
            CodyBoardComment result = codyBoardCommentsRepository.save(insertCodyBoardComment);
            CodyBoardReplyReturnDto codyBoardReplyReturnDto = new CodyBoardReplyReturnDto(result);
            return codyBoardReplyReturnDto;

        }else{
            throw new RuntimeException("회원,게시글,댓글 중 하나가 없어서 오류가 나왔다.");
        }


    }
}
