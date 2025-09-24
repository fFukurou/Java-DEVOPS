package br.com.fiap.otmav.controller;

import br.com.fiap.otmav.domain.endereco.*;
import br.com.fiap.otmav.service.EnderecoService;
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
@RequestMapping("/api/enderecos")
@Tag(name = "Enderecos", description = "CRUD for addresses")
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;

    @Operation(summary = "Create address")
    @PostMapping
    public ResponseEntity<ReadEnderecoDto> create(@RequestBody @Valid CreateEnderecoDto dto, UriComponentsBuilder uriBuilder) {
        ReadEnderecoDto created = enderecoService.create(dto);
        URI uri = uriBuilder.path("/api/enderecos/{id}").buildAndExpand(created.id()).toUri();
        return ResponseEntity.created(uri).body(created);
    }

    @Operation(summary = "List addresses")
    @ApiResponse(responseCode = "200", description = "List returned")
    @GetMapping
    public ResponseEntity<Page<ReadEnderecoDto>> findAll(
            @Parameter(description = "Number (exact)", example = "123") @RequestParam(required = false) Integer numero,
            @Parameter(description = "State (partial)", example = "SP") @RequestParam(required = false) String estado,
            @Parameter(description = "Country code", example = "BR") @RequestParam(required = false) String codigoPais,
            @Parameter(description = "Postal code", example = "01234-567") @RequestParam(required = false) String codigoPostal,
            @Parameter(description = "Street name (partial)", example = "Main") @RequestParam(required = false) String rua,
            @Parameter(hidden = true) @PageableDefault(size = 10, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {

        return ResponseEntity.ok(enderecoService.findAllFiltered(numero, estado, codigoPais, codigoPostal, rua, pageable));
    }

    @Operation(summary = "Get address by ID")
    @GetMapping("/{id}")
    public ResponseEntity<ReadEnderecoDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(enderecoService.findById(id));
    }

    @Operation(summary = "Update address")
    @PutMapping("/{id}")
    public ResponseEntity<ReadEnderecoDto> update(@PathVariable Long id, @RequestBody @Valid UpdateEnderecoDto dto) {
        return ResponseEntity.ok(enderecoService.update(id, dto));
    }

    @Operation(summary = "Delete address")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        enderecoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
