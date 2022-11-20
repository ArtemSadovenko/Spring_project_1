package lpnu.service.impl;

import lpnu.dto.UserDTO;
import lpnu.entity.User;
import lpnu.entity.enumeration.Status;
import lpnu.entity.enumeration.UserRole;
import lpnu.exception.IrregularDate;
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
        User user = UserMapper.toEntity(userDTO);

        userRepository.update(user);

        return  UserMapper.toFullDTO(user);
    }

    @Override
    public UserDTO banUser(long adminId, long userId) {
        if(findById(adminId).getUserRole() != UserRole.ADMIN){
            throw new IrregularDate("Only admin can ban another user");
        }
        if(adminId == userId){
            throw new IrregularDate("Це тобі не Ubuntu щоб в ногу стріляти");
        }

        userRepository.ban(userId);

        return UserMapper.toFullDTO(userRepository.findById(userId));
    }

    @Override
    public UserDTO unbanUser(long adminId, long userId) {
        if(findById(adminId).getUserRole() != UserRole.ADMIN){
            throw new IrregularDate("Only admin can ban another user");
        }
        if(adminId== userId){
            throw new IrregularDate("Це тобі не Ubuntu щоб в ногу стріляти");
        }

       userRepository.unban(userId);

        return UserMapper.toFullDTO(userRepository.findById(userId));
    }

    @Override
    public UserDTO deleteUser(long userId) {
        return userRepository.delete(userId);
    }



    @Override
    public UserDTO findById(Long id) {
        return UserMapper.toFullDTO(userRepository.findById(id));
    }
}
