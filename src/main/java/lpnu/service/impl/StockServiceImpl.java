package lpnu.service.impl;

import lpnu.dto.StockDTO;
import lpnu.entity.items.Stock;
import lpnu.mapper.StockMapper;
import lpnu.repository.StockRepository;
import lpnu.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StockServiceImpl implements StockService {
    @Autowired
    StockRepository stockRepository;

    @Override
    public List<StockDTO> getAllStock() {
        return stockRepository.getAllStock().stream()
                .map(StockMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public StockDTO createStock(StockDTO stockDTO) {
        Stock stock = StockMapper.toEntity(stockDTO);

        stockRepository.save(stock);

        return StockMapper.toDTO(stock);
    }

    @Override
    public StockDTO updateStock(StockDTO stockDTO) {
        Stock stock = StockMapper.toEntity(stockDTO);

        stockRepository.update(stock);

        return StockMapper.toDTO(stock);
    }

    @Override
    public StockDTO findById(long id) {
        return StockMapper.toDTO(stockRepository.findById(id));
    }
}
