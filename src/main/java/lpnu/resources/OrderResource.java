package lpnu.resources;

import lpnu.dto.OrderDTO;
import lpnu.dto.StockDTO;
import lpnu.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderResource {
    @Autowired
    OrderService orderService;

    @PostMapping("/crypto/buy")
    public OrderDTO buyCrypto(@RequestBody @Validated OrderDTO orderDTO){
        orderService.buyCrypto(orderDTO);
        return orderDTO;
    }

    @PostMapping("/stock/buy")
    public OrderDTO buyStock(@RequestBody @Validated OrderDTO orderDTO){
        orderService.buyStock(orderDTO);
        return orderDTO;
    }

    @PostMapping("/crypto/sell")
    public OrderDTO sellCrypto(@RequestBody @Validated OrderDTO orderDTO){
        orderService.sellCrypto(orderDTO);
        return orderDTO;
    }

    @PostMapping("/stock/sell")
    public OrderDTO sellStock(@RequestBody @Validated OrderDTO orderDTO){
        orderService.sellStock(orderDTO);
        return orderDTO;
    }
}
