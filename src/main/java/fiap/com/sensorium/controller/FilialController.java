package fiap.com.sensorium.controller;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import fiap.com.sensorium.domain.filial.Filial;
import fiap.com.sensorium.domain.filial.FilialRepository;
import fiap.com.sensorium.domain.filial.ReadFilialDto;
import fiap.com.sensorium.infra.exception.EntityNotFoundException;
import fiap.com.sensorium.service.FilialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("filiais")
public class FilialController {

    @Autowired
    private FilialRepository filialRepository;
    @Autowired
    private FilialService filialService;

    // GET
    @GetMapping
    public ResponseEntity<Page<ReadFilialDto>> listAll(
            @RequestParam(required = false) String nomeFilial,
            @RequestParam(required = false) String estado,
            @PageableDefault(size = 10, sort = "nomeFilial") Pageable pageable
    ) {
        Page<Filial> filiais = filialService.filterQuery(
                nomeFilial, estado, pageable);
        return ResponseEntity.ok(filiais.map(ReadFilialDto::new));
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ReadFilialDto getById(@PathVariable Long id) {
        return filialService.findById(id)
                .map(ReadFilialDto::new)
                .orElseThrow(() -> new EntityNotFoundException("Filial não encontrada com ID: " + id));
    }
    // CLEAR CACHE
    @PostMapping("/clear-cache")
    public ResponseEntity<Void> clearCache() {
        filialService.clearCache();
        return ResponseEntity.noContent().build();
    }
}