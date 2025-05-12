package overflow.rebridge.domain.member;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import overflow.rebridge.domain.member.dto.mypageResponse;
import overflow.rebridge.global.security.LoginUser;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/info")
    @Operation(summary = "사용자 정보 가져오기")
    public mypageResponse getMyPage(@AuthenticationPrincipal LoginUser loginUser) {
        return memberService.getMyInfo(loginUser.memberId());
    }
}

