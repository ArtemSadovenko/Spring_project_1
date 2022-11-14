package lpnu.mapper;

import lombok.NonNull;
import lpnu.dto.UserDTO;
import lpnu.entity.User;

public class UserMapper {
    public static UserDTO toFullDTO(User user){
        UserDTO userDTO = new UserDTO();

        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setSurname(user.getSurname());
        userDTO.setBirthDate(user.getBirthDate());
        userDTO.setStatus(user.getStatus());
        userDTO.setBriefcase(user.getBriefcase());
        userDTO.setUserBalance(user.getUserBalance());
        userDTO.setUserRole(user.getUserRole());

        return userDTO;
    }

    public static UserDTO toBriefDTO(User user){
        UserDTO userDTO = new UserDTO();

        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setSurname(user.getSurname());
        userDTO.setBirthDate(user.getBirthDate());
        userDTO.setStatus(user.getStatus());
        userDTO.setBriefcase(null);
        userDTO.setUserBalance(user.getUserBalance());
        userDTO.setUserRole(user.getUserRole());

        return userDTO;
    }

    //to do?
    public static User toEntity(UserDTO userDTO){
        User user = new User();

        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setSurname(userDTO.getSurname());
        user.setBirthDate(userDTO.getBirthDate());
        user.setStatus(userDTO.getStatus());
        user.setBriefcase(userDTO.getBriefcase());
        user.setUserBalance(userDTO.getUserBalance());
        user.setUserRole(userDTO.getUserRole());

        return user;
    }


}
