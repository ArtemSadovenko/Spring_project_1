package lpnu.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lpnu.entity.enumeration.Status;
import lpnu.entity.enumeration.UserRole;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private long id;
    private String name;
    private String surname;
    private LocalDate birthDate;
    private Status status;
    private Briefcase briefcase;
    private UserRole userRole;
    private  double userBalance;
}
