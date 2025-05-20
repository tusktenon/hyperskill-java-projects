package qrcodeapi;

import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public enum CorrectionLevel {

    L(ErrorCorrectionLevel.L),
    M(ErrorCorrectionLevel.M),
    Q(ErrorCorrectionLevel.Q),
    H(ErrorCorrectionLevel.H);

    private final ErrorCorrectionLevel level;

    CorrectionLevel(ErrorCorrectionLevel level) {
        this.level = level;
    }

    ErrorCorrectionLevel level() {
        return level;
    }
}
