package qrcodeapi;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class Controller {

    @GetMapping("/health")
    @ResponseStatus(HttpStatus.OK)
    public void health() {
    }

    @GetMapping("/qrcode")
    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    public void qrcode() {
    }
}
