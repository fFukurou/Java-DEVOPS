package br.com.fiap.otmav.domain.endereco;

public record ReadEnderecoDto(
        Long id,
        String rua,
        String cidade
) {
    public ReadEnderecoDto(Endereco e) {
        this(e.getId(), e.getRua(), e.getCidade());
    }
}
