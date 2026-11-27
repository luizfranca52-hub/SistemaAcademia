package model;

import java.time.LocalDate;

public abstract class Funcionario extends Pessoa {
    private double salarioBase;
    private LocalDate dataAdmissao;

    public Funcionario(int id, String nome, String cpf, LocalDate dataNascimento, String telefone,
                       double salarioBase, LocalDate dataAdmissao) {
        super(id, nome, cpf, dataNascimento, telefone);
        this.salarioBase = salarioBase;
        this.dataAdmissao = dataAdmissao;
    }

    // cada um calcula do seu jeito (polimorfismo)
    public abstract double calcularSalario();

    public double getSalarioBase() {
        return salarioBase;
    }

    public void setSalarioBase(double salarioBase) {
        this.salarioBase = salarioBase;
    }

    public LocalDate getDataAdmissao() {
        return dataAdmissao;
    }

    public void setDataAdmissao(LocalDate dataAdmissao) {
        this.dataAdmissao = dataAdmissao;
    }
}
