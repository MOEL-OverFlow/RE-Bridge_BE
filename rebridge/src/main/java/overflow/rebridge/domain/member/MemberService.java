package overflow.rebridge.domain.member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import overflow.rebridge.domain.image.Image;
import overflow.rebridge.domain.image.ImageRepository;
import overflow.rebridge.domain.image.ImageService;
import overflow.rebridge.domain.jobPosting.Field;
import overflow.rebridge.domain.member.dto.mypageResponse;

import java.time.LocalDate;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final ImageRepository imageRepository;

    public Member findMemberById(Long memberId) {
        return memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new NoSuchElementException("회원이 존재하지 않습니다."));
    }

    public mypageResponse getMyInfo(Long memberId) {
        return findMemberById(memberId).toInfo();
    }

    public void updateName(Long memberId, String name) {
        Member member = findMemberById(memberId);
        member.updateName(name);
        memberRepository.save(member);
    }

    public void updateBirthDate(Long memberId, LocalDate birthDate) {
        Member member = findMemberById(memberId);
        member.updateBirthDate(birthDate);
        memberRepository.save(member);
    }

    public void updateNation(Long memberId, String nation) {
        Member member = findMemberById(memberId);
        member.updateNation(Nation.valueOf(nation));
        memberRepository.save(member);
    }

    public void updateFields(Long memberId, String field1, String field2) {
        Member member = findMemberById(memberId);
        member.updateField(Field.valueOf(field1), Field.valueOf(field2));
        memberRepository.save(member);
    }

    public void updatePassword(Long memberId, String newPassword) {
        Member member = findMemberById(memberId);
        member.updatePassword(passwordEncoder.encode(newPassword));
        memberRepository.save(member);
    }

    public void updateProfileImage(Long memberId, String newImageUrl) {
        Member member = findMemberById(memberId);

        Image image = member.getImage();
        if (image == null) {
            image = new Image(newImageUrl, member);
            imageRepository.save(image);
        } else {
            image.updateUrl(newImageUrl);
        }
    }

}
