package lpnu.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lpnu.dto.CryptoDTO;
import lpnu.dto.StockDTO;
import lpnu.entity.enumeration.Status;
import lpnu.entity.enumeration.UserRole;
import lpnu.entity.items.Crypto;

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

    public CryptoDTO addCrypto(CryptoDTO cryptoDTO){
        if(briefcase == null){
            briefcase = new Briefcase();
        }
        briefcase.addCrypto(cryptoDTO);
        return cryptoDTO;
    }

    public StockDTO addStock(StockDTO stockDTO){
        if(briefcase == null){
            briefcase = new Briefcase();
        }
        briefcase.addStock(stockDTO);
        return stockDTO;
    }
}
