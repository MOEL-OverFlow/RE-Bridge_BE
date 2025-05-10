package overflow.rebridge.global.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.DisabledException;
import overflow.rebridge.domain.member.Member;
import overflow.rebridge.domain.member.MemberRepository;
import overflow.rebridge.domain.member.Role;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + email));

        // 탈퇴 회원은 로그인 불가
        if (member.getRole() == Role.DEACTIVATED) {
            throw new DisabledException("탈퇴한 사용자입니다.");
        }

        // Spring Security가 인식할 수 있는 UserDetails 객체 반환
        return new User(
                member.getEmail(),                   // username
                member.getPassword(),                // password
                Collections.singletonList(           // 권한 목록
                        new SimpleGrantedAuthority("ROLE_" + member.getRole().name())
                )
        );
    }
}
