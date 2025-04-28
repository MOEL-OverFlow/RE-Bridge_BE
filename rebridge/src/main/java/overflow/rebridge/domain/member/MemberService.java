package overflow.rebridge.domain.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    @Autowired
    MemberRepository memberRepository;
}
