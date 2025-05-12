package overflow.rebridge.domain.bookmark;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import overflow.rebridge.domain.jobPosting.JobPosting;
import overflow.rebridge.global.security.LoginUser;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bookmarks")
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @PostMapping("/{jobPostingId}")
    @Operation(summary = "북마크 추가")
    public ResponseEntity<?> addBookmark(
            @AuthenticationPrincipal LoginUser loginUser,
            @PathVariable Long jobPostingId) {
        bookmarkService.addBookmark(loginUser.memberId(), jobPostingId);
        return ResponseEntity.ok("북마크 추가 완료");
    }

    @DeleteMapping("/{jobPostingId}")
    @Operation(summary = "북마크 삭제")
    public ResponseEntity<?> removeBookmark(
            @AuthenticationPrincipal LoginUser loginUser,
            @PathVariable Long jobPostingId) {
        bookmarkService.removeBookmark(loginUser.memberId(), jobPostingId);
        return ResponseEntity.ok("북마크 삭제 완료");
    }

    @GetMapping
    @Operation(summary = "로그인한 사용자의 북마크 가져오기")
    public ResponseEntity<List<JobPosting>> getMyBookmarks(
            @AuthenticationPrincipal LoginUser loginUser) {
        return ResponseEntity.ok(bookmarkService.getMyBookmarks(loginUser.memberId()));
    }
}
