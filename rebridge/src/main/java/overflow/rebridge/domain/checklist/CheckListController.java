package overflow.rebridge.domain.checklist;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/checklists")
public class CheckListController {

    @Autowired
    CheckListService checkListService;

    @Operation(summary = "체크리스트 상태 반환")
    @GetMapping("/{id}")
    public ResponseEntity<Object> getCheckList(@PathVariable Long id) {
        return ResponseEntity.ok(checkListService.getStatus(id));
    }

    @Operation(summary = "체크리스트 상태 갱신")
    @PostMapping("/{id}")
    public ResponseEntity<Object> setCheckList() {
        return ResponseEntity.ok(checkListService.setStatus());
    }
}
