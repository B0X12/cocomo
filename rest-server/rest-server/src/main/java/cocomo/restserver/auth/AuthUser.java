package cocomo.restserver.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AuthUser {

    @Id private Integer id;
    private String otpKey;
    private String otpCode;

}
