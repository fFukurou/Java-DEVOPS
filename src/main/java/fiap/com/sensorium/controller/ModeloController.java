package fiap.com.sensorium.controller;

import fiap.com.sensorium.domain.modelo.Modelo;
import fiap.com.sensorium.domain.modelo.ModeloRepository;
import fiap.com.sensorium.domain.modelo.ReadModeloDto;
import fiap.com.sensorium.service.ModeloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("modelos")
public class ModeloController {

    @Autowired
    private ModeloService modeloService;

    @Autowired
    private ModeloRepository modeloRepository;

    // GET
    @GetMapping
    public ResponseEntity<Page<ReadModeloDto>> listAll(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String combustivel,
            @PageableDefault(size = 10, sort = "nome") Pageable pageable
    ) {
        Page<Modelo> modelos = modeloService.filterQuery(
                nome, combustivel, pageable);

        return ResponseEntity.ok(modelos.map(ReadModeloDto::new));
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<ReadModeloDto> getById(@PathVariable Long id) {
        return modeloService.findById(id)
                .map(modelo -> ResponseEntity.ok(new ReadModeloDto(modelo)))
                .orElse(ResponseEntity.notFound().build());
    }

    // CLEAR CACHE
    @PostMapping("/clear-cache")
    public ResponseEntity<Void> clearModeloCache() {
        modeloService.clearCache();
        return ResponseEntity.noContent().build();
    }
}
