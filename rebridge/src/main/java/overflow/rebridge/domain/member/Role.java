package overflow.rebridge.domain.member;

public enum Role {
    GUEST,       // 가입만 하고 정보 미입력
    MEMBER,      // 정보 입력 완료 (활성 사용자)
    DEACTIVATED, // 탈퇴 처리된 사용자
    ADMIN        // 관리자
}
