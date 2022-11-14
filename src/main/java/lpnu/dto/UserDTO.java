package lpnu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lpnu.entity.Briefcase;
import lpnu.entity.enumeration.Status;
import lpnu.entity.enumeration.UserRole;
import org.springframework.lang.NonNull;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private long id;
    @NonNull
    private String name;
    @NonNull
    private String surname;
    @NonNull
    private LocalDate birthDate;
    private Status status;
    private UserRole userRole;
    private Briefcase briefcase;
    private double userBalance;
}
