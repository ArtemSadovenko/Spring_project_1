package lpnu.resources;
import lpnu.dto.UserDTO;
import lpnu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserResource {

    @Autowired
    private UserService userService ;

//    @Autowired
//    private CryptoRepository cryptoService;

    @GetMapping("/allFull")
    public List<UserDTO> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/allBrief")
    public List<UserDTO> getAllUsersBrief(){
        return userService.getAllUsersBrief();
    }

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

//    @GetMapping("/test")
//    public List<CryptoDTO> getCrypto(){
//        return cryptoService.getAllCrypto().stream()
//                .map(CryptoMapper::toDTO)
//                .collect(Collectors.toList());
//    }
}
