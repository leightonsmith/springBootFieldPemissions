package hello;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @RequestMapping("/")
    @JsonView(View.ReadOnly.class)
    public @ResponseBody Greeting index() {
        return new Greeting(123, "Hi there", "hidden content");
    }

    @RequestMapping("/view")
    public @ResponseBody Greeting second(@RequestParam long id) {
        return new Greeting(id, "Hi there", "hidden content");
    }

    @RequestMapping("/nested")
    public @ResponseBody Salutation nested(@RequestParam long id) {
        // bzzt ... assigning a view doesn't work for a nested class ...
        return new Salutation(id, "Hi there");
    }

}
