package overflow.rebridge.domain.bookmark;

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
    public ResponseEntity<?> addBookmark(
            @AuthenticationPrincipal LoginUser loginUser,
            @PathVariable Long jobPostingId) {
        bookmarkService.addBookmark(loginUser.memberId(), jobPostingId);
        return ResponseEntity.ok("북마크 추가 완료");
    }

    @DeleteMapping("/{jobPostingId}")
    public ResponseEntity<?> removeBookmark(
            @AuthenticationPrincipal LoginUser loginUser,
            @PathVariable Long jobPostingId) {
        bookmarkService.removeBookmark(loginUser.memberId(), jobPostingId);
        return ResponseEntity.ok("북마크 삭제 완료");
    }

    @GetMapping
    public ResponseEntity<List<JobPosting>> getMyBookmarks(
            @AuthenticationPrincipal LoginUser loginUser) {
        return ResponseEntity.ok(bookmarkService.getMyBookmarks(loginUser.memberId()));
    }
}
