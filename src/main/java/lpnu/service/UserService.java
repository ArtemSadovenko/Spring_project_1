package lpnu.service;

import lpnu.dto.UserDTO;
import lpnu.repository.UserRepository;

import java.util.List;

public interface UserService {

    List<UserDTO> getAllUsers();
    List<UserDTO> getAllUsersBrief();
    UserDTO createUser(UserDTO userDTO);
    UserDTO updateUser(UserDTO userDTO);
    void deleteUser(Long id);
    UserDTO findById(Long id);

}
