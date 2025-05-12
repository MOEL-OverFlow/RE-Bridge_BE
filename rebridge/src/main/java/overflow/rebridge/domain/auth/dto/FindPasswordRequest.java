package overflow.rebridge.domain.auth.dto;

import java.time.LocalDate;

public record FindPasswordRequest(
        String email,
        String name,
        String foreignerNumber,
        LocalDate birthDate
) {}
