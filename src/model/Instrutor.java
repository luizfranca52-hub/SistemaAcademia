package model;

import java.time.LocalDate;

public class Instrutor extends Funcionario {
    private String cref;
    private int cargaHorariaSemanal;

    public Instrutor(int id, String nome, String cpf, LocalDate dataNascimento, String telefone,
                     double salarioBase, LocalDate dataAdmissao, String login, String senha,
                     String cref, int cargaHorariaSemanal) {
        super(id, nome, cpf, dataNascimento, telefone, salarioBase, dataAdmissao, login, senha);
        this.cref = cref;
        this.cargaHorariaSemanal = cargaHorariaSemanal;
    }

    // salário base + R$ 30 por hora que passar de 20h semanais
    @Override
    public double calcularSalario() {
        double salario = getSalarioBase();
        if (cargaHorariaSemanal > 20) {
            int horasExtras = cargaHorariaSemanal - 20;
            salario = salario + horasExtras * 30.0;
        }
        return salario;
    }

    @Override
    public void exibirResumo() {
        System.out.println("O nome do instrutor é: " + this.nome + " Ele nasceu em: " + this.dataNascimento + " \nPara entrar em contato: " + this.telefone);
    }

    public String getCref() {
        return cref;
    }

    public void setCref(String cref) {
        this.cref = cref;
    }

    public int getCargaHorariaSemanal() {
        return cargaHorariaSemanal;
    }

    public void setCargaHorariaSemanal(int cargaHorariaSemanal) {
        this.cargaHorariaSemanal = cargaHorariaSemanal;
    }
}
