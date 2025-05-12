package overflow.rebridge.domain.jobPosting;

import jakarta.persistence.*;
import lombok.Getter;
import overflow.rebridge.domain.member.Nation;

@Getter
@Entity
@Table(name = "job_posting")
public class JobPosting{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "job_posting_id")
    private Long id;

    @Column(name = "company_name", nullable = false)
    private String companyName;

    @Column(name = "job_title", nullable = false)
    private String jobTitle; // 분야/직종

    @Column(name = "detail_url", nullable = false)
    private String detailUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "industry_type", nullable = false)
    private IndustryType industryType;

    @Enumerated(EnumType.STRING)
    @Column(name = "nation", nullable = false)
    private Nation nation;

    @Column(name = "recruitment_count")
    private int recruitmentCount;

    @Enumerated(EnumType.STRING)
    @Column(name = "experience", nullable = false)
    private ExperienceType experience;

    @Enumerated(EnumType.STRING)
    @Column(name = "korean_skill_level", nullable = false)
    private KoreanSkillLevel koreanSkillLevel;

    @Column(name = "deadline", nullable = false)
    private String deadline; // or LocalDate if convertible

    protected JobPosting() {
    }

}
