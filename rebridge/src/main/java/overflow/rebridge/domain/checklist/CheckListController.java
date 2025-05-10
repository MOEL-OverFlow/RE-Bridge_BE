package overflow.rebridge.domain.checklist;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import overflow.rebridge.domain.member.Member;

@RestController
@RequestMapping("/checklists")
public class CheckListController {

    @Autowired
    CheckListService checkListService;

    @Operation(summary = "체크리스트 전체 상태 반환")
    @GetMapping
    public ResponseEntity<CheckListStatusResponse> getCheckList(@RequestParam Long memberId) {
        return ResponseEntity.ok(checkListService.getStatus(memberId));
    }

//    @Operation(summary = "보험 체크리스트 상태 갱신")
//    @PostMapping("/insurances")
//    public ResponseEntity<Object> setCheckList() {
//        return ResponseEntity.ok(checkListService.setStatus());
//    }
//
//    @Operation(summary = "서류 체크리스트 상태 갱신")
//    @PostMapping("/documents")
//    public ResponseEntity<Object> setCheckList() {
//        return ResponseEntity.ok(checkListService.setStatus());
//    }
//
//    @Operation(summary = "프로그램 체크리스트 상태 갱신")
//    @PostMapping("/programs")
//    public ResponseEntity<Object> setCheckList() {
//        return ResponseEntity.ok(checkListService.setStatus());
//    }
}
