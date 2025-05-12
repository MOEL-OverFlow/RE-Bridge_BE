package overflow.rebridge.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);

    Optional<Member> findByMemberId(Long memberId);

    Optional<Member> findByForeignerNumberAndNameAndNationAndBirthDate(
            String foreignerNumber, String name, String nation, LocalDate birthDate);
}


