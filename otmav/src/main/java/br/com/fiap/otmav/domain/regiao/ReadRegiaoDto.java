package br.com.fiap.otmav.domain.regiao;

public record ReadRegiaoDto(
        Long id,
        String localizacaoWkt,
        Double area
) {
    public ReadRegiaoDto(Regiao r) {
        this(r.getId(), r.getLocalizacaoWkt(), r.getArea());
    }
}
