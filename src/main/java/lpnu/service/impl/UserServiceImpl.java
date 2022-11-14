package lpnu.service.impl;

import lpnu.dto.UserDTO;
import lpnu.entity.User;
import lpnu.entity.enumeration.Status;
import lpnu.mapper.UserMapper;
import lpnu.repository.UserRepository;
import lpnu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.getAllUsers().stream()
                .map(UserMapper::toFullDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> getAllUsersBrief() {
        return userRepository.getAllUsers().stream()
                .map(UserMapper::toBriefDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = UserMapper.toEntity(userDTO);

        user.setStatus(Status.ACTIVE);
        userRepository.save(user);

        return UserMapper.toFullDTO(user);
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO) {
        return null;
    }

    @Override
    public void deleteUser(Long id) {

    }

    @Override
    public UserDTO findById(Long id) {
        return null;
    }
}
