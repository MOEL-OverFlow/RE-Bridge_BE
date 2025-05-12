package overflow.rebridge.domain.bookmark;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import overflow.rebridge.domain.jobPosting.JobPosting;
import overflow.rebridge.domain.jobPosting.JobPostingService;
import overflow.rebridge.domain.member.Member;
import overflow.rebridge.domain.member.MemberService;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final JobPostingService jobPostingService;
    private final MemberService memberService;

    @Transactional
    public void addBookmark(Long memberId, Long jobPostingId) {
        JobPosting jobPosting = jobPostingService.findJobPostingById(jobPostingId);
        Member member = memberService.findMemberById(memberId);
        if (bookmarkRepository.existsByMemberAndJobPosting(member, jobPosting)) {
            throw new IllegalStateException("이미 북마크된 채용공고입니다.");
        }

        Bookmark bookmark = Bookmark.ofProxy(memberId, jobPosting); // proxy 방식
        bookmarkRepository.save(bookmark);
    }

    @Transactional
    public void removeBookmark(Long memberId, Long jobPostingId) {
        Member member = memberService.findMemberById(memberId);
        Bookmark bookmark = bookmarkRepository.findByMemberAndJobPostingId(member, jobPostingId)
                .orElseThrow(() -> new NoSuchElementException("북마크가 존재하지 않습니다."));

        bookmarkRepository.delete(bookmark);
    }

    @Transactional(readOnly = true)
    public List<JobPosting> getMyBookmarks(Long memberId) {
        Member member = memberService.findMemberById(memberId);
        return bookmarkRepository.findAllByMember(member)
                .stream()
                .map(Bookmark::getJobPosting)
                .toList();
    }
}
