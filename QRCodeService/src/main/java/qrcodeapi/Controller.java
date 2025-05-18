package qrcodeapi;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class Controller {

    public static final int MIN_SIZE = 150;
    public static final int MAX_SIZE = 350;
    public static final List<String> SUPPORTED_TYPES = List.of("png", "jpeg", "gif");

    public static final String ILLEGAL_SIZE_MESSAGE =
            "Image size must be between %d and %d pixels".formatted(MIN_SIZE, MAX_SIZE);

    public static final String UNSUPPORTED_TYPE_MESSAGE =
            "Only %s and %s image types are supported".formatted(
                    String.join(", ", SUPPORTED_TYPES.subList(0, SUPPORTED_TYPES.size() - 1)),
                    SUPPORTED_TYPES.getLast());

    @GetMapping("/health")
    @ResponseStatus(HttpStatus.OK)
    public void health() {
    }

    @GetMapping("/qrcode")
    public ResponseEntity<?> qrcode(@RequestParam int size, @RequestParam String type) {
        if (size < MIN_SIZE || size > MAX_SIZE) {
            return ResponseEntity.badRequest().body(new ErrorMessage(ILLEGAL_SIZE_MESSAGE));
        }
        if (!SUPPORTED_TYPES.contains(type)) {
            return ResponseEntity.badRequest().body(new ErrorMessage(UNSUPPORTED_TYPE_MESSAGE));
        }
        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType("image/" + type))
                .body(QRCodeService.blankCode(size));
    }
}
