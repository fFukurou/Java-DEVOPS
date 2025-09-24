package br.com.fiap.otmav.controller;

import br.com.fiap.otmav.domain.setor.*;
import br.com.fiap.otmav.service.SetorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/setores")
@Tag(name = "Setores", description = "CRUD for setores")
public class SetorController {

    @Autowired
    private SetorService setorService;

    @Operation(summary = "Create setor")
    @PostMapping
    public ResponseEntity<ReadSetorDto> create(@RequestBody @Valid CreateSetorDto dto, UriComponentsBuilder uriBuilder) {
        ReadSetorDto created = setorService.create(dto);
        URI uri = uriBuilder.path("/api/setores/{id}").buildAndExpand(created.id()).toUri();
        return ResponseEntity.created(uri).body(created);
    }

    @Operation(summary = "List setores")
    @ApiResponse(responseCode = "200", description = "List returned")
    @GetMapping
    public ResponseEntity<Page<ReadSetorDto>> findAll(
            @Parameter(description = "Qtd moto (exact)") @RequestParam(required = false) Integer qtdMoto,
            @Parameter(description = "Capacidade (exact)") @RequestParam(required = false) Integer capacidade,
            @Parameter(description = "Name (partial)") @RequestParam(required = false) String nome,
            @Parameter(description = "Color (exact, case-insensitive)") @RequestParam(required = false) String cor,
            @Parameter(description = "Patio id") @RequestParam(required = false) Long patioId,
            @Parameter(description = "Regiao id") @RequestParam(required = false) Long regiaoId,
            @Parameter(hidden = true) @PageableDefault(size = 10, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {

        return ResponseEntity.ok(setorService.findAllFiltered(qtdMoto, capacidade, nome, cor, patioId, regiaoId, pageable));
    }

    @Operation(summary = "Get setor by ID")
    @GetMapping("/{id}")
    public ResponseEntity<ReadSetorDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(setorService.findById(id));
    }

    @Operation(summary = "Update setor")
    @PutMapping("/{id}")
    public ResponseEntity<ReadSetorDto> update(@PathVariable Long id, @RequestBody @Valid UpdateSetorDto dto) {
        return ResponseEntity.ok(setorService.update(id, dto));
    }

    @Operation(summary = "Delete setor")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        setorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
