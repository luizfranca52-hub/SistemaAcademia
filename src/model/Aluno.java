package model;

import java.time.LocalDate;

public class Aluno extends Pessoa implements Autenticavel{
    String login;
    String senha;

    public Aluno(int id, String nome, String cpf, LocalDate dataNascimento, String telefone, String login, String senha){
        super(id, nome, cpf, dataNascimento, telefone);
        this.login = login;
        this.senha = senha;
    }

    public void exibirResumo(){
        System.out.println("O nome do aluno é: " + this.nome + " Ele nasceu em: " + this.dataNascimento + " \nPara entrar em contato: "+ this.telefone);
    }

    @Override
    public boolean autenticar(String login, String senha) { return this.login.equals(login) && this.senha.equals(senha);}

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
