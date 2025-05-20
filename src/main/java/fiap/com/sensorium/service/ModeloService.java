package fiap.com.sensorium.service;

import fiap.com.sensorium.domain.modelo.Modelo;
import fiap.com.sensorium.domain.modelo.ModeloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ModeloService {

    @Autowired
    private ModeloRepository modeloRepository;

    public Page<Modelo> filterQuery(
            String nomeModelo,
            String combustivel,
            Pageable pageable
    ) {
        if (nomeModelo != null && combustivel != null) {
            return modeloRepository.findByNomeContainingIgnoreCaseAndCombustivelIgnoreCase(
                    nomeModelo, combustivel, pageable);
        } else if (nomeModelo != null) {
            return modeloRepository.findByNomeContainingIgnoreCase(nomeModelo, pageable);
        } else if (combustivel != null) {
            return modeloRepository.findByCombustivelIgnoreCase(combustivel, pageable);
        }
        return modeloRepository.findAll(pageable);
    }
}