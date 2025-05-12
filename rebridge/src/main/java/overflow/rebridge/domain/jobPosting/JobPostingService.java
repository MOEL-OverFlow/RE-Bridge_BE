package overflow.rebridge.domain.jobPosting;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import overflow.rebridge.domain.member.Nation;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobPostingService {

    private final JobPostingRepository jobPostingRepository;
    private final JobPostingCrawlerService crawlerService;

    public void triggerCrawl() {
        crawlerService.crawl(); // 수동 실행
    }

    public List<JobPosting> findAll() {
        return jobPostingRepository.findAll();
    }

    public List<JobPosting> findByNation(Nation nation) {
        return jobPostingRepository.findByNation(nation);
    }

    public List<JobPosting> findByIndustryType(IndustryType industryType) {
        return jobPostingRepository.findByIndustryType(industryType);
    }

    public List<JobPosting> findByField(Field field) {
        return jobPostingRepository.findByField(field);
    }

    public List<JobPosting> findByJobType(JobType jobType) {
        return jobPostingRepository.findByJobType(jobType);
    }
}
