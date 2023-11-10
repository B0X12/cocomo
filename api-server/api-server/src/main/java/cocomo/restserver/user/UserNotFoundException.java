package cocomo.restserver.user;

/*
 * HTTP Status Code
 * 2__ -> OK
 * 4__ -> Client
 * 5__ -> Server
 */

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message)
    {
        super(message);
    }

}

