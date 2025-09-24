package br.com.fiap.otmav.controller;

import br.com.fiap.otmav.domain.regiao.*;
import br.com.fiap.otmav.service.RegiaoService;
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
@RequestMapping("/api/regioes")
@Tag(name = "Regioes", description = "CRUD for regions")
public class RegiaoController {

    @Autowired
    private RegiaoService regiaoService;

    @Operation(summary = "Create region (WKT localizacao)")
    @PostMapping
    public ResponseEntity<ReadRegiaoDto> create(@RequestBody @Valid CreateRegiaoDto dto, UriComponentsBuilder uriBuilder) {
        ReadRegiaoDto created = regiaoService.create(dto);
        URI uri = uriBuilder.path("/api/regioes/{id}").buildAndExpand(created.id()).toUri();
        return ResponseEntity.created(uri).body(created);
    }

    @Operation(summary = "List regions (optional filters)")
    @ApiResponse(responseCode = "200", description = "List returned")
    @GetMapping
    public ResponseEntity<Page<ReadRegiaoDto>> findAll(
            @Parameter(description = "Area (exact)") @RequestParam(required = false) Double area,
            @Parameter(description = "Text search on WKT (optional)") @RequestParam(required = false) String searchWkt,
            @Parameter(hidden = true) @PageableDefault(size = 10, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {

        return ResponseEntity.ok(regiaoService.findAllFiltered(area, searchWkt, pageable));
    }

    @Operation(summary = "Get region by ID")
    @GetMapping("/{id}")
    public ResponseEntity<ReadRegiaoDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(regiaoService.findById(id));
    }

    @Operation(summary = "Update region")
    @PutMapping("/{id}")
    public ResponseEntity<ReadRegiaoDto> update(@PathVariable Long id, @RequestBody @Valid UpdateRegiaoDto dto) {
        return ResponseEntity.ok(regiaoService.update(id, dto));
    }

    @Operation(summary = "Delete region")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        regiaoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
