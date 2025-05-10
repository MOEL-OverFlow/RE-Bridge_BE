package overflow.rebridge.domain.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import overflow.rebridge.domain.member.Member;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    public void saveOrUpdate(Member member, String token) {
        refreshTokenRepository.findByMember(member)
                .ifPresentOrElse(
                        existing -> {
                            existing.update(token);
                            refreshTokenRepository.save(existing);
                        },
                        () -> {
                            RefreshToken newToken = RefreshToken.builder()
                                    .member(member)
                                    .token(token)
                                    .build();
                            refreshTokenRepository.save(newToken);
                        }
                );
    }
}