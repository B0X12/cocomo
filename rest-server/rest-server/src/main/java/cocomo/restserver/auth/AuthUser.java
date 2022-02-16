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
    private String otpKey;
    private String otpCode;

}
