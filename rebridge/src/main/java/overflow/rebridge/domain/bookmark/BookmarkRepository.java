package overflow.rebridge.domain.bookmark;

import org.springframework.data.jpa.repository.JpaRepository;
import overflow.rebridge.domain.jobPosting.JobPosting;

import java.util.List;
import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    boolean existsByMemberIdAndJobPosting(Long memberId, JobPosting jobPosting);

    Optional<Bookmark> findByMemberIdAndJobPostingId(Long memberId, Long jobPostingId);

    List<Bookmark> findAllByMemberId(Long memberId);
}
