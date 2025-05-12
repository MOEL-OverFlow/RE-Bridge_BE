package overflow.rebridge.domain.jobPosting;

import jakarta.persistence.*;
import lombok.Getter;
import overflow.rebridge.domain.member.Nation;

@Getter
@Entity
@Table(name = "job_posting")
public class JobPosting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "job_posting_id")
    private Long id;

    @Column(name = "company_name", nullable = false)
    private String companyName; // 업체명

    @Enumerated(EnumType.STRING)
    @Column(name = "field", nullable = false)
    private Field field; // 분야

    @Enumerated(EnumType.STRING)
    @Column(name = "job_type", nullable = false)
    private JobType jobType; // 직종

    @Column(name = "detail_url", nullable = false)
    private String detailUrl; // 상세 정보

    @Enumerated(EnumType.STRING)
    @Column(name = "industry_type", nullable = false)
    private IndustryType industryType; // 업종

    @Enumerated(EnumType.STRING)
    @Column(name = "nation", nullable = false)
    private Nation nation; // 근무국가

    @Column(name = "recruitment_count")
    private int recruitmentCount; // 모집 인원

    @Enumerated(EnumType.STRING)
    @Column(name = "experience", nullable = false)
    private ExperienceType experience; // 경력

    @Enumerated(EnumType.STRING)
    @Column(name = "korean_skill_level", nullable = false)
    private KoreanSkillLevel koreanSkillLevel; // 한국어능력

    @Column(name = "deadline", nullable = false)
    private String deadline; // 마감일

    protected JobPosting() {
    }

    public static JobPosting of(
            String companyName,
            String detailUrl,
            IndustryType industryType,
            Nation nation,
            int recruitmentCount,
            ExperienceType experience,
            KoreanSkillLevel koreanSkillLevel,
            String deadline,
            Field field,
            JobType jobType
    ) {
        JobPosting jobPosting = new JobPosting();
        jobPosting.companyName = companyName;
        jobPosting.detailUrl = detailUrl;
        jobPosting.industryType = industryType;
        jobPosting.nation = nation;
        jobPosting.recruitmentCount = recruitmentCount;
        jobPosting.experience = experience;
        jobPosting.koreanSkillLevel = koreanSkillLevel;
        jobPosting.deadline = deadline;
        jobPosting.field = field;
        jobPosting.jobType = jobType;
        return jobPosting;
    }
}
