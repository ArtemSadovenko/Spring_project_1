package lpnu.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import lpnu.entity.items.Crypto;
import lpnu.entity.items.Stock;
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
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class StockRepository {
    public List<Stock> stocks = new ArrayList<>();
    private long id;

    public List<Stock> getAllStock() {
        return new ArrayList<>(stocks);
    }

    public Stock save(Stock stock) {
        validate(stock);

        ++id;
        stock.setId(id);
        stocks.add(stock);

        return stock;
    }

    public Stock update(Stock stock) {
        Stock saved = findById(stock.getId());

        //validate(stock);

        saved.setTitle(stock.getTitle());
        saved.setCost(stock.getCost());
        saved.setCapitalisation(stock.getCapitalisation());
        saved.setAmount(stock.getAmount());

        return saved;
    }

    public Stock findById(long id) {
        return stocks.stream()
                .filter(e -> e.getId() == id)
                .findFirst()
                .orElseThrow(() -> new IrregularDate("Stock not found by id: " + id, 400));
    }

    private void validate(Stock stock) {
        if (stocks.stream().anyMatch(e -> e.getTitle().equals(stock.getTitle()))) {
            throw new IrregularDate("Stock '" + stock.getTitle() + "' already exist", 400);
        }
    }

    public void buy(int amount, long id) {
        stocks = stocks.stream()
                .map(e -> {
                    if (e.getId() == id) {
//                        if(e.getAmount() < amount){
//                            throw new RejectedPurchase("Not enough amount");
//                        }
                        e.setAmount(e.getAmount() - amount);
                        e.setCapitalisation(e.getCapitalisation() + e.getCost()*amount);
                        e.genCost();

                    }
                    return e;
                })
                .collect(Collectors.toList());
    }

    public void validateBuy(int amount, long id) {
        findById(id);

        stocks.stream()
                .map(e -> {
                    if (e.getId() == id) {
                        if (e.getAmount() < amount) {
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

    public Stock sell(double amount, long id){
        stocks = stocks.stream()
                .map(e ->{
                    if(e.getId() == id){
                        e.setAmount(e.getAmount() + (int)amount);
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

        final Path file = Paths.get("stock.txt");
        try {
            final String savedStocksAsString = Files.readString(file, StandardCharsets.UTF_16);
            stocks = JacksonUtil.deserialize(savedStocksAsString, new TypeReference<List<Stock>>() {});


            if (stocks == null) {
                stocks = new ArrayList<>();
                return;
            }

            this.id = stocks.stream().mapToLong(Stock::getId).max().orElse(0);



        } catch (final Exception e){
            System.out.println("We have an issue");
            stocks = new ArrayList<>();
        }

    }


    @PreDestroy
    public void preDestroy(){
        final Path file = Paths.get("stock.txt");

        try {
            Files.writeString(file, JacksonUtil.serialize(stocks), StandardCharsets.UTF_16);
        } catch (final Exception e){
            System.out.println("We have an issue");
        }
    }

}

