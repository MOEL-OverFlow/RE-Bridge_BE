package overflow.rebridge.domain.jobPosting;

import org.springframework.data.jpa.repository.JpaRepository;
import overflow.rebridge.domain.member.Nation;

import java.util.List;

public interface JobPostingRepository extends JpaRepository<JobPosting, Long> {
    boolean existsByDetailUrl(String detailUrl);

    List<JobPosting> findByNation(Nation nation);

    List<JobPosting> findByIndustryType(IndustryType industryType);

    List<JobPosting> findByField(Field field);

    List<JobPosting> findByJobType(JobType jobType);
}
