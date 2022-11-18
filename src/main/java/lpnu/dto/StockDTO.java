package lpnu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import javax.validation.constraints.PositiveOrZero;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class StockDTO {
    private long id;
    @NonNull
    private String title;
    private double cost;
    @NonNull
    @PositiveOrZero
    private double capitalisation;
    @NonNull
    @PositiveOrZero
    private int amount;

    public void genCost(){
        this.cost = capitalisation/amount;
    }
}
