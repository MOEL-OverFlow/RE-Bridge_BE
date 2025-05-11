package overflow.rebridge.domain.checklist;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import overflow.rebridge.domain.member.Member;
import overflow.rebridge.global.security.LoginUser;

@RestController
@RequestMapping("/checklists")
public class CheckListController {

    @Autowired
    CheckListService checkListService;

    @Operation(summary = "체크리스트 전체 상태 반환")
    @GetMapping
    public ResponseEntity<CheckListStatusDto> getCheckList(@AuthenticationPrincipal LoginUser loginUser) {
        Long memberId = loginUser.memberId();
        return ResponseEntity.ok(checkListService.getStatus(memberId));
    }

    @Operation(summary = "체크리스트 전체 상태 갱신")
    @PostMapping
    public ResponseEntity<Object> setCheckList(@AuthenticationPrincipal LoginUser loginUser, @RequestBody CheckListStatusDto dto) {
        Long memberId = loginUser.memberId();
        return ResponseEntity.ok(checkListService.setStatus(memberId, dto));
    }
}
