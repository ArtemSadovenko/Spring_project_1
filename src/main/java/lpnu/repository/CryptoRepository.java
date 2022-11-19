package lpnu.repository;

import lpnu.dto.CryptoDTO;
import lpnu.entity.items.Crypto;
import lpnu.exception.IrregularDate;
import lpnu.exception.RejectedPurchase;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CryptoRepository {
    private List<Crypto> cryptos = new ArrayList<>();
    private long id = 0;

    public Crypto save(Crypto crypto){
        validate(crypto);

        ++id;
        crypto.setId(id);
        cryptos.add(crypto);

        return crypto;
    }

    public Crypto update(Crypto crypto){
        Crypto saved = findById(crypto.getId());

        //validate(crypto);

        saved.setTitle(crypto.getTitle());
        saved.setCost(crypto.getCost());
        saved.setCapitalisation(crypto.getCapitalisation());
        saved.setAmount(crypto.getAmount());

        return saved;
    }

    public List<Crypto> getAllCrypto(){
        return new ArrayList<>(cryptos);
    }

    public Crypto findById(long id){
        return cryptos.stream()
                .filter(e -> e.getId() == id)
                .findFirst()
                .orElseThrow(() -> new IrregularDate("Crypto not found by id: "+ id, 400));
    }

    //?to do
    public void validate(Crypto crypto){
        if(cryptos.stream().anyMatch(e -> e.getTitle().equals(crypto.getTitle()))){
            throw new IrregularDate("Crypto '" + crypto.getTitle() + "' already exist", 400);
        }
    }

    public void buy(double amount, long id){
//       findById(id);
//
//       cryptos = cryptos.stream()
//               .map(e -> {
//                   if(e.getId() == id ){
//                       if(e.getAmount() < amount){
//                           throw new RejectedPurchase("Not enough amount");
//                       }
//                       else {
//                           e.setAmount(e.getAmount() - amount);
//                       }
//                   }
//                   return e;
//               })
//               .collect(Collectors.toList());
        cryptos = cryptos.stream()
                .map(e -> {
                    if(e.getId() == id ){
                        e.setAmount(e.getAmount() - amount);
                        e.setCapitalisation(e.getCapitalisation() + e.getCost()*amount);
                        e.genCost();
                    }
                    return e;
                })
                .collect(Collectors.toList());

    }

    public void validateBuy(double amount, long id){
        findById(id);

        cryptos.stream()
                .map(e -> {
                    if(e.getId() == id ){
                        if(e.getAmount() < amount){
                            throw new RejectedPurchase("Not enough amount");
                        }
//                        else {
//                            e.setAmount(e.getAmount() - amount);
//                        }
                    }
                    return e;
                })
                .collect(Collectors.toList());
    }

    public Crypto sell(double amount, long id){
        cryptos = cryptos.stream()
                .map(e ->{
                    if(e.getId() == id){
                        e.setAmount(e.getAmount() + amount);
                        e.setCapitalisation(e.getCapitalisation() - e.getCost()*amount);
                        e.genCost();
                    }
                    return e;
                })
                .collect(Collectors.toList());
        return findById(id);
    }

}
