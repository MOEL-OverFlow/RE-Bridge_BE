package overflow.rebridge.domain.jobPosting;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import overflow.rebridge.domain.member.Nation;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/job-postings")
public class JobPostingController {

    private final JobPostingService jobPostingService;

    @PostMapping("/crawl")
    public ResponseEntity<String> triggerCrawl() {
        jobPostingService.triggerCrawl();
        return ResponseEntity.ok("크롤링 완료");
    }

    @GetMapping
    public ResponseEntity<List<JobPosting>> getAll() {
        return ResponseEntity.ok(jobPostingService.findAll());
    }

    @GetMapping("/nation/{nation}")
    public ResponseEntity<List<JobPosting>> getByNation(@PathVariable Nation nation) {
        return ResponseEntity.ok(jobPostingService.findByNation(nation));
    }

    @GetMapping("/industry/{industryType}")
    public ResponseEntity<List<JobPosting>> getByIndustry(@PathVariable IndustryType industryType) {
        return ResponseEntity.ok(jobPostingService.findByIndustryType(industryType));
    }

    @GetMapping("/field/{field}")
    public ResponseEntity<List<JobPosting>> getByField(@PathVariable Field field) {
        return ResponseEntity.ok(jobPostingService.findByField(field));
    }

    @GetMapping("/job-type/{jobType}")
    public ResponseEntity<List<JobPosting>> getByJobType(@PathVariable JobType jobType) {
        return ResponseEntity.ok(jobPostingService.findByJobType(jobType));
    }
}
