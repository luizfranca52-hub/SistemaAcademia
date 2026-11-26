package model;

import java.time.LocalDate;

public class PagamentoPix extends Pagamento {
    private double percentualDesconto;

    public PagamentoPix(int id, Matricula matricula, double valor, LocalDate dataPagamento, double percentualDesconto) {
        super(id, matricula, valor, dataPagamento);
        this.percentualDesconto = percentualDesconto;
    }

    // pix tem desconto
    @Override
    public double calcularValorFinal() {
        return getValor() - getValor() * percentualDesconto / 100;
    }

    public double getPercentualDesconto() {
        return percentualDesconto;
    }
}
