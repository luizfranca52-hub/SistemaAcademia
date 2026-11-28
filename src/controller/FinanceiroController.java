package controller;

import exception.PagamentoInvalidoException;
import model.*;
import repository.*;

import java.time.LocalDate;
import java.util.List;

public class FinanceiroController {
    private PlanoRepository planoRepo;
    private MatriculaRepository matriculaRepo;
    private PagamentoRepository pagamentoRepo;
    private AlunoRepository alunoRepo;
    private RecepcionistaRepository recepcionistaRepo;

    public FinanceiroController(PlanoRepository planoRepo, MatriculaRepository matriculaRepo, PagamentoRepository pagamentoRepo, AlunoRepository alunoRepo, RecepcionistaRepository recepcionistaRepo) {
        this.planoRepo = planoRepo;
        this.matriculaRepo = matriculaRepo;
        this.pagamentoRepo = pagamentoRepo;
        this.alunoRepo = alunoRepo;
        this.recepcionistaRepo = recepcionistaRepo;
    }

    public String criarPlano(int id, String nome, double valor, TipoPlano tipo) {
        Plano plano = new Plano(id, nome, valor, tipo);
        planoRepo.salvar(plano);
        return "Plano cadastrado com sucesso!";
    }


    public List<Plano> listarPlanos() {
        return planoRepo.listarTodos();
    }



    public String realizarMatricula(int idMatricula, int idAluno, int idPlano, int idRecepcionista) {
        Aluno aluno = alunoRepo.buscarPorId(idAluno).orElse(null);
        Plano plano = planoRepo.buscarPorId(idPlano).orElse(null);
        Recepcionista recepcionista = recepcionistaRepo.buscarPorId(idRecepcionista).orElse(null);
        if (aluno == null || plano == null || recepcionista == null) { //regra de negocio do recepcionista
            return "Erro: Aluno, Plano ou Recepcionista não encontrado.";
        }
        if (plano.getStatus() != StatusPlano.ATIVO) {
            return "Erro: Não é possível matricular em um plano inativo.";
        }

        Matricula matricula = new Matricula(idMatricula, aluno, plano, LocalDate.now(), StatusMatricula.ATIVA, recepcionista, 0);
        matriculaRepo.salvar(matricula);

        recepcionista.registrarMatricula(); //incrementar na meta
        recepcionistaRepo.salvar(recepcionista);

        return "Matrícula realizada com sucesso! Vencimento: " + matricula.getDataFim();
    }








    public String registrarPagamentoCartao(int idPagamento, int idMatricula, double valorPago, int parcelas, boolean credito) {
        Matricula matricula = matriculaRepo.buscarPorId(idMatricula).orElse(null);
        if (matricula == null) return "Erro: Matrícula não encontrada.";
        if (matricula.getStatus() == StatusMatricula.CANCELADA) {
            return "Erro: Não é possível registrar pagamento para uma matrícula cancelada.";
        }

        try {
            Pagamento pagamento = new PagamentoCartao(idPagamento, matricula, valorPago, LocalDate.now(), parcelas, credito);
            return concluirPagamento(pagamento, matricula);
        } catch (PagamentoInvalidoException e) {
            return "Erro no pagamento com cartão: " + e.getMessage();
        }
    }

    public String registrarPagamentoDinheiro(int idPagamento, int idMatricula, double valorPago, double valorRecebido) {
        Matricula matricula = matriculaRepo.buscarPorId(idMatricula).orElse(null);
        if (matricula == null) return "Erro: Matrícula não encontrada.";
        if (matricula.getStatus() == StatusMatricula.CANCELADA) {
            return "Erro: Não é possível registrar pagamento para uma matrícula cancelada.";
        }

        try {
            Pagamento pagamento = new PagamentoDinheiro(idPagamento, matricula, valorPago, LocalDate.now(), valorRecebido);
            return concluirPagamento(pagamento, matricula);
        } catch (PagamentoInvalidoException e) {
            return "Erro no pagamento em dinheiro: " + e.getMessage();
        }
    }

    public String registrarPagamentoPix(int idPagamento, int idMatricula, double valorPago, double percentualDesconto) {
        Matricula matricula = matriculaRepo.buscarPorId(idMatricula).orElse(null);
        if (matricula == null) return "Erro: Matrícula não encontrada.";
        if (matricula.getStatus() == StatusMatricula.CANCELADA) {
            return "Erro: Não é possível registrar pagamento para uma matrícula cancelada.";
        }

        try {
            Pagamento pagamento = new PagamentoPix(idPagamento, matricula, valorPago, LocalDate.now(), percentualDesconto);
            return concluirPagamento(pagamento, matricula);
        } catch (PagamentoInvalidoException e) {
            return "Erro no pagamento via Pix: " + e.getMessage();
        }
    }

    private String concluirPagamento(Pagamento pagamento, Matricula matricula) {
        pagamentoRepo.salvar(pagamento); //funciona com qualquer subclasse
        matricula.setStatus(StatusMatricula.ATIVA);
        matricula.setDataInicio(LocalDate.now());
        matricula.calcularDataFim();
        matriculaRepo.salvar(matricula);

        return "Pagamento registrado! Matrícula ativa até: " + matricula.getDataFim();
    }




}
//métodos: criarPlano(), listarPlanos(), realizarMatricula(), registrarPagamento(), alterarStatusMatricula(), verificarInadimplencia()
//Gerencia plano, matricula e pagamento
