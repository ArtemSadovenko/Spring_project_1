package lpnu.resources;

import lpnu.dto.UserDTO;
import lpnu.entity.User;
import lpnu.service.UserService;
import lpnu.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserResource {

    @Autowired
    private UserServiceImpl userService ;

    @GetMapping("/allFull")
    public List<UserDTO> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/test")
    public String test(){
        return "Done";
    }

    @GetMapping("/allBrief")
    public List<UserDTO> getAllUsersBrief(){
        return userService.getAllUsersBrief();
    }

    @PostMapping()
    public UserDTO createUser(@RequestBody UserDTO userDTO){
        return userService.createUser(userDTO);
    }

}
