package com.kj.deleteMember;

import com.kj.deleteMember.entity.DeleteMember;
import com.kj.deleteMember.repository.DeleteMemberRepository;
import com.kj.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteMemberService {
    private final DeleteMemberRepository deleteMemberRepository;

    public Page<DeleteMember> findByDeleteMember(int page) {
        Pageable pageable = PageRequest.of(page,5, Sort.by(Sort.Direction.DESC,"deleteDate"));
        Page<DeleteMember> memberList = deleteMemberRepository.findByDeleteMember(pageable);
        return memberList;
    }

    public Page<DeleteMember> findAllSearchPageDeleteMember(String category, String keyword,int page) {
        Pageable pageable = PageRequest.of(page, 5, Sort.by(Sort.Direction.DESC, "deleteDate"));
        if (category.equals("userId")) {
            Page<DeleteMember> memberList = deleteMemberRepository.findByDeleteMemberUserId(keyword, pageable);
            return memberList;
        } else if(category.equals("userName")) {
            Page<DeleteMember> memberList = deleteMemberRepository.findByDeleteMemberUserName(keyword, pageable);
            return memberList;
        }else if(category.equals("nickName")) {
            Page<DeleteMember> memberList = deleteMemberRepository.findByDeleteMemberNickName(keyword, pageable);
            return memberList;
        } else {
            Page<DeleteMember> memberList = deleteMemberRepository.findByDeleteAllCategory(keyword, pageable);
            return memberList;
        }
    }
}
