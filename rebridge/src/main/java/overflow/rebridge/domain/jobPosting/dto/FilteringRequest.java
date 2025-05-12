package overflow.rebridge.domain.jobPosting.dto;

public record FilteringRequest(
        String field,
        String jobType,
        String industryType,
        String nation,
        String experience,
        String koreanSkillLevel
) {
}
