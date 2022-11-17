package lpnu.repository;

import lpnu.entity.User;
import lpnu.entity.enumeration.Status;
import lpnu.entity.enumeration.UserRole;
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


    public List<User> getAllUsers() {
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

    public User save(User user) {

            validate(user);
            ++id;
            user.setId(id);
            user.setStatus(Status.ACTIVE);
            if (user.getUserRole() == null && user.getUserBalance() >= 100_000) {
                user.setUserRole(UserRole.VIP_CUSTOMER);
            }
            else if(user.getUserRole() == null ){
                user.setUserRole(UserRole.CUSTOMER);
            }
            users.add(user);

        return user;
    }

    private void validate(User user) throws IrregularDate {
        if (user.getBirthDate().isAfter(todayDate.minusYears(18))) {
            throw new IrregularDate("User is not adult", 400);
        }
        if (user.getUserBalance() < 0) {
            throw new IrregularDate("Balance is less then 0", 400);
        }
    }

    public User findById(Long id) {
        return users.stream()
                .filter(e -> e.getId() == (id))
                .findFirst()
                .orElseThrow(() -> new IrregularDate(("User not found by id: " + id)));
    }

    public User update(User user) {
        User saved = findById(user.getId());

        validate(user);
        saved.setName(user.getName());
        saved.setSurname(user.getSurname());
        saved.setUserRole(user.getUserRole());
        saved.setStatus(user.getStatus());
        saved.setUserBalance(user.getUserBalance());
        saved.setBriefcase(user.getBriefcase());

        if(saved.getUserBalance() >= 100_000 && saved.getUserRole()!= UserRole.ADMIN){
            saved.setUserRole(UserRole.VIP_CUSTOMER);
        }

        return saved;
    }
}
