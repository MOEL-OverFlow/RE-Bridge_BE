package overflow.rebridge.domain.jobPosting;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import overflow.rebridge.domain.jobPosting.dto.JobPostingResponse;
import overflow.rebridge.global.security.LoginUser;

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
    public ResponseEntity<List<JobPostingResponse>> getAll(
            @AuthenticationPrincipal LoginUser loginUser
    ) {
        return ResponseEntity.ok(jobPostingService.findAll(loginUser.memberId()));
    }

}
