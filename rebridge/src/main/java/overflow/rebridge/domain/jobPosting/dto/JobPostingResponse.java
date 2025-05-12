package overflow.rebridge.domain.jobPosting.dto;

public record JobPostingResponse(
        Long id,
        String companyName,
        String field,
        String jobType,
        String detailUrl,
        String industryType,
        String nation,
        int recruitmentCount,
        String experience,
        String koreanSkillLevel,
        String deadline,
        Boolean isBookmark
) {
}
