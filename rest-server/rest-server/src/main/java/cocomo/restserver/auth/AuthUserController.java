package cocomo.restserver.auth;

import cocomo.restserver.auth.otp.OtpController;
import cocomo.restserver.auth.qr.QRCode_CreateAndSave;
import cocomo.restserver.auth.qr.QRCode_Data;
import cocomo.restserver.user.User;
import cocomo.restserver.user.UserNotFoundException;
import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import cocomo.restserver.define.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
@RequestMapping("/auth")
public class AuthUserController {

    @Autowired
    private AuthUserDaoService service;

    @GetMapping("/users")
    public List<AuthUser> retrieveAllUsers() // 전체 유저 조회
    {
        return service.findAll();
    }

    @PostMapping("/users")
    public ResponseEntity<AuthUser> saveUserId(AuthUser authUser)
    {
        AuthUser savedAuthUser = service.save(authUser);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedAuthUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/otp/{id}")
    public AuthUser otpCreate(@PathVariable int id)
            throws InvalidKeyException, NoSuchAlgorithmException
    {
        AuthUser authUser = service.findById(id);
        if (authUser == null)
        {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        String otpCode = OtpController.createOtp();
        authUser.setOtpCode(otpCode);

        return authUser;
    }

    @GetMapping("/qr/{id}")
    public int qrCreate(@PathVariable int id) throws IOException, WriterException // 전체 유저 조회
    {
        QRCode_CreateAndSave.qrCreate(id);
        return Status.QR_CREATED;
    }

}
