package cocomo.restserver.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthUser {

    @Id private Integer id;
    private String otpCode;

}
