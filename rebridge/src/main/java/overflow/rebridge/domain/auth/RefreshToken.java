package overflow.rebridge.domain.auth;

import jakarta.persistence.*;
import lombok.*;
import overflow.rebridge.domain.member.Member;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false, unique = true)
    private Member member;

    @Column(nullable = false, length = 500)
    private String token;

    public void update(String newToken) {
        this.token = newToken;
    }
}
