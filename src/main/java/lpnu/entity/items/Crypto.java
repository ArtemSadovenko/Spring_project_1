package lpnu.entity.items;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Crypto implements Item{
    private long id;
    private String title;
    private double cost;
    private double capitalisation;
    private double amount;
}
