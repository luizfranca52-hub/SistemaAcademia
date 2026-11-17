package model;

import java.time.LocalDate;

public class Aluno extends Pessoa{
    public Aluno(int id, String nome, String cpf, LocalDate dataNascimento, String telefone){
        super(id, nome, cpf, dataNascimento, telefone);
    }

    public void exibirResumo(){
        System.out.println("O nome do aluno é: " + this.nome + " Ele nasceu em: " + this.dataNascimento + " \nPara entrar em contato: "+ this.telefone);
    }
}
