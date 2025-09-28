package br.com.fiap.otmav.service;

import br.com.fiap.otmav.domain.endereco.*;
import br.com.fiap.otmav.exception.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EnderecoService {

    @Autowired
    private final EnderecoRepository enderecoRepository;

    @Transactional
    public ReadEnderecoDto create(CreateEnderecoDto dto) {
        Endereco e = new Endereco();
        e.setNumero(dto.numero());
        e.setEstado(dto.estado());
        e.setCodigoPais(dto.codigoPais());
        e.setCodigoPostal(dto.codigoPostal());
        e.setComplemento(dto.complemento());
        e.setRua(dto.rua());

        return new ReadEnderecoDto(enderecoRepository.save(e));
    }

    public Page<ReadEnderecoDto> findAllFiltered(Integer numero, String estado, String codigoPais, String codigoPostal, String rua, Pageable pageable) {
        return enderecoRepository.findAllFiltered(numero, estado, codigoPais, codigoPostal, rua, pageable)
                .map(ReadEnderecoDto::new);
    }

    public ReadEnderecoDto findById(Long id) {
        return enderecoRepository.findById(id)
                .map(ReadEnderecoDto::new)
                .orElseThrow(() -> new NotFoundException("Endereco nao encontrado com id: " + id));
    }

    @Transactional
    public ReadEnderecoDto update(Long id, UpdateEnderecoDto dto) {
        Endereco e = enderecoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Endereco nao encontrado com id: " + id));

        if (dto.numero() != null) e.setNumero(dto.numero());
        if (dto.estado() != null) e.setEstado(dto.estado());
        if (dto.codigoPais() != null) e.setCodigoPais(dto.codigoPais());
        if (dto.codigoPostal() != null) e.setCodigoPostal(dto.codigoPostal());
        if (dto.complemento() != null) e.setComplemento(dto.complemento());
        if (dto.rua() != null) e.setRua(dto.rua());

        return new ReadEnderecoDto(enderecoRepository.save(e));
    }

    @Transactional
    public void delete(Long id) {
        if (!enderecoRepository.existsById(id)) {
            throw new NotFoundException("Endereco nao encontrado com id: " + id);
        }
        enderecoRepository.deleteById(id);
    }
}
