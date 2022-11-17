package lpnu.mapper;

import lpnu.dto.StockDTO;
import lpnu.entity.items.Stock;

public class StockMapper {
    public static Stock toEntity(StockDTO stockDTO){
        Stock stock = new Stock();

        stock.setId(stockDTO.getId());
        stock.setAmount(stockDTO.getAmount());
        stock.setCost(stockDTO.getCost());
        stock.setCapitalisation(stockDTO.getCapitalisation());
        stock.setTitle(stockDTO.getTitle());

        return stock;
    }

    public static StockDTO toDTO(Stock stock){
        StockDTO stockDTO = new StockDTO();

        stockDTO.setId(stock.getId());
        stockDTO.setAmount(stock.getAmount());
        stockDTO.setCost(stock.getCost());
        stockDTO.setCapitalisation(stock.getCapitalisation());
        stockDTO.setTitle(stock.getTitle());

        return stockDTO;
    }
}
