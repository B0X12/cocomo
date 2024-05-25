package cocomo.restserver.user;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

// /users에서 볼 수 없는 ignore된 내용 (password, joinDate)
// 을 볼 수 있는 관리자 페이지

@RestController
@RequestMapping("/admin") // url의 맨 앞에 붙게됨
public class AdminUserController {

    private UserDaoService service;
    public AdminUserController(UserDaoService service) // 의존성 추가
    {
        this.service = service;
    }

    @GetMapping("/users")
    public MappingJacksonValue retrieveAllUsers() // 전체 유저 조회
    {
        List<User> users = service.findAll();

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id", "name", "email", "joinDate");

        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(users);
        mapping.setFilters(filters);

        return mapping;
    }

    @GetMapping("/users/{id}")
    public MappingJacksonValue retrieveUser(@PathVariable int id) // 특정 유저 조회
    {
        User user =  service.findOne(id);

        if (user == null)
        {
            // [예외처리] 존재하지 않는 유저를 검색했을 때
            // 200 OK가 아닌 500 Internal Server… 를 결과로 돌려주도록 함.
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id", "name", "email", "joinDate");

        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(user);
        mapping.setFilters(filters);

        return mapping;
    }

}
