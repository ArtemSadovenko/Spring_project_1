package lpnu.entity.items;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Crypto {
    private long id;
    private String title;
    private double cost;
    private double capitalisation;
    private double amount;

//    public Crypto(long id, String title, double capitalisation, double amount) {
//        this.id = id;
//        this.title = title;
//        this.capitalisation = capitalisation;
//        this.amount = amount;
//        this.cost = capitalisation/amount;
//    }

}
