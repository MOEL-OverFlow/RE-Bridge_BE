package overflow.rebridge.domain.auth.dto;

import overflow.rebridge.domain.image.Image;
import overflow.rebridge.domain.nation.Nation;

import java.time.LocalDate;

public record SignupRequest(
        String email,
        String password,
        String name,
        LocalDate birthDate,
        String foreignerNumber,
        String nation,
        String image,
        String industry1,
        String industry2
) {
}