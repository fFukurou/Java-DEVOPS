package fiap.com.sensorium.service;

import fiap.com.sensorium.domain.filial.Filial;
import fiap.com.sensorium.domain.filial.FilialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class FilialService {

    @Autowired
    private FilialRepository filialRepository;

    public Page<Filial> filterQuery(
            String nomeFilial,
            String estado,
            Pageable pageable
    ) {
        if (nomeFilial != null && estado != null) {
            return filialRepository.findByNomeFilialAndEstado(nomeFilial, estado, pageable);
        } else if (nomeFilial != null) {
            return filialRepository.findByNomeFilialContainingIgnoreCase(nomeFilial, pageable);
        } else if (estado != null) {
            return filialRepository.findByEstadoIgnoreCase(estado, pageable);
        }
        return filialRepository.findAll(pageable);
    }

}