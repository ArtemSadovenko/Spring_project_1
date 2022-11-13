package lpnu.resources;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/res")
public class UserResource {

    @GetMapping("/api")
    public String rest(){
        return "Uellp";
    }
    @GetMapping("/api2")
    public String test(){
        return "Uell";
    }
}
