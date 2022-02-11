package cocomo.restserver.user;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@AllArgsConstructor
//@JsonIgnoreProperties(value={"passwd", "joinDate"})
@JsonFilter("UserInfo")
public class User {

    // DTO
    private Integer id;
    String passwd; // 외부에 노출 안되는 데이터

    @Size(min = 3, message = "이름은 2글자 이상 입력해주세요.")
    private String name;
    private String email;

    @Past private Date joinDate; // 과거 데이터로만

}
