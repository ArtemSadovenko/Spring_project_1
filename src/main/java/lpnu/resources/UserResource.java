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
    private UserService userService ;

    @GetMapping("/allFull")
    public List<UserDTO> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/allBrief")
    public List<UserDTO> getAllUsersBrief(){
        return userService.getAllUsersBrief();
    }

//    @PostMapping("/test1")
//    public String createUser(){
//        return "Done";
//    }

//@GetMapping("/test")
//public String test(){
//    return "Done";
//}

    @PostMapping("/create")
    public UserDTO createUser(@RequestBody @Validated UserDTO userDTO){
        return userService.createUser(userDTO);
    }

    @GetMapping("/{id}")
    public UserDTO findById(@PathVariable long id){
        return userService.findById(id);
    }

    @PutMapping
    public UserDTO updateUser(@RequestBody @Validated UserDTO userDTO){
        return userService.updateUser(userDTO);
    }

}
