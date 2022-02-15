package cocomo.restserver.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/jpa")
public class UserJpaController {

    @Autowired // 의존성 주입
    private UserRepository userRepository;

    @GetMapping("/users")
    public List<User> retrieveAllUsers()
    {
        return userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public EntityModel<User> retrieveUser(@PathVariable int id)
    {
        Optional<User> user = userRepository.findById(id);

        if (!user.isPresent()) // 검색한 id가 존재하지 않으면
        {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        // hateoas
        EntityModel model = EntityModel.of(user);

        // 이 user에 대한 값을 반환시킬 때,
        // user가 사용할 수 있는 추가적인 정보를 hyperlink로 넣어줌
        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());

        model.add(linkTo.withRel("all-users"));
        return model;
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id)
    {
        userRepository.deleteById(id);
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user)
    {
        User savedUser = userRepository.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();
        // {id}에 savedUser.getId()를 넣어주고 uri 형태로 만들어줌

        return ResponseEntity.created(location).build();
    }

}
