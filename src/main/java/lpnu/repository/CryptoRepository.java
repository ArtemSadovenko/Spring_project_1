package lpnu.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import lpnu.dto.CryptoDTO;
import lpnu.entity.User;
import lpnu.entity.items.Crypto;
import lpnu.exception.IrregularDate;
import lpnu.exception.RejectedPurchase;
import lpnu.util.JacksonUtil;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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




    @PostConstruct
    public void init(){

        final Path file = Paths.get("crypto.txt");
        try {
            final String savedCryptosAsString = Files.readString(file, StandardCharsets.UTF_16);
            cryptos = JacksonUtil.deserialize(savedCryptosAsString, new TypeReference<List<Crypto>>() {});


            if (cryptos == null) {
                cryptos = new ArrayList<>();
                return;
            }

            this.id = cryptos.stream().mapToLong(Crypto::getId).max().orElse(0);



        } catch (final Exception e){
            System.out.println("We have an issue");
            cryptos = new ArrayList<>();
        }

    }


    @PreDestroy
    public void preDestroy(){
        final Path file = Paths.get("crypto.txt");

        try {
            Files.writeString(file, JacksonUtil.serialize(cryptos), StandardCharsets.UTF_16);
        } catch (final Exception e){
            System.out.println("We have an issue");
        }
    }
}
