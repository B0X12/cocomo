package cocomo.restserver.exception;

import cocomo.restserver.user.UserNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@RestController
@ControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class) // Exception에 대해서 이렇게 처리함
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request)
    {
        // timestamp, message, details
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(), ex.getMessage()
                        , request.getDescription(false));

        return new ResponseEntity(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserNotFoundException.class) // UserNotFoundException에 대해서는 이렇게 처리함
    public final ResponseEntity<Object> handleUserNotFoundException(Exception ex, WebRequest request)
    {
        // timestamp, message, details
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(), ex.getMessage()
                        , request.getDescription(false));

        return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex
            , HttpHeaders headers, HttpStatus status, WebRequest request)
    {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date()
                , "Validation Failed", ex.getBindingResult().toString());

        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

}
