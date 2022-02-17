package cocomo.restserver.auth;

import cocomo.restserver.auth.otp.OtpController;
import cocomo.restserver.auth.qr.QRCodeController;
import cocomo.restserver.user.User;
import cocomo.restserver.user.UserNotFoundException;
import cocomo.restserver.user.UserRepository;
import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

import cocomo.restserver.define.*;

// given - when - then
@RestController
@RequestMapping("/auth")
public class AuthUserController {

    @Autowired private AuthUserDaoService service;
    @Autowired private AuthUserRepository authUserRepository;
    @Autowired private UserRepository userRepository;


    @GetMapping("/users")
    public List<AuthUser> retrieveAllUsers() // 전체 유저 조회
    {
        return authUserRepository.findAll();
    }

    @PostMapping("/users")
    public ResponseEntity<AuthUser> saveUserId(AuthUser authUser)
    {
        AuthUser savedAuthUser = authUserRepository.save(authUser);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedAuthUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/otp/{id}")
    public EntityModel<AuthUser> otpCreate(@PathVariable int id)
            throws InvalidKeyException, NoSuchAlgorithmException
    {
        Optional<AuthUser> authUser = authUserRepository.findById(id);
        if (!authUser.isPresent())
        {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }
        else
        {
            if (authUser.get().getOtpKey() == null) // key값이 없는 유저면
            {
                String otpKey = OtpController.generateKey(Integer.toString(id)); // otp 키 생성
                authUser.get().setOtpKey(otpKey);
            }
            String otpCode = OtpController.createOtp(authUser.get().getOtpKey()); // otp 값 생성
            authUser.get().setOtpCode(otpCode);
        }
        authUserRepository.save(authUser.get());

        EntityModel model = EntityModel.of(authUser);
        return model;
    }

    @GetMapping("/qr/{userId}")
    public int findUserName(@PathVariable String userId)
    {
        Optional<User> findUser = userRepository.findByUserId(userId);

        if (!findUser.isPresent()) // 검색한 id가 존재하지 않으면
        {
            throw new UserNotFoundException(String.format("ID[%s] not found", userId));
        }
        else
        {
            QRCodeController.qrCreate(userId);
        }

        return Status.QR_CREATED;
    }

}
