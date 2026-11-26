package model;

import exception.PagamentoInvalidoException;

import java.time.LocalDate;

public class PagamentoCartao extends Pagamento {
    private int parcelas;
    private boolean credito; // true = crédito, false = débito

    public PagamentoCartao(int id, Matricula matricula, double valor, LocalDate dataPagamento, int parcelas, boolean credito) {
        super(id, matricula, valor, dataPagamento);

        if (!credito && parcelas != 1) {
            throw new PagamentoInvalidoException("Pagamento no débito é só à vista (1 parcela).");
        }
        int mesesDoPlano = matricula.getPlano().calcularDuracaoMeses();
        if (parcelas < 1 || parcelas > mesesDoPlano) {
            throw new PagamentoInvalidoException("Parcelas devem ser de 1 até " + mesesDoPlano + " (meses do plano).");
        }
        this.parcelas = parcelas;
        this.credito = credito;
    }

    // crédito acima de 3x tem 10% de juros
    @Override
    public double calcularValorFinal() {
        if (credito && parcelas > 3) {
            return getValor() * 1.10;
        }
        return getValor();
    }

    public double calcularValorParcela() {
        return calcularValorFinal() / parcelas;
    }

    public int getParcelas() {
        return parcelas;
    }

    public boolean isCredito() {
        return credito;
    }
}
