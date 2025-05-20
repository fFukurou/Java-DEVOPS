package fiap.com.sensorium.domain.patio;

public record ReadPatioDto(
        Long id,
        Integer totalMotos,
        Integer capacidadeMoto,
        Integer areaPatio,
        double ocupacaoPercentual
) {
    public ReadPatioDto(Patio patio) {
        this(
                patio.getId(),
                patio.getTotalMotos(),
                patio.getCapacidade(),
                patio.getArea(),
                calculateOcupacao(patio)
        );
    }

    private static double calculateOcupacao(Patio patio) {
        if (patio.getCapacidade() == 0) return 0;
        return ((double) patio.getTotalMotos() / patio.getCapacidade()) * 100;
    }


}
