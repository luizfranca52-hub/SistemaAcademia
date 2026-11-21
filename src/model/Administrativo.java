package model;

import java.time.LocalDate;

public class Administrativo extends Pessoa implements Autenticavel{
    private String cargo;
    private String login;
    private String senha;


    public Administrativo(int id, String nome, String cpf, LocalDate dataNascimento, String telefone, String cargo, String login, String senha) {
        super(id, nome, cpf, dataNascimento, telefone);
        this.cargo = cargo;
        this.login = login;
        this.senha = senha;
    }

    public void exibirResumo(){
        System.out.println("O nome do funcionario administrativo é: " + this.nome + " Ele nasceu em: " + this.dataNascimento + " \nPara entrar em contato: "+ this.telefone);
    }

    public boolean autenticar(String login, String senha) { return this.login.equals(login) && this.senha.equals(senha);}

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        cargo = cargo;
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
