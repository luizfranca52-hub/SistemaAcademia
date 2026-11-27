package model;

import exception.RegraNegocioException;

import java.time.LocalDate;
import java.time.Period;

public class Aluno extends Pessoa {
    private String responsavel; // nome do responsável, obrigatório se for menor de idade

    public Aluno(int id, String nome, String cpf, LocalDate dataNascimento, String telefone, String responsavel){
        super(id, nome, cpf, dataNascimento, telefone);
        this.responsavel = responsavel;

        if (ehMenorDeIdade() && (responsavel == null || responsavel.isBlank())) {
            throw new RegraNegocioException("Aluno menor de idade precisa de um responsável.");
        }
    }

    public boolean ehMenorDeIdade() {
        int idade = Period.between(dataNascimento, LocalDate.now()).getYears();
        return idade < 18;
    }

    public void exibirResumo(){
        System.out.println("O nome do aluno é: " + this.nome + " Ele nasceu em: " + this.dataNascimento + " \nPara entrar em contato: "+ this.telefone);
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }
}
