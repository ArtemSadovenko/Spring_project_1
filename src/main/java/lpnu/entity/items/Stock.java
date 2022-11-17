package lpnu.entity.items;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Stock implements Item{
    private long id;
    private String title;
    private double cost;
    private double capitalisation;
    private int amount;
}
