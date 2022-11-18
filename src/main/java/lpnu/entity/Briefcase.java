package lpnu.entity;

import lombok.Data;
import lpnu.entity.items.Crypto;
import lpnu.entity.items.Stock;

import java.util.ArrayList;
import java.util.List;


@Data
public class Briefcase {
    private List<Crypto> cryptos = new ArrayList<>();
    private List<Stock> stocks = new ArrayList<>();
    private double balanceItems;

    public Crypto addCrypto(Crypto crypto){
        cryptos.add(crypto);

        update();

        return  crypto;
    }

    public Stock addStock(Stock stock){
        stocks.add(stock);

        update();

        return stock;
    }

    private void update(){
        double cryptoCost = cryptos.stream()
                .mapToDouble(e -> e.getAmount()* e.getCost())
                .sum();
        double stockCost = stocks.stream()
                .mapToDouble(e -> e.getAmount()* e.getCost())
                .sum();
        balanceItems = cryptoCost+stockCost;
    }
}