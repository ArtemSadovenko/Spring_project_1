package lpnu.mapper;

import lpnu.dto.CryptoDTO;
import lpnu.entity.items.Crypto;

public class CryptoMapper {
    public static Crypto toEntity(CryptoDTO cryptoDTO){
        Crypto crypto = new Crypto();

        crypto.setId(cryptoDTO.getId());
        crypto.setAmount(cryptoDTO.getAmount());
        crypto.setCost(cryptoDTO.getCost());
        crypto.setCapitalisation(cryptoDTO.getCapitalisation());
        crypto.setTitle(cryptoDTO.getTitle());

        return crypto;
    }

    public static CryptoDTO toDTO(Crypto crypto){
        CryptoDTO cryptoDTO = new CryptoDTO();

        cryptoDTO.setId(crypto.getId());
        cryptoDTO.setAmount(crypto.getAmount());
        cryptoDTO.setCost(crypto.getCost());
        cryptoDTO.setCapitalisation(crypto.getCapitalisation());
        cryptoDTO.setTitle(crypto.getTitle());

        return cryptoDTO;
    }
}
