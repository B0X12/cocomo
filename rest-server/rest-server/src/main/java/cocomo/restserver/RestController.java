package cocomo.restserver;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@org.springframework.web.bind.annotation.RestController
public class RestController
{
    // GET
    // endpoint
    @GetMapping("/cocomo")
    public String mainControl()
    {
        return "hello!";
    }

    @GetMapping("/cocomo-json")
    public MainControlBean mainControlBean()
    {
        return new MainControlBean("hello?");
        // message: hello와 같은 형식으로 보여짐 (json)
    }

    @GetMapping("/cocomo-json/{id}")
    public MainControlBean mainControlBean(@PathVariable String id)
    {
        return new MainControlBean(String.format("안녕하세요, %s님!", id));
        // message: hello와 같은 형식으로 보여짐 (json)
    }
}
