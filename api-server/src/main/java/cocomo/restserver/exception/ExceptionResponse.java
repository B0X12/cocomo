package cocomo.restserver.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor // default Constructor
public class ExceptionResponse {

    // 에러가 발생하면 ExceptionResponse의 내용을 찍어줌
    private Date timestamp;
    private String message;
    private String details;

}
