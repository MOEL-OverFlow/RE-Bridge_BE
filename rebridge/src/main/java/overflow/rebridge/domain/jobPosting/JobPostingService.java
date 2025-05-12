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
import lombok.extern.slf4j.Slf4j;

@Slf4j
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

    public List<List<JobPostingResponse>> findAllGroupedBy10(Long memberId) {
        List<JobPosting> jobPostings = jobPostingRepository.findAll();
        Member member = memberService.findMemberById(memberId);

        List<JobPostingResponse> allResponses = jobPostings.stream()
                .map(jobPosting -> toResponse(jobPosting, member))
                .collect(Collectors.toList());

        return groupBySize(allResponses, 10);
    }

    // 유틸 메서드
    private <T> List<List<T>> groupBySize(List<T> list, int size) {
        List<List<T>> result = new ArrayList<>();
        for (int i = 0; i < list.size(); i += size) {
            result.add(list.subList(i, Math.min(i + size, list.size())));
        }
        return result;
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
        log.info("🔍 추천 대상 회원 => ID: {}, Nation: {}, Field1: {}, Field2: {}",
                member.getMemberId(), member.getNation(), member.getField1(), member.getField2());

        List<JobPosting> primary = jobPostingRepository.findByNationAndField(member.getNation(), member.getField1());
        log.info("✅ 1차 추천 공고 개수: {}", primary.size());

        List<JobPosting> results = new ArrayList<>(primary.stream().limit(5).toList());

        if (results.size() < 5 && member.getField2() != null && member.getField2() != Field.NONE) {
            List<JobPosting> secondary = jobPostingRepository.findByNationAndField(member.getNation(), member.getField2());
            log.info("✅ 2차 추천 공고 개수: {}", secondary.size());
            secondary.removeIf(results::contains);
            results.addAll(secondary.stream().limit(5 - results.size()).toList());
        }

        if (results.size() < 5) {
            List<JobPosting> fallback = jobPostingRepository.findByNation(member.getNation());
            fallback.removeIf(results::contains);
            log.info("⚠️ 보완용 공고 개수: {}", fallback.size());
            Collections.shuffle(fallback);
            results.addAll(fallback.stream().limit(5 - results.size()).toList());
        }

        log.info("🎯 최종 추천 공고 수: {}", results.size());

        return results.stream()
                .map(jobPosting -> toResponse(jobPosting, member))
                .collect(Collectors.toList());
    }


}
