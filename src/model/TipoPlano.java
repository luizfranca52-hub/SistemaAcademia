package model;

public enum TipoPlano {
    MENSAL(1),
    TRIMESTRAL(3),
    SEMESTRAL(6),
    ANUAL(12);

    private final int meses;

    TipoPlano(int meses) {
        this.meses = meses;
    }

    public int getMeses() {
        return meses;
    }
}
