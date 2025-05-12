package overflow.rebridge.domain.bookmark;

import org.springframework.data.jpa.repository.JpaRepository;
import overflow.rebridge.domain.jobPosting.JobPosting;
import overflow.rebridge.domain.member.Member;

import java.util.List;
import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    boolean existsByMemberAndJobPosting(Member member, JobPosting jobPosting);

    Optional<Bookmark> findByMemberAndJobPostingId(Member member, Long jobPostingId);

    List<Bookmark> findAllByMember(Member member);
}
