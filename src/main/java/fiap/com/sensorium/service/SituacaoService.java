package fiap.com.sensorium.service;


import fiap.com.sensorium.domain.situacao.Situacao;
import fiap.com.sensorium.domain.situacao.SituacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SituacaoService {

    @Autowired
    private SituacaoRepository situacaoRepository;

    @Cacheable(value = "situacoes", key = "{#status, #pageable.pageNumber, #pageable.pageSize, #pageable.sort}")
    public Page<Situacao> findByStatusIgnoreCase(String status, Pageable pageable) {
        return situacaoRepository.findByStatusIgnoreCase(status, pageable);
    }

    @Cacheable(value = "situacoes", key = "'all_' + #pageable.pageNumber + '_' + #pageable.pageSize + '_' + #pageable.sort.toString()")
    public Page<Situacao> findAllIgnoreCase(Pageable pageable) {
        return situacaoRepository.findAll(pageable);
    }

    @CacheEvict(value = {"situacoes"}, allEntries = true)
    public void clearCache() {

    }
}
