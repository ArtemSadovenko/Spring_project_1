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
        CryptoDTO cryptoDTO = CryptoMapper.toDTO(cryptoRepository.findById(orderDTO.getItemID()));
        cryptoDTO.setAmount(orderDTO.getAmount());

        cryptoRepository.validateBuy(orderDTO.getAmount(), orderDTO.getItemID());
        userRepository.validateCrypto(cryptoDTO, orderDTO.getUserId());

        cryptoRepository.buy(orderDTO.getAmount(), orderDTO.getItemID());
        userRepository.addCrypto(cryptoDTO, orderDTO.getUserId());

        return orderDTO;
    }

    @Override
    public OrderDTO buyStock(OrderDTO orderDTO) {
        if(orderDTO.getAmount() - (int)orderDTO.getAmount() >0){
            throw new RejectedPurchase("Stock amount must be int");
        }
        StockDTO stockDTO = StockMapper.toDTO(stockRepository.findById(orderDTO.getItemID()));
        stockDTO.setAmount((int)orderDTO.getAmount());

        stockRepository.validateBuy((int)orderDTO.getAmount(), orderDTO.getItemID());
        userRepository.validateStock(stockDTO, orderDTO.getUserId());

        stockRepository.buy((int)orderDTO.getAmount(), orderDTO.getItemID());
        userRepository.addStock(stockDTO, orderDTO.getUserId());

        return orderDTO;
    }

    @Override
    public OrderDTO sellCrypto(OrderDTO orderDTO) {
        return null;
    }

    @Override
    public OrderDTO sellStock(OrderDTO orderDTO) {
        return null;
    }

    @Override
    public void refresh(long itemId) {

    }


}
