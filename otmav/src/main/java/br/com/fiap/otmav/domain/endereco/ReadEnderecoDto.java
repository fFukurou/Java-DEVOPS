package br.com.fiap.otmav.domain.endereco;

public record ReadEnderecoDto(
        Long id,
        Integer numero,
        String estado,
        String codigoPais,
        String codigoPostal,
        String complemento,
        String rua
) {
    public ReadEnderecoDto(Endereco e) {
        this(e.getId(), e.getNumero(), e.getEstado(), e.getCodigoPais(), e.getCodigoPostal(), e.getComplemento(), e.getRua());
    }
}
