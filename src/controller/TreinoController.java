package controller;

import model.*;
import repository.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TreinoController {
    private AlunoRepository alunoRepo;
    private InstrutorRepository instrutorRepo;
    private ExercicioRepository exercicioRepo;
    private TreinoRepository treinoRepo;
    private AvaliacaoFisicaRepository avaliacaoRepo;
    private EquipamentoRepository equipamentoRepo;

    public TreinoController(AlunoRepository alunoRepo, InstrutorRepository instrutorRepo, ExercicioRepository exercicioRepo, TreinoRepository treinoRepo, AvaliacaoFisicaRepository avaliacaoRepo, EquipamentoRepository equipamentoRepo) {
        this.alunoRepo = alunoRepo;
        this.instrutorRepo = instrutorRepo;
        this.exercicioRepo = exercicioRepo;
        this.treinoRepo = treinoRepo;
        this.avaliacaoRepo = avaliacaoRepo;
        this.equipamentoRepo = equipamentoRepo;
    }

    public String cadastrarExercicio(int id, String nome, GrupoMuscular grupoMuscular, double peso, int idEquipamento, int repeticoes) {
        Equipamento equipamento = equipamentoRepo.buscarPorId(idEquipamento).orElse(null);

        if (equipamento == null) {
            return "Erro: Equipamento não encontrado.";
        }
        Exercicio novo = new Exercicio(id, nome, grupoMuscular, peso, equipamento, repeticoes);
        exercicioRepo.salvar(novo);

        return "Exercício cadastrado com sucesso!";
    }




    public String registrarAvaliacao(int id, int idAluno, int idInstrutor, LocalDate data, double peso, double altura) {
        Aluno aluno = alunoRepo.buscarPorId(idAluno).orElse(null);
        Instrutor instrutor = instrutorRepo.buscarPorId(idInstrutor).orElse(null);
        if (aluno == null || instrutor == null) {
            return "Erro: Aluno ou Instrutor não encontrado.";
        }
        AvaliacaoFisica avaliacao = new AvaliacaoFisica(id, aluno, instrutor, data, peso, altura);
        avaliacaoRepo.salvar(avaliacao);

        return "Avaliação física registrada com sucesso!";
    }


    public String montarTreino(int id, int idAluno, int idInstrutor, String objetivo) {
        Aluno aluno = alunoRepo.buscarPorId(idAluno).orElse(null);
        Instrutor instrutor = instrutorRepo.buscarPorId(idInstrutor).orElse(null);
        if (aluno == null || instrutor == null) {
            return "Erro: Aluno ou Instrutor não encontrado.";
        }

        Treino treino = new Treino(id, aluno, instrutor, objetivo);
        treinoRepo.salvar(treino);
        return "Treino montado com sucesso! ID do treino: " + treino.getId();
    }

    public String adicionarExercicioNoTreino(int idTreino, int idExercicio) {
        Treino treino = treinoRepo.buscarPorId(idTreino).orElse(null);
        Exercicio exercicio = exercicioRepo.buscarPorId(idExercicio).orElse(null);

        if (treino == null || exercicio == null) {
            return "Erro: Treino ou Exercício não encontrado.";
        }
        treino.adicionarExercicio(exercicio);
        treinoRepo.salvar(treino);
        return "Exercício adicionado ao treino!";
    }

    public List<Treino> listarTreinosDoAluno(int idAluno) {
        List<Treino> todosOsTreinos = treinoRepo.listarTodos();
        List<Treino> treinosDoAluno = new ArrayList<>();

        for (Treino t : todosOsTreinos) {
            if (t.getAluno().getId() == idAluno) {
                treinosDoAluno.add(t);
            }
        }
        return treinosDoAluno;
        // O plano é a main receber essa lista e faz o print
    }
}

//registrarAvaliacao(), cadastrarExercicio(), montarTreino(), adicionarExercicioNoTreino(), listarTreinosDoAluno()
//Avaliacao fisica, treino e exercicio