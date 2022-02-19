package cocomo.restserver.auth;

import cocomo.restserver.auth.otp.OtpService;
import cocomo.restserver.auth.qr.QRCodeService;
import cocomo.restserver.user.User;
import cocomo.restserver.user.UserNotFoundException;
import cocomo.restserver.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

import cocomo.restserver.define.*;

// given - when - then
@RestController
@RequestMapping("/auth")
public class AuthUserController {

    @Autowired private AuthUserDaoService service;
    @Autowired private AuthUserRepository authUserRepository;
    @Autowired private UserRepository userRepository;

    private boolean retRes;

    private OtpService otpService;
    private QRCodeService qrCodeService;

    private Timer timer;
    private TimerTask timerTask;

    private int mAuthQrResult;
    private int mAuthFingerResult;

    private boolean authQrResult = false;
    private boolean authFingerResult = false;
    private boolean authLockResult = false;


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


    // OTP key
    @GetMapping("/otp-key/{userId}")
    public EntityModel<AuthUser> getOtpKey(@PathVariable String userId)
    {
        Optional<AuthUser> authUser = authUserRepository.findByUserId(userId);

        if (!authUser.isPresent())
        {
            throw new UserNotFoundException(String.format("ID[%s] not found", userId));
        }
        else
        {
            EntityModel model = EntityModel.of(authUser);
            return model;
        }
    }


    // OtpController
    @GetMapping("/otp/{userId}")
    public EntityModel<AuthUser> authOtp(@PathVariable String userId)
            throws InvalidKeyException, NoSuchAlgorithmException
    {
        Optional<AuthUser> authUser = authUserRepository.findByUserId(userId);
        if (!authUser.isPresent())
        {
            throw new UserNotFoundException(String.format("ID[%s] not found", userId));
        }
        else
        {
            if (authUser.get().getOtpKey() == null) // key값이 없는 유저면
            {
                String otpKey = otpService.generateKey(userId); // otp 키 생성
                authUser.get().setOtpKey(otpKey);
            }
            String otpCode = otpService.createOtp(authUser.get().getOtpKey()); // otp 값 생성
            authUser.get().setOtpCode(otpCode);
        }
        authUserRepository.save(authUser.get());

        EntityModel model = EntityModel.of(authUser);
        return model;
    }

    // ───────────────────────────────

    /*
     * QR (GET)
     * 생성
     */
    @GetMapping("/qr-create/{userId}")
    public int authQrCreate(@PathVariable String userId)
    {
        Optional<User> findUser = userRepository.findByUserId(userId);

        if (!findUser.isPresent()) // 검색한 id가 존재하지 않으면
        {
            throw new UserNotFoundException(String.format("ID[%s] not found", userId));
        }
        else
        {
            qrCodeService.qrCreate(userId);
        }

        return Status.QR_CREATED;
    }

    /*
     * QR (GET)
     * Screenlock -> Android 요청
     */
    @GetMapping("/qr/{userId}")
    public int authQr(@PathVariable String userId)
    {
        Optional<AuthUser> findAuthUser = authUserRepository.findByUserId(userId);

        if (!findAuthUser.isPresent())
        {
            throw new UserNotFoundException(String.format("ID[%s] not found", userId));
        }
        else
        {
            // 인증 대기
            timer = new Timer();
            setTimerTask(timer);

            while (!authQrResult)
            {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if (authQrResult)
            {
                mAuthQrResult = Status.AUTH_SUCCESS;
                timer.cancel();
            }

            authQrResult = false;
            findAuthUser.get().setAuthQrResult(Status.AUTH_NOTHING);
            return mAuthQrResult;
        }
    }

    /*
     * QR (POST)
     * Android -> Screenlock로 결과 전달
     */
    @PostMapping("/qr/{userId}")
    public int authQrResult(@PathVariable String userId, @RequestBody AuthUser authUser)
    {
        Optional<AuthUser> findAuthUser = authUserRepository.findByUserId(userId);

        if (!findAuthUser.isPresent())
        {
            throw new UserNotFoundException(String.format("ID[%s] not found", userId));
        }
        else
        {
            if (authUser.getAuthQrResult() == Status.AUTH_SUCCESS)
            {
                // 해당 사용자의 인증 결과를 설정
                findAuthUser.get().setAuthQrResult(Status.AUTH_SUCCESS);
                authQrResult = true;
            }
            else if (authUser.getAuthQrResult() == Status.AUTH_FAILED)
            {
                findAuthUser.get().setAuthQrResult(Status.AUTH_FAILED);
            }
            else if (authUser.getAuthQrResult() == Status.AUTH_ERROR)
            {
                findAuthUser.get().setAuthQrResult(Status.AUTH_ERROR);
            }
            else
            {
                return 0;
            }
        }

        authUserRepository.save(findAuthUser.get());
        return findAuthUser.get().getAuthQrResult();
    }

    // ───────────────────────────────

    /*
     * Fingerprint (GET)
     * Screenlock -> Android 요청
     */
    @GetMapping("/finger/{userId}")
    public int authFinger(@PathVariable String userId)
    {
        Optional<AuthUser> findAuthUser = authUserRepository.findByUserId(userId);

        if (!findAuthUser.isPresent())
        {
            throw new UserNotFoundException(String.format("ID[%s] not found", userId));
        }
        else
        {
            // userID 기반으로 user를 찾고,
            // 사용자가 인증될 때 까지 대기한 후 인증 결과 반환

            timer = new Timer();
            setTimerTask(timer);

            while (!authFingerResult)
            {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

            if (authFingerResult)
            {
                mAuthFingerResult = Status.AUTH_SUCCESS;
                timer.cancel();
            }

            authFingerResult = false;
            findAuthUser.get().setAuthFingerResult(Status.AUTH_NOTHING);
            return mAuthFingerResult;
        }
    }

    /*
     * Fingerprint (POST)
     * Android -> Screenlock로 결과 전달
     */
    @PostMapping("/finger/{userId}")
    public int authFingerResult(@PathVariable String userId, @RequestBody AuthUser authUser)
    {
        Optional<AuthUser> findAuthUser = authUserRepository.findByUserId(userId);

        if (!findAuthUser.isPresent())
        {
            throw new UserNotFoundException(String.format("ID[%s] not found", userId));
        }
        else
        {
            if (authUser.getAuthFingerResult() == Status.AUTH_SUCCESS)
            {
                // 해당 사용자의 인증 결과를 설정
                findAuthUser.get().setAuthFingerResult(Status.AUTH_SUCCESS);
                authFingerResult = true;
            }
            else if (authUser.getAuthFingerResult() == Status.AUTH_FAILED)
            {
                findAuthUser.get().setAuthFingerResult(Status.AUTH_FAILED);
            }
            else if (authUser.getAuthFingerResult() == Status.AUTH_ERROR)
            {
                findAuthUser.get().setAuthFingerResult(Status.AUTH_ERROR);
            }
            else
            {
                return 0;
            }
        }

        authUserRepository.save(findAuthUser.get());
        return findAuthUser.get().getAuthFingerResult();
    }

    // ───────────────────────────────

    /*
     * Screenlock (GET)
     */
    @GetMapping("/lock/{userId}")
    public EntityModel<AuthUser> enrolledScreenlockResponse(@PathVariable String userId)
    {
        Optional<AuthUser> findAuthUser = authUserRepository.findByUserId(userId);

        if (!findAuthUser.isPresent())
        {
            throw new UserNotFoundException(String.format("ID[%s] not found", userId));
        }
        else
        {
            // 사용자가 스크린락 요청할 때까지 대기
            findAuthUser.get().setAuthFingerResult(0);

            timer = new Timer();
            setTimerTask(timer);

            while (!authLockResult)
            {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

            if (authLockResult) // Android에서 요청이 도착했을 경우
            {
                findAuthUser.get().setExecuteScreenlock(1);
                authUserRepository.save(findAuthUser.get());

                timer.cancel();
            }

            authLockResult = false;
            EntityModel model = EntityModel.of(findAuthUser);
            return model;
        }
    }

    /*
     * Screenlock (PUT)
     * Android에서 스크린락 메뉴 클릭
     */
    @PutMapping("/lock/{userId}")
    public void enrolledScreenlockRequest(@PathVariable String userId)
    {
        Optional<AuthUser> findAuthUser = authUserRepository.findByUserId(userId);

        if (!findAuthUser.isPresent())
        {
            throw new UserNotFoundException(String.format("ID[%s] not found", userId));
        }
        else
        {
            authLockResult = true; // Android의 요청 여부
        }
    }

    // ───────────────────────────────

    public void setTimerTask(Timer timer)
    {
        timerTask = new TimerTask()
        {
            @Override
            public void run()
            {
                System.out.println("#Timer: 2s");
            }
        };

        timer.schedule(timerTask, 2000, 2000);
    }
}