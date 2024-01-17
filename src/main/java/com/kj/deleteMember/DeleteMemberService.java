package com.kj.deleteMember;

import com.kj.deleteMember.repository.DeleteMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteMemberService {
    private final DeleteMemberRepository deleteMemberRepository;

    public void deleteMember(){}
}
