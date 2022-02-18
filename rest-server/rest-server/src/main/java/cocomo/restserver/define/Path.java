package cocomo.restserver.define;

import cocomo.restserver.auth.qr.QRCodeService;
import lombok.Data;

@Data
public class Path {

    public static final String ADMIN = "/admin";

    // 사용자 관련
    private static final String USER = "/users";
    public static final String LOGIN = USER + "/login";
    public static final String SIGNUP = USER + "/signup";

    // 스크린락 실행
    private static final String SCREENLOCK = "/screenlock";

    // 인증 수행
    private static final String AUTH = "/auth";
    public static final String AUTH_OTP = "/otp";
    public static final String AUTH_QR = "/qr";
    public static final String AUTH_FINGERPRINT = "/fingerprint";

    // 경로
    public static final String PATH_QR = QRCodeService.class.getResource("").getPath();

}