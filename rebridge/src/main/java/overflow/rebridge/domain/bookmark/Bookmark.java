package overflow.rebridge.domain.bookmark;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.ReflectionUtils;
import overflow.rebridge.domain.jobPosting.JobPosting;
import overflow.rebridge.domain.member.Member;
import overflow.rebridge.global.entity.BaseTimeEntity;

import java.lang.reflect.Field;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "bookmark", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"member_id", "job_posting_id"})
})
public class Bookmark extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookmark_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_posting_id", nullable = false)
    private JobPosting jobPosting;

    public Bookmark(Member member, JobPosting jobPosting) {
        this.member = member;
        this.jobPosting = jobPosting;
    }

    public static Bookmark of(Member member, JobPosting jobPosting) {
        return new Bookmark(member, jobPosting);
    }

    public static Bookmark ofProxy(Long memberId, JobPosting jobPosting) {
        Member proxyMember = new Member(); // 엔티티는 protected 생성자일 경우 setter 필요
        Field memberIdField = ReflectionUtils.findField(Member.class, "memberId");
        ReflectionUtils.makeAccessible(memberIdField);
        ReflectionUtils.setField(memberIdField, proxyMember, memberId);

        Bookmark bookmark = new Bookmark();
        bookmark.member = proxyMember;
        bookmark.jobPosting = jobPosting;
        return bookmark;
    }
}
