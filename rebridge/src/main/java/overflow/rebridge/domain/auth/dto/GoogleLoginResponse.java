package overflow.rebridge.domain.auth.dto;

public record GoogleLoginResponse(
        String name,
        String email,
        String role,
        String loginType,
        String accessToken,
        String refreshToken) {
}
