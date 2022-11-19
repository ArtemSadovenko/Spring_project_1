package lpnu.repository;

import lpnu.entity.items.Crypto;
import lpnu.entity.items.Stock;
import lpnu.exception.IrregularDate;
import lpnu.exception.RejectedPurchase;
import org.springframework.stereotype.Repository;

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
}

