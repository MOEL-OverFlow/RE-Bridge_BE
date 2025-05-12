package overflow.rebridge.domain.jobPosting;

import org.springframework.data.jpa.repository.JpaRepository;
import overflow.rebridge.domain.member.Nation;

import java.util.List;

public interface JobPostingRepository extends JpaRepository<JobPosting, Long> {
    boolean existsByDetailUrl(String detailUrl);

    List<JobPosting> findByNationAndField(Nation nation, Field field);

    List<JobPosting> findByNation(Nation nation);
}
