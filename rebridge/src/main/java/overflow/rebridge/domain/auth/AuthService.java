package overflow.rebridge.domain.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import overflow.rebridge.domain.auth.dto.FindIdRequest;
import overflow.rebridge.domain.auth.dto.SignupRequest;
import overflow.rebridge.domain.image.ImageService;
import overflow.rebridge.domain.member.Member;
import overflow.rebridge.domain.member.MemberRepository;
import overflow.rebridge.domain.member.Nation;
import overflow.rebridge.domain.member.Role;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final ImageService imageService;

    public void signup(SignupRequest request) {
        Optional<Member> optionalMember = memberRepository.findByEmail(request.email());
        String encodedPassword = passwordEncoder.encode(request.password());

        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();

            if (member.getRole() != Role.GUEST) {
                throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
            }

            // 외국인등록번호 중복 검사 (GUEST여도 다른 사람이 이미 썼을 수 있음)
            Optional<Member> foreignerNumberConflict = memberRepository.findByForeignerNumber(request.foreignerNumber());
            if (foreignerNumberConflict.isPresent() && !foreignerNumberConflict.get().getEmail().equals(request.email())) {
                throw new IllegalArgumentException("이미 존재하는 외국인 등록번호입니다.");
            }

            // GUEST → MEMBER 정보 업데이트
            member.updateInfo(request, encodedPassword);
            memberRepository.save(member);
            imageService.saveImage(request.image(), member);
            return;
        }

        // 신규 회원가입 (이메일도 없음)
        if (memberRepository.findByForeignerNumber(request.foreignerNumber()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 외국인 등록번호입니다.");
        }

        Member member = new Member(request, encodedPassword);
        memberRepository.save(member);
        imageService.saveImage(request.image(), member);
    }

    public String findId(FindIdRequest request) {
        Member member = memberRepository.findByForeignerNumberAndNameAndNationAndBirthDate(
                request.foreignerNumber(),
                request.name(),
                Nation.valueOf(request.nation()),
                request.birthDate()
        ).orElseThrow(() -> new IllegalArgumentException("일치하는 회원 정보를 찾을 수 없습니다."));

        return member.getEmail();
    }
}