package lpnu.service.impl;

import lpnu.dto.CryptoDTO;
import lpnu.entity.items.Crypto;
import lpnu.mapper.CryptoMapper;
import lpnu.repository.CryptoRepository;
import lpnu.service.CryptoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CryptoServiceImpl implements CryptoService {
    @Autowired
    CryptoRepository cryptoRepository;

    @Override
    public List<CryptoDTO> getAllCrypto() {
        return cryptoRepository.getAllCrypto().stream()
                .map(CryptoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CryptoDTO createCrypto(CryptoDTO cryptoDTO) {
        Crypto crypto = CryptoMapper.toEntity(cryptoDTO);

        cryptoRepository.save(crypto);

        return CryptoMapper.toDTO(crypto);
    }

    @Override
    public CryptoDTO updateCrypto(CryptoDTO cryptoDTO) {
        Crypto crypto = CryptoMapper.toEntity(cryptoDTO);

        cryptoRepository.update(crypto);

        return CryptoMapper.toDTO(crypto);
    }

    @Override
    public CryptoDTO findById(long id) {
        return CryptoMapper.toDTO(cryptoRepository.findById(id));
    }
}
