package overflow.rebridge.domain.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import overflow.rebridge.domain.auth.dto.FindIdRequest;
import overflow.rebridge.domain.auth.dto.SignupRequest;
import overflow.rebridge.domain.image.Image;
import overflow.rebridge.domain.image.ImageService;
import overflow.rebridge.domain.member.Member;
import overflow.rebridge.domain.member.MemberRepository;
import overflow.rebridge.domain.nation.NationRepository;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final NationRepository nationRepository;
    private final PasswordEncoder passwordEncoder;
    private final ImageService imageService;

    public void signup(SignupRequest request) {
        if (memberRepository.findByEmail(request.email()).isPresent()) {
            ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("이미 존재하는 이메일입니다.");
            return;
        }

        String encodedPassword = passwordEncoder.encode(request.password());
        Member member = new Member(request);
        imageService.saveImage(request.image(), member);
        memberRepository.save(member);

        ResponseEntity.ok("회원가입 성공");
    }

    public String findid(FindIdRequest request) {
        Member member = memberRepository.findByForeignerNumberAndNameAndNationAndBirthDate(
                request.foreignerNumber(),
                request.name(),
                request.nation(),
                request.birthDate()
        ).orElseThrow(() -> new IllegalArgumentException("일치하는 회원 정보를 찾을 수 없습니다."));

        return member.getEmail();
    }
}