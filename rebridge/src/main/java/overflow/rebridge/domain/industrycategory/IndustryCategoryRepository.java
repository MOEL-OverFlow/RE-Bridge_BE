package overflow.rebridge.domain.industrycategory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndustryCategoryRepository extends JpaRepository<IndustryCategory, Long> {
}
