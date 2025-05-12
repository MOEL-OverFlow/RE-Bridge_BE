package overflow.rebridge.domain.member.dto;

import java.time.LocalDate;

public record mypageResponse(
        String email,
        String image,
        String name,
        LocalDate birthDate,
        String nation,
        String field1,
        String field2
) {
}
