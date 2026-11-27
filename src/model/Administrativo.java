package model;

import java.time.LocalDate;

public class Administrativo extends Funcionario {
    private String cargo;

    public Administrativo(int id, String nome, String cpf, LocalDate dataNascimento, String telefone,
                          double salarioBase, LocalDate dataAdmissao,
                          String cargo) {
        super(id, nome, cpf, dataNascimento, telefone, salarioBase, dataAdmissao);
        this.cargo = cargo;
    }

    // base + 20% de gratificação
    @Override
    public double calcularSalario() {
        return getSalarioBase() * 1.20;
    }

    @Override
    public void exibirResumo() {
        System.out.println("O nome do funcionario administrativo é: " + this.nome + " Ele nasceu em: " + this.dataNascimento + " \nPara entrar em contato: " + this.telefone);
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
}
