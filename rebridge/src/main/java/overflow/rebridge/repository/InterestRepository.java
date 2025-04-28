package overflow.rebridge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import overflow.rebridge.domain.Interest;

@Repository
public interface InterestRepository extends JpaRepository<Interest, Long> {
}
