package overflow.rebridge.domain.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class MemberService {

    @Autowired
    MemberRepository memberRepository;
    public Member findMemberById(Long memberId) {
        return memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new NoSuchElementException("회원이 존재하지 않습니다."));
    }
}
