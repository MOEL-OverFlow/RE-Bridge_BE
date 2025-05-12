package overflow.rebridge.domain.member;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import overflow.rebridge.domain.member.dto.*;
import overflow.rebridge.global.security.LoginUser;

@RestController
@RequestMapping("/me")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/info")
    @Operation(summary = "사용자 정보 가져오기")
    public mypageResponse getMyPage(@AuthenticationPrincipal LoginUser loginUser) {
        return memberService.getMyInfo(loginUser.memberId());
    }

    @PatchMapping("/name")
    @Operation(summary = "사용자 이름 수정")
    public void updateName(@RequestBody UpdateNameRequest request, @AuthenticationPrincipal LoginUser loginUser) {
        memberService.updateName(loginUser.memberId(), request.name());
    }

    @PatchMapping("/birth-date")
    @Operation(summary = "사용자 생년월일 수정")
    public void updateBirthDate(@RequestBody UpdateBirthDateRequest request, @AuthenticationPrincipal LoginUser loginUser) {
        memberService.updateBirthDate(loginUser.memberId(), request.birthDate());
    }

    @PatchMapping("/nation")
    @Operation(summary = "사용자 국가 수정")
    public void updateNation(@RequestBody UpdateNationRequest request, @AuthenticationPrincipal LoginUser loginUser) {
        memberService.updateNation(loginUser.memberId(), request.nation());
    }

    @PatchMapping("/fields")
    @Operation(summary = "사용자 관심 업종 수정")
    public void updateFields(@RequestBody UpdateFieldRequest request, @AuthenticationPrincipal LoginUser loginUser) {
        memberService.updateFields(loginUser.memberId(), request.field1(), request.field2());
    }

    @PatchMapping("/password")
    @Operation(summary = "비밀번호 수정")
    public void updatePassword(@RequestBody UpdatePasswordRequest request, @AuthenticationPrincipal LoginUser loginUser) {
        memberService.updatePassword(loginUser.memberId(), request.newPassword());
    }

    @PatchMapping("/profileImage")
    @Operation(summary = "프로필 이미지 수정")
    public void updateProfileImage(@RequestBody UpdateImageRequest request, @AuthenticationPrincipal LoginUser loginUser) {
        memberService.updateProfileImage(loginUser.memberId(), request.newImageUrl());
    }

    @PatchMapping("/deactivate")
    @Operation(summary = "회원 탈퇴 (비활성화)")
    public void deactivateAccount(@AuthenticationPrincipal LoginUser loginUser) {
        memberService.deactivate(loginUser.memberId());
    }
}
