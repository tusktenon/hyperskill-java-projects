package qrcodeapi;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.awt.image.BufferedImage;

@RestController
@RequestMapping("/api")
public class Controller {

    private static final BufferedImage BLANK;

    static {
        BLANK = new BufferedImage(250, 250, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = BLANK.createGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 250, 250);
    }

    @GetMapping("/health")
    @ResponseStatus(HttpStatus.OK)
    public void health() {
    }

    @GetMapping(path = "/qrcode")
    public ResponseEntity<BufferedImage> qrcode() {
        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(BLANK);
    }
}
