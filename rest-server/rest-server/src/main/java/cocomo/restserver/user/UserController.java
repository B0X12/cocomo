package cocomo.restserver.user;

import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
public class UserController {

    private UserDaoService service;
    public UserController(UserDaoService service) // 의존성 추가
    {
        this.service = service;
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) // 유저 추가
    {
        User savedUser = service.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();
                // {id}에 savedUser.getId()를 넣어주고 uri 형태로 만들어줌

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/users")
    public List<User> retrieveAllUsers() // 전체 유저 조회
    {
        return service.findAll();
    }

    @GetMapping("/users/{id}")
    public User retrieveUser(@PathVariable int id) // 특정 유저 조회
    {
        User user =  service.findOne(id);

        if (user == null)
        {
            // [예외처리] 존재하지 않는 유저를 검색했을 때
            // 200 OK가 아닌 500 Internal Server… 를 결과로 돌려주도록 함.
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        return user;
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id)
    {
        User user = service.deleteById(id);

        if (user == null)
        {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }
    }

}
