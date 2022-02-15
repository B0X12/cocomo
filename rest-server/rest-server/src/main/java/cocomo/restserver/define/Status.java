package cocomo.restserver.define;

import lombok.Data;

@Data
public class Status {

    // AUTH USER
    public static int AUTH_NOTHING = 101;
    public static int AUTH_WAITING = 102;
    public static int AUTH_COMPLETED = 103;

    // AUTH
    public static int OTP_CREATED = 2001;
    public static int QR_CREATED = 2002;

}
