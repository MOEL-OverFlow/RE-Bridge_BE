package overflow.rebridge.domain.auth.dto;

import java.time.LocalDate;

public record FindIdRequest(
        String foreignerNumber,
        String name,
        String nation,
        LocalDate birthDate
) {}