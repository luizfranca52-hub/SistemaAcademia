package model;

import java.time.LocalDate;

public class Instrutor extends Pessoa implements Autenticavel {
    private String cref;
    private String login;
    private String senha;
    private double salario;

    public Instrutor (String cref, String login, String senha, double salario, int id, String nome, String cpf, LocalDate dataNascimento, String telefone){
        super(id, nome, cpf, dataNascimento, telefone);
        this.cref = cref;
        this.login = login;
        this.senha = senha;
        this.salario = salario;
    }

    public void exibirResumo(){
        System.out.println("O nome do instrutor é: " + this.nome + " Ele nasceu em: " + this.dataNascimento + " \nPara entrar em contato: "+ this.telefone);
    }

    public boolean autenticar(String login, String senha){
        if(login == this.login && senha == this.senha){
            return true;
        }
        return false;
    }

    public String getCref() {
        return cref;
    }

    public void setCref(String cref) {
        this.cref = cref;
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

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }
}
