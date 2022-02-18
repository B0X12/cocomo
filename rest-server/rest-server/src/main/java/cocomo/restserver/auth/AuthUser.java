package cocomo.restserver.auth;

import cocomo.restserver.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AuthUser {

    @Id private Integer id;
    @Column(unique = true) private String userId;
    private String otpKey;
    private String otpCode;
    private int authQrResult;
    private int authFingerResult;
    private int executeScreenlock;

}
