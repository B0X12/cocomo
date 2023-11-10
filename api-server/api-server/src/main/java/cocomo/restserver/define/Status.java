package cocomo.restserver.define;

import lombok.Data;

@Data
public class Status {

    // AUTH USER
    public static int AUTH_NOTHING = 774;
    public static int AUTH_FAILED = 775;
    public static int AUTH_ERROR = 776;
    public static int AUTH_SUCCESS = 777;

    // AUTH
    public static int OTP_CREATED = 2001;
    public static int QR_CREATED = 2002;

    // AUTH STATUS
    public static final long LIMIT_TIME = 30000;

}
