package fiap.com.sensorium.controller;

import fiap.com.sensorium.domain.moto.Moto;
import fiap.com.sensorium.domain.moto.ReadMotoDto;
import fiap.com.sensorium.domain.motorista.Motorista;
import fiap.com.sensorium.domain.motorista.MotoristaRepository;
import fiap.com.sensorium.domain.motorista.ReadMotoristaDto;
import fiap.com.sensorium.infra.exception.EntityNotFoundException;
import fiap.com.sensorium.service.MotoristaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("motoristas")
public class MotoristaController {

    @Autowired
    private MotoristaRepository motoristaRepository;

    @Autowired
    private MotoristaService motoristaService;


    // GET
    @GetMapping
    public ResponseEntity<Page<ReadMotoristaDto>> listAll(
            @RequestParam(required = false) String plano,
            @PageableDefault(size = 10, sort = "id") Pageable pageable
    ) {
        return ResponseEntity.ok(
                motoristaService.filterQuery(plano, pageable)
                        .map(ReadMotoristaDto::new)
        );
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ReadMotoristaDto getById(@PathVariable Long id) {
        return motoristaService.findById(id)
                .map(ReadMotoristaDto::new)
                .orElseThrow(() -> new EntityNotFoundException("Motorista não encontrado com ID: " + id));
    }

    // CLEAR CACHE
    @PostMapping("/clear-cache")
    public ResponseEntity<Void> clearCache() {
        motoristaService.clearCache();
        return ResponseEntity.noContent().build();
    }
}
