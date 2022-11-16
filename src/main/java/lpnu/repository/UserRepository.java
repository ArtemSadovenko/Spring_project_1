package lpnu.repository;

import lpnu.entity.User;
import lpnu.exception.IrregularDate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {
    private List<User> users = new ArrayList<>();
    private long id = 0L;
    private LocalDate todayDate = LocalDate.now();


    public List<User> getAllUsers(){
//        User user = new User();
//        user.setName("Tom");
//        user.setSurname("Bond");
//        user.setBirthDate( LocalDate.of(2004, 1,1));
//        users.add(user);

        return new ArrayList<>(users);
    }

//    public ResponseEntity save(User user){
//        try {
//            validate(user);
//            ++id;
//            user.setId(id);
//            users.add(user);
//
//        }catch (IrregularDate e){
//            return ResponseEntity.status(400).body(e.invoke());
//        }
//        return ResponseEntity.ok().body(user);
//    }

    public User save(User user){
        try {
            validate(user);
            ++id;
            user.setId(id);
            users.add(user);

        }catch (IrregularDate e){}
        return user;
    }

    private void validate(User user)throws IrregularDate {
        if(user.getBirthDate().isAfter(todayDate.minusYears(18))){
            throw new IrregularDate("User is not adult", 400);
        }
    }

    public User findById(Long id){
        return users.stream()
                .filter(e -> e.getId() == (id))
                .findFirst()
                .orElseThrow(() -> new IrregularDate(("User not found by id: " + id)));
    }
}
