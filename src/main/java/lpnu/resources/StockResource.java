package lpnu.resources;

import lpnu.dto.StockDTO;
import lpnu.service.StockService;
import lpnu.service.impl.StockServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stock")
public class StockResource {

    @Autowired
    private StockService stockService;

    @PostMapping("/create")
    public StockDTO createStock(@RequestBody @Validated StockDTO stockDTO){
        stockDTO.genCost();
        return stockService.createStock(stockDTO);
    }

    @GetMapping("/all")
    public List<StockDTO> getAllStock(){
        return stockService.getAllStock();
    }

    @GetMapping("/{id}")
    public StockDTO findById(@PathVariable long id){
        return stockService.findById(id);
    }

    @PutMapping("/update")
    public StockDTO updateStock(@RequestBody @Validated StockDTO stockDTO){
        stockDTO.genCost();
        return stockService.updateStock(stockDTO);
    }


}
