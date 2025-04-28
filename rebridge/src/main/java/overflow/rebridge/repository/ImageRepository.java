package overflow.rebridge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import overflow.rebridge.domain.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
}
