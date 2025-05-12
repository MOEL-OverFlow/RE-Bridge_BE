package overflow.rebridge.domain.auth.dto;

public record EmailVerifyRequest(
        String email,
        String code
) {}
