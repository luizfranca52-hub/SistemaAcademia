package model;

import java.time.LocalDate;

public class Administrativo extends Pessoa implements Autenticavel{
    private String Cargo;
    private String login;
    private String senha;


    public Administrativo(int id, String nome, String cpf, LocalDate dataNascimento, String telefone) {
        super(id, nome, cpf, dataNascimento, telefone);
    }

    public void exibirResumo(){
        System.out.println("O nome do funcionario administrativo é: " + this.nome + " Ele nasceu em: " + this.dataNascimento + " \nPara entrar em contato: "+ this.telefone);
    }

    public boolean autenticar(String login, String senha){
        if(login == this.login && senha == this.senha){
            return true;
        }
        return false;
    }

    public String getCargo() {
        return Cargo;
    }

    public void setCargo(String cargo) {
        Cargo = cargo;
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
