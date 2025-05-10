package overflow.rebridge.global.security;

public record LoginUser(
        Long memberId,
        String email,
        String role
) {
}
