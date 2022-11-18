package lpnu.service;

import lpnu.dto.CryptoDTO;
import lpnu.dto.StockDTO;

import java.util.List;

public interface StockService {
    List<StockDTO> getAllStock();
    StockDTO createStock(StockDTO stockDTO);
    StockDTO updateStock(StockDTO stockDTO);
    StockDTO findById(long id);
}
