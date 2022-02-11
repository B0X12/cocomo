package cocomo.restserver.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    @GetMapping("/cocomo")
    public String mainControl()
    {
        return "hello!";
    }

    @GetMapping("/cocomo-json")
    public RestBean restBean()
    {
        return new RestBean("hello?");
        // 반환형이 클래스면
        // message: hello와 같은 형식으로 보여짐 (json)
    }

    @GetMapping("/cocomo-json/{id}")
    public RestBean restBean(@PathVariable String id)
    {
        return new RestBean(String.format("안녕하세요, %s님!", id));
    }
}
