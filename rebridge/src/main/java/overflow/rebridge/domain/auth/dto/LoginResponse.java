package overflow.rebridge.domain.auth.dto;

public record LoginResponse(
        String accessToken,
        String refreshToken
) {
}
