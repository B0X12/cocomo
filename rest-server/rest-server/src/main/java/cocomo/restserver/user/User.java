package cocomo.restserver.user;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class User {

    // DTO
    private Integer id;
    private String passwd;
    private String name;
    private String email;
    private Date joinDate;

}
