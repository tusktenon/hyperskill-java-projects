package qrcodeapi;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api")
public class Controller {

    public static final int MIN_SIZE = 150;
    public static final int MAX_SIZE = 350;
    public static final List<String> SUPPORTED_TYPES = List.of("png", "jpeg", "gif");

    public static final String ILLEGAL_SIZE_MESSAGE =
            "Image size must be between %d and %d pixels".formatted(MIN_SIZE, MAX_SIZE);

    public static final String INVALID_CORRECTION_MESSAGE =
            "Permitted error correction levels are %s".formatted(String.join(", ",
                    Arrays.stream(CorrectionLevel.values())
                            .map(CorrectionLevel::toString)
                            .toList()));

    public static final String UNSUPPORTED_TYPE_MESSAGE =
            "Only %s and %s image types are supported".formatted(
                    String.join(", ", SUPPORTED_TYPES.subList(0, SUPPORTED_TYPES.size() - 1)),
                    SUPPORTED_TYPES.getLast());

    @GetMapping("/health")
    @ResponseStatus(HttpStatus.OK)
    public void health() {
    }

    @GetMapping("/qrcode")
    public ResponseEntity<BufferedImage> qrcode(
            @RequestParam String contents,
            @RequestParam(defaultValue = "250") int size,
            @RequestParam(defaultValue = "L") String correction,
            @RequestParam(defaultValue = "png") String type
    ) {
        if (contents.isBlank()) {
            throw new RequestException("Contents cannot be null or blank");
        }
        if (size < MIN_SIZE || size > MAX_SIZE) {
            throw new RequestException(ILLEGAL_SIZE_MESSAGE);
        }
        CorrectionLevel correctionLevel;
        try {
            correctionLevel = CorrectionLevel.valueOf(correction);
        } catch (IllegalArgumentException e) {
            throw new RequestException(INVALID_CORRECTION_MESSAGE);
        }
        if (!SUPPORTED_TYPES.contains(type)) {
            throw new RequestException(UNSUPPORTED_TYPE_MESSAGE);
        }
        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType("image/" + type))
                .body(QRCodeService.generateImage(contents, correctionLevel.level(), size));
    }

    @ExceptionHandler(RequestException.class)
    public ResponseEntity<ErrorMessage> handleRequestException(RequestException e) {
        return ResponseEntity.badRequest().body(new ErrorMessage(e.getMessage()));
    }
}
