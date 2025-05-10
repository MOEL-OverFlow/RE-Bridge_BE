package overflow.rebridge.global.error;

import org.springframework.http.HttpStatus;

public record ErrorResponse(HttpStatus status, String message) {

}
