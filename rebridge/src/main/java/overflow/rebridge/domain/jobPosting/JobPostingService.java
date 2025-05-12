package overflow.rebridge.domain.jobPosting;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import overflow.rebridge.domain.bookmark.BookmarkRepository;
import overflow.rebridge.domain.jobPosting.dto.JobPostingResponse;
import overflow.rebridge.domain.member.Member;
import overflow.rebridge.domain.member.MemberService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobPostingService {

    private final JobPostingRepository jobPostingRepository;
    private final JobPostingCrawlerService crawlerService;
    private final BookmarkRepository bookmarkRepository;
    private final MemberService memberService;

    public void triggerCrawl() {
        crawlerService.crawl(); // 수동 실행
    }

    public List<JobPostingResponse> findAll(Long memberId) {
        List<JobPosting> jobPostings = jobPostingRepository.findAll();
        Member member = memberService.findMemberById(memberId);
        return jobPostings.stream()
                .map(jobPosting -> toResponse(jobPosting, member))
                .collect(Collectors.toList());
    }

    public JobPosting findJobPostingById(Long id) {
        return jobPostingRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 채용공고입니다."));
    }

    private JobPostingResponse toResponse(JobPosting jobPosting, Member member) {
        boolean isBookmarked = bookmarkRepository.existsByMemberAndJobPosting(member, jobPosting);

        return new JobPostingResponse(
                jobPosting.getId(),
                jobPosting.getCompanyName(),
                jobPosting.getField().name(),
                jobPosting.getJobType().name(),
                jobPosting.getDetailUrl(),
                jobPosting.getIndustryType().name(),
                jobPosting.getNation().name(),
                jobPosting.getRecruitmentCount(),
                jobPosting.getExperience().name(),
                jobPosting.getKoreanSkillLevel().name(),
                jobPosting.getDeadline(),
                isBookmarked
        );
    }

    public List<JobPostingResponse> recommend(Long memberId) {

        Member member = memberService.findMemberById(memberId);
        List<JobPosting> primary = jobPostingRepository.findByNationAndField(member.getNation(), member.getField1());
        Collections.shuffle(primary);
        List<JobPosting> results = new ArrayList<>(primary.stream().limit(5).toList());

        if (results.size() < 5 && member.getField2() != null) {
            List<JobPosting> secondary = jobPostingRepository.findByNationAndField(member.getNation(), member.getField2());
            Collections.shuffle(secondary);
            // 중복 제거
            secondary.removeIf(job -> results.contains(job));
            results.addAll(secondary.stream().limit(5 - results.size()).toList());
        }

        return results.stream()
                .map(jobPosting -> toResponse(jobPosting, member))
                .collect(Collectors.toList());
    }

}
