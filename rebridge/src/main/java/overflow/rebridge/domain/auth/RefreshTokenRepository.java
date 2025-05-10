package overflow.rebridge.domain.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import overflow.rebridge.domain.member.Member;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByMember(Member member);
}
