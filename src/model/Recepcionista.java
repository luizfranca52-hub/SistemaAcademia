package model;

import java.time.LocalDate;

public class Recepcionista extends Funcionario {
    private Turno turno;
    private int metaMatriculas;
    private int matriculasRealizadas;

    public Recepcionista(int id, String nome, String cpf, LocalDate dataNascimento, String telefone,
                         double salarioBase, LocalDate dataAdmissao,
                         Turno turno, int metaMatriculas, int matriculasRealizadas) {
        super(id, nome, cpf, dataNascimento, telefone, salarioBase, dataAdmissao);
        this.turno = turno;
        this.metaMatriculas = metaMatriculas;
        this.matriculasRealizadas = matriculasRealizadas;
    }

    // conta mais uma matrícula pra meta
    public void registrarMatricula() {
        matriculasRealizadas++;
    }

    public boolean bateuMeta() {
        return matriculasRealizadas >= metaMatriculas;
    }

    // base + 15% se for noite + 300 se bater a meta
    @Override
    public double calcularSalario() {
        double salario = getSalarioBase();
        if (turno == Turno.NOITE) {
            salario = salario + getSalarioBase() * 0.15;
        }
        if (bateuMeta()) {
            salario = salario + 300.0;
        }
        return salario;
    }

    @Override
    public void exibirResumo() {
        System.out.println("O nome da recepcionista é: " + this.nome + " Turno: " + this.turno + " \nPara entrar em contato: " + this.telefone);
    }

    public Turno getTurno() {
        return turno;
    }

    public void setTurno(Turno turno) {
        this.turno = turno;
    }

    public int getMetaMatriculas() {
        return metaMatriculas;
    }

    public void setMetaMatriculas(int metaMatriculas) {
        this.metaMatriculas = metaMatriculas;
    }

    public int getMatriculasRealizadas() {
        return matriculasRealizadas;
    }
}
