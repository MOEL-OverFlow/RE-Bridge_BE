package overflow.rebridge.domain.auth;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import overflow.rebridge.domain.member.Member;
import overflow.rebridge.domain.member.MemberRepository;
import overflow.rebridge.domain.member.Role;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GoogleOAuthService {

    private final MemberRepository memberRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final String GOOGLE_USERINFO_URI = "https://www.googleapis.com/oauth2/v3/userinfo";

    public Member loginWithGoogle(String accessToken) {
        // 1. 구글 사용자 정보 요청
        RestTemplate restTemplate = new RestTemplate();
        String userInfoResponse = restTemplate.getForObject(
                GOOGLE_USERINFO_URI + "?access_token=" + accessToken,
                String.class
        );

        try {
            JsonNode userInfo = objectMapper.readTree(userInfoResponse);
            String email = userInfo.get("email").asText();
            String name = userInfo.get("name").asText();

            // 2. DB에서 회원 조회 또는 생성
            Optional<Member> optionalMember = memberRepository.findByEmail(email);
            Member member = optionalMember.orElseGet(() -> {
                Member newMember = new Member(name, email, Role.GUEST);
                return memberRepository.save(newMember);
            });

            // 추후 GUEST면 추가 정보 입력 유도 가능
            return member;

        } catch (Exception e) {
            throw new RuntimeException("Google 사용자 정보 파싱 실패", e);
        }
    }
}

