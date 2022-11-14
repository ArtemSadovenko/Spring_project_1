package lpnu.repository;

import lpnu.entity.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {
    private List<User> users = new ArrayList<>();
    private long id = 0L;

    public List<User> getAllUsers(){
        User user = new User();
        user.setName("Tom");
        user.setSurname("Bond");
        users.add(user);

        return new ArrayList<>(users);
    }

    public User save(User user){
        ++id;
        user.setId(id);
        users.add(user);

        return user;
    }


}
