package model;

import java.time.LocalDate;

public class Pagamento {
    private int id;
    private Aluno aluno;
    private Matricula matricula;
    private double valor;
    private LocalDate dataPagamento;
    private FormaPagamento forma;

    public Pagamento(int id, Aluno aluno, Matricula matricula, double valor, LocalDate dataPagamento, FormaPagamento forma) {
        this.id = id;
        this.aluno = aluno;
        this.matricula = matricula;
        this.valor = valor;
        this.dataPagamento = dataPagamento;
        this.forma = forma;
    }

    public Aluno getAluno() {
        return aluno;
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

    public FormaPagamento getForma() {
        return forma;
    }

    public void setForma(FormaPagamento forma) {
        this.forma = forma;
    }
}
