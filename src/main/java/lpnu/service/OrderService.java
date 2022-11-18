package lpnu.service;

import lpnu.dto.OrderDTO;

public interface OrderService {
    OrderDTO buyCrypto(OrderDTO orderDTO);
    OrderDTO buyStock(OrderDTO orderDTO);

    OrderDTO sellCrypto(OrderDTO orderDTO);
    OrderDTO sellStock(OrderDTO orderDTO);

    void refresh(long itemId);
}
