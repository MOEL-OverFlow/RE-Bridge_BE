package overflow.rebridge.domain.educationprogram;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EducationProgramRepository extends JpaRepository<EducationProgram, Long> {
}
