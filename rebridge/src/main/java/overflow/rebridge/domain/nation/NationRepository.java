package overflow.rebridge.domain.nation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NationRepository extends JpaRepository<Nation, Long> {
}
