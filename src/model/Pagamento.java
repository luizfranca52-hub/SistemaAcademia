package model;

import exception.PagamentoInvalidoException;

import java.time.LocalDate;

public abstract class Pagamento {
    private int id;
    private Matricula matricula;
    private double valor;
    private LocalDate dataPagamento;

    public Pagamento(int id, Matricula matricula, double valor, LocalDate dataPagamento) {
        if (valor <= 0) {
            throw new PagamentoInvalidoException("O valor do pagamento precisa ser maior que zero.");
        }
        this.id = id;
        this.matricula = matricula;
        this.valor = valor;
        this.dataPagamento = dataPagamento;
    }

    // cada forma de pagamento calcula o valor final do seu jeito (polimorfismo)
    public abstract double calcularValorFinal();

    // o aluno vem pela matrícula, não precisa guardar de novo
    public Aluno getAluno() {
        return matricula.getAluno();
    }

    public int getId() {
        return id;
    }

    public Matricula getMatricula() {
        return matricula;
    }

    public double getValor() {
        return valor;
    }

    public LocalDate getDataPagamento() {
        return dataPagamento;
    }
}
