package model;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AulaColetiva {
    private String modalidade;
    private Instrutor instrutor;
    private List<DayOfWeek> dias;
    private LocalTime horario;
    private int vagas;
    private List<Aluno> alunos;

    public AulaColetiva(String modalidade, Instrutor instrutor, List<DayOfWeek> dias, LocalTime horario, int vagas) {
        this.modalidade = modalidade;
        this.instrutor = instrutor;
        this.dias = new ArrayList<>(dias);
        this.horario = horario;
        this.vagas = vagas;
        this.alunos = new ArrayList<>();
    }

    public boolean temVaga(){
        return alunos.size() < vagas;
    }

    public boolean matricularAluno(Aluno aluno){
        if(!temVaga() || alunos.contains(aluno)){
            return false;
        }
        alunos.add(aluno);
        return true;
    }




    public String getModalidade() {
        return modalidade;
    }

    public void setModalidade(String modalidade) {
        this.modalidade = modalidade;
    }

    public Instrutor getInstrutor() {
        return instrutor;
    }

    public void setInstrutor(Instrutor instrutor) {
        this.instrutor = instrutor;
    }

    public List<DayOfWeek> getDias() {
        return dias;
    }

    public void setDias(List<DayOfWeek> dias) {
        this.dias = dias;
    }

    public LocalTime getHorario() {
        return horario;
    }

    public void setHorario(LocalTime horario) {
        this.horario = horario;
    }

    public int getVagas() {
        return vagas;
    }

    public void setVagas(int vagas) {
        this.vagas = vagas;
    }

    public List<Aluno> getAlunos() {
        return alunos;
    }

    public void setAlunos(List<Aluno> alunos) {
        this.alunos = alunos;
    }
}
