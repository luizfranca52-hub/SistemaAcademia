package controller;

import model.Aluno;
import model.AulaColetiva;
import model.Instrutor;
import repository.AlunoRepository;
import repository.AulaColetivaRepository;
import repository.InstrutorRepository;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AulaColetivaController {
    private AulaColetivaRepository aulaRepo;
    private InstrutorRepository instrutorRepo;
    private AlunoRepository alunoRepo;

    public AulaColetivaController(AulaColetivaRepository aulaRepo, InstrutorRepository instrutorRepo, AlunoRepository alunoRepo) {
        this.aulaRepo = aulaRepo;
        this.instrutorRepo = instrutorRepo;
        this.alunoRepo = alunoRepo;
    }

    public String criarAula(int id, String modalidade, int idInstrutor, List<DayOfWeek> dias, LocalTime horario, int vagas){
        Instrutor instrutor = instrutorRepo.buscarPorId(idInstrutor).orElse(null);
        if(instrutor == null){return "Erro! Instrutor não encontrado";}

        for(AulaColetiva aulaExistente : aulaRepo.listarTodos()){
            boolean mesmoInstrutor = aulaExistente.getInstrutor().getId() == idInstrutor;
            boolean mesmoHorario = aulaExistente.getHorario().equals(horario);
            boolean diaEmComum = !Collections.disjoint(aulaExistente.getDias(), dias);
            if (mesmoInstrutor && mesmoHorario && diaEmComum) {
                return "Erro: Este instrutor já possui aula neste horário em um dos dias selecionados.";
            }
        }
        AulaColetiva novaAula = new AulaColetiva(id, modalidade, instrutor, dias, horario, vagas);
        aulaRepo.salvar(novaAula);
        return "Aula coletiva criada com sucesso!";
    }



    public List<AulaColetiva> listarAulasDisponiveis() {
        List<AulaColetiva> disponiveis = new ArrayList<>();
        for (AulaColetiva aula : aulaRepo.listarTodos()) {
            if (aula.getAlunos().size() < aula.getVagas()) {
                disponiveis.add(aula);
            }
        }
        return disponiveis;
    }

    public String inscreverAlunoNaAula(int idAula, int idAluno) {
        AulaColetiva aula = aulaRepo.buscarPorId(idAula).orElse(null);
        Aluno aluno = alunoRepo.buscarPorId(idAluno).orElse(null);

        if (aula == null || aluno == null) {
            return "Erro: Aula e/ou Aluno não encontrados.";
        }
        if (aula.getAlunos().size() >= aula.getVagas()) {
            return "Erro: Turma lotada!";
        }
        if (aula.getAlunos().contains(aluno)) {
            return "Erro: Aluno já está inscrito nesta aula.";
        }
        aula.getAlunos().add(aluno);
        aulaRepo.salvar(aula);
        return "Inscrição realizada com sucesso!";
    }



    public String cancelarInscricao(int idAula, int idAluno) {
        AulaColetiva aula = aulaRepo.buscarPorId(idAula).orElse(null);
        Aluno aluno = alunoRepo.buscarPorId(idAluno).orElse(null);
        if (aula == null || aluno == null) {
            return "Erro: Aula ou Aluno não encontrado.";
        }
        boolean removido = aula.getAlunos().remove(aluno);
        if (!removido) {
            return "Erro: Este aluno não estava inscrito nesta aula.";
        }
        aulaRepo.salvar(aula);
        return "Inscrição cancelada com sucesso!";
    }
}
//Gerencia aulaCOletiva, mas cruza intrutor, aluno., matricula...
//criarAula(), listarAulasDisponiveis(), inscreverAlunoNaAula(), cancelarInscricao()