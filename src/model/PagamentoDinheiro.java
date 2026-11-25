package model;

import exception.PagamentoInvalidoException;

import java.time.LocalDate;

public class PagamentoDinheiro extends Pagamento {
    private double valorRecebido;

    public PagamentoDinheiro(int id, Matricula matricula, double valor, LocalDate dataPagamento, double valorRecebido) {
        super(id, matricula, valor, dataPagamento);
        if (valorRecebido < valor) {
            throw new PagamentoInvalidoException("O valor recebido é menor que o valor a pagar.");
        }
        this.valorRecebido = valorRecebido;
    }

    // dinheiro n tem desconto nem juros
    @Override
    public double calcularValorFinal() {
        return getValor();
    }

    public double calcularTroco() {
        return valorRecebido - calcularValorFinal();
    }

    public double getValorRecebido() {
        return valorRecebido;
    }
}
