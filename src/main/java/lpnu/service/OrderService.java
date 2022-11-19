package lpnu.service;

import lpnu.dto.CryptoDTO;
import lpnu.dto.OrderDTO;
import lpnu.dto.StockDTO;

public interface OrderService {
    OrderDTO buyCrypto(OrderDTO orderDTO);
    OrderDTO buyStock(OrderDTO orderDTO);

    OrderDTO sellCrypto(OrderDTO orderDTO);
    OrderDTO sellStock(OrderDTO orderDTO);

    void refreshCrypto(CryptoDTO cryptoDTO);
    void refreshCStock(StockDTO stockDTO);
}
