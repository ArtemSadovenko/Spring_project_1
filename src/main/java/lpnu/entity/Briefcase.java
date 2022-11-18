package lpnu.entity;

import lombok.Data;
import lpnu.dto.CryptoDTO;
import lpnu.dto.StockDTO;
import lpnu.entity.items.Crypto;
import lpnu.entity.items.Stock;
import lpnu.repository.CryptoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Data
public class Briefcase {
    private List<CryptoDTO> cryptos = new ArrayList<>();
    private List<StockDTO> stocks = new ArrayList<>();
    private double balanceItems;

    public CryptoDTO addCrypto(CryptoDTO crypto) {
//        cryptos.add(crypto);
//
//        update();
//
//        return  crypto;
        if (cryptos.stream()
                .map(CryptoDTO::getTitle)
                .anyMatch(e -> e.equals(crypto.getTitle()))) {

            cryptos = cryptos.stream()
//                .filter(e -> e.getTitle().equals(crypto.getTitle()))
//                .map(e -> {
//                    if(e.getAmount() < crypto.getAmount()){
//                        throw new IrregularDate("Amount to buy is grater than amount of birge");
//                    }
//                    else {
//                        e.setAmount(e.getAmount() - crypto.getAmount());
//                    }
//                    return e;
//                })
                    .peek(e -> {
                        if (e.getTitle().equals(crypto.getTitle())) {
                            e.setAmount(e.getAmount() + crypto.getAmount());
                        }
                    })
                    .collect(Collectors.toList());
        } else {
            cryptos.add(crypto);
        }

        update();

        return crypto;
    }

        public StockDTO addStock (StockDTO stock){
            if(stocks.stream().map(StockDTO::getTitle).anyMatch(e -> e.equals(stock.getTitle()))){
                stocks = stocks.stream()
                        .peek(e -> {
                            if (e.getTitle().equals(stock.getTitle())) {
                                e.setAmount(e.getAmount() + stock.getAmount());
                            }
                        })
                        .collect(Collectors.toList());
            }

            update();

            return stock;
        }

    private void update() {

        double cryptoCost = cryptos.stream()
                .mapToDouble(e -> e.getAmount() * e.getCost())
                .sum();
        double stockCost = stocks.stream()
                .mapToDouble(e -> e.getAmount() * e.getCost())
                .sum();
        balanceItems = cryptoCost + stockCost;
    }
}