package lpnu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.lang.NonNullFields;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Min;
import javax.validation.constraints.Null;
import javax.validation.constraints.PositiveOrZero;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CryptoDTO {
    private long id;
    @NonNull
    private String title;
    private double cost;
    @NonNull
    @PositiveOrZero
    private double capitalisation;
    @NonNull
    @PositiveOrZero
    private double amount;

    public void genCost(){
        this.cost = capitalisation/amount;
    }
}
