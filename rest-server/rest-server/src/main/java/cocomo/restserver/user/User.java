package cocomo.restserver.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
//@JsonIgnoreProperties(value={"passwd", "joinDate"})
//@JsonFilter("UserInfo") // w/o Hateoas (클라이언트에게 uri 정보 제공)
public class User {

    // DTO
    @Id
    @GeneratedValue // 1번부터 자등으로 ++되어 들어감
    private Integer id;
    private String userId;
    private String passwd; // 외부에 노출 안되는 데이터

    @Size(min = 3, message = "이름은 2글자 이상 입력해주세요.")
    private String userName;
    private String email;
    private String phone;

    @Past private Date joinDate; // 과거 데이터로만

}
