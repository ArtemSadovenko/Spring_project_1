package lpnu.resources;

import lpnu.dto.CryptoDTO;
import lpnu.service.CryptoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/crypto")
public class CryptoResource {

    @Autowired
    private CryptoService cryptoService;

    @PostMapping("/create")
    public CryptoDTO createCrypto(@RequestBody @Validated CryptoDTO cryptoDTO){
        cryptoDTO.genCost();
        return cryptoService.createCrypto(cryptoDTO);
    }

    @GetMapping("/all")
    public List<CryptoDTO> getAllCrypto(){
        return cryptoService.getAllCrypto();
    }

    @PutMapping("/update")
    public CryptoDTO updateCrypto(@RequestBody @Validated CryptoDTO cryptoDTO){
        cryptoDTO.genCost();
        return cryptoService.updateCrypto(cryptoDTO);
    }

    @GetMapping("/{id}")
    public CryptoDTO findById(@PathVariable long id){
        return cryptoService.findById(id);
    }


}
