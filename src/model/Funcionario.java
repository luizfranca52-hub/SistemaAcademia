package model;

import java.time.LocalDate;

public abstract class Funcionario extends Pessoa implements Autenticavel {
    private double salarioBase;
    private LocalDate dataAdmissao;
    private String login;
    private String senha;

    public Funcionario(int id, String nome, String cpf, LocalDate dataNascimento, String telefone,
                       double salarioBase, LocalDate dataAdmissao, String login, String senha) {
        super(id, nome, cpf, dataNascimento, telefone);
        this.salarioBase = salarioBase;
        this.dataAdmissao = dataAdmissao;
        this.login = login;
        this.senha = senha;
    }

    // cada um calcula do seu jeito (polimorfismo)
    public abstract double calcularSalario();

    @Override
    public boolean autenticar(String login, String senha) {
        return this.login.equals(login) && this.senha.equals(senha);
    }

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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
