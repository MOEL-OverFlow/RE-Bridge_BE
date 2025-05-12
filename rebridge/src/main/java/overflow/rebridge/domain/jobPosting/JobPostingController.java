package overflow.rebridge.domain.jobPosting;

import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "수동 크롤링")
    public ResponseEntity<String> triggerCrawl() {
        jobPostingService.triggerCrawl();
        return ResponseEntity.ok("크롤링 완료");
    }

    @GetMapping
    @Operation(summary = "모든 채용 공고 가져오기")
    public ResponseEntity<List<JobPostingResponse>> getAll(
            @AuthenticationPrincipal LoginUser loginUser
    ) {
        return ResponseEntity.ok(jobPostingService.findAll(loginUser.memberId()));
    }

    @GetMapping("/recommend")
    @Operation(summary = "추천 채용 공고 불러오기")
    public ResponseEntity<List<JobPostingResponse>> recommend(
            @AuthenticationPrincipal LoginUser loginUser
    ){
        return ResponseEntity.ok(jobPostingService.recommend(loginUser.memberId()));
    }

}
