package overflow.rebridge.global.s3;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/images")
public class S3Controller {

    private final S3Service s3Service;

    @PostMapping("/upload")
    @Operation(summary = "이미지 업로드")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        String imageUrl = s3Service.uploadImage(file);
        return ResponseEntity.ok(imageUrl);
    }
}

