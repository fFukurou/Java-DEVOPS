package fiap.com.sensorium.controller;

import fiap.com.sensorium.domain.modelo.*;
import fiap.com.sensorium.infra.exception.EntityNotFoundException;
import fiap.com.sensorium.service.ModeloService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

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
    public ReadModeloDto getById(@PathVariable Long id) {
        Modelo modelo = modeloService.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Modelo não encontrado com ID: " + id));

        return new ReadModeloDto(modelo);
    }

    // POST
    @PostMapping
    @Transactional
    public ResponseEntity<ReadModeloDto> create(@RequestBody @Valid CreateModeloDto dto) {
        Modelo modelo = new Modelo();
        modeloService.copyDtoToModel(dto, modelo);

        Modelo saved = modeloService.create(modelo);
        return ResponseEntity
                .created(URI.create("/modelos/" + saved.getId()))
                .body(new ReadModeloDto(saved));
    }


    // PUT
    @PutMapping("/{id}")
    @Transactional
    public ReadModeloDto update(
            @PathVariable Long id,
            @RequestBody @Valid UpdateModeloDto dto
    ) {
        Modelo modelo = modeloService.findByIdOrThrow(id);
        modeloService.copyDtoToModel(dto, modelo);

        return new ReadModeloDto(modeloService.update(modelo));
    }

    // DELETE
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        modeloService.findByIdOrThrow(id);
        modeloService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // CLEAR CACHE
    @PostMapping("/clear-cache")
    public ResponseEntity<Void> clearModeloCache() {
        modeloService.clearCache();
        return ResponseEntity.noContent().build();
    }
}
