package lpnu.service.impl;

import lpnu.dto.CryptoDTO;
import lpnu.dto.OrderDTO;
import lpnu.dto.StockDTO;
import lpnu.exception.RejectedPurchase;
import lpnu.mapper.CryptoMapper;
import lpnu.mapper.StockMapper;
import lpnu.repository.CryptoRepository;
import lpnu.repository.StockRepository;
import lpnu.repository.UserRepository;
import lpnu.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    CryptoRepository cryptoRepository;

    @Autowired
    StockRepository stockRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public OrderDTO buyCrypto(OrderDTO orderDTO) {
        CryptoDTO cryptoDTO = CryptoMapper.toDTO(cryptoRepository.findById(orderDTO.getItemId()));
        cryptoDTO.setAmount(orderDTO.getAmount());

        cryptoRepository.validateBuy(orderDTO.getAmount(), orderDTO.getItemId());
        userRepository.validateCrypto(cryptoDTO, orderDTO.getUserId());

        userRepository.addCrypto(cryptoDTO, orderDTO.getUserId());
        cryptoRepository.buy(orderDTO.getAmount(), orderDTO.getItemId());

        refreshCrypto(CryptoMapper.toDTO(cryptoRepository.findById(orderDTO.getItemId())));
        return orderDTO;
    }

    @Override
    public OrderDTO buyStock(OrderDTO orderDTO) {
        if(orderDTO.getAmount() - (int)orderDTO.getAmount() >0){
            throw new RejectedPurchase("Stock amount must be int");
        }
        StockDTO stockDTO = StockMapper.toDTO(stockRepository.findById(orderDTO.getItemId()));
        stockDTO.setAmount((int)orderDTO.getAmount());

        stockRepository.validateBuy((int)orderDTO.getAmount(), orderDTO.getItemId());
        userRepository.validateStock(stockDTO, orderDTO.getUserId());

        userRepository.addStock(stockDTO, orderDTO.getUserId());
        stockRepository.buy((int)orderDTO.getAmount(), orderDTO.getItemId());

        refreshCStock(StockMapper.toDTO(stockRepository.findById(orderDTO.getItemId())));
        return orderDTO;
    }

    @Override
    public OrderDTO sellCrypto(OrderDTO orderDTO) {
        CryptoDTO cryptoDTO = CryptoMapper.toDTO(cryptoRepository.findById(orderDTO.getItemId()));
        cryptoDTO.setAmount(orderDTO.getAmount());

        userRepository.validateCryptoSell(cryptoDTO, orderDTO.getUserId());

        cryptoRepository.sell(orderDTO.getAmount(), orderDTO.getItemId());
        userRepository.sellCrypto(cryptoDTO, orderDTO.getUserId());

        refreshCrypto(CryptoMapper.toDTO(cryptoRepository.findById(orderDTO.getItemId())));
        return orderDTO;
    }

    @Override
    public OrderDTO sellStock(OrderDTO orderDTO) {
        if(orderDTO.getAmount() - (int)orderDTO.getAmount() >0){
            throw new RejectedPurchase("Stock amount must be int");
        }

        StockDTO stockDTO = StockMapper.toDTO(stockRepository.findById(orderDTO.getItemId()));
        stockDTO.setAmount((int)orderDTO.getAmount());

        userRepository.validateStockSell(stockDTO, orderDTO.getUserId());

        stockRepository.sell(orderDTO.getAmount(), orderDTO.getItemId());
        userRepository.sellStock(stockDTO, orderDTO.getUserId());

        refreshCStock(StockMapper.toDTO(stockRepository.findById(orderDTO.getItemId())));
        return orderDTO;
    }

    @Override
    public void refreshCrypto(CryptoDTO cryptoDTO) {
        userRepository.refreshCrypto(cryptoDTO);
    }

    @Override
    public void refreshCStock(StockDTO stockDTO) {
        userRepository.refreshStock(stockDTO);
    }
}
