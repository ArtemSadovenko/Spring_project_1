package lpnu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import javax.validation.constraints.PositiveOrZero;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    @NonNull
    private long userId;
    @NonNull
    private long itemId;
    @NonNull
    @PositiveOrZero
    private double amount;
}
