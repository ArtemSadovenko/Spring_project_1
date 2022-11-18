package lpnu.service;

import lpnu.dto.CryptoDTO;
import lpnu.entity.items.Crypto;
import org.springframework.aop.target.LazyInitTargetSource;

import java.util.ArrayList;
import java.util.List;

public interface CryptoService {
     List<CryptoDTO> getAllCrypto();
     CryptoDTO createCrypto(CryptoDTO cryptoDTO);
     CryptoDTO updateCrypto(CryptoDTO cryptoDTO);
     CryptoDTO findById(long id);
}