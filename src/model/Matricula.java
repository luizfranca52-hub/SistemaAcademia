package model;

import exception.TransicaoInvalidaException;

import java.time.LocalDate;

public class Matricula {
    private int id;
    private Aluno aluno;
    private Plano plano;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private StatusMatricula status;
    private Recepcionista recepcionista; // quem confirmou (null enqt ta pendente)
    private int diasTrancados;          // 0 = nunca trancou

    public Matricula(int id, Aluno aluno, Plano plano, LocalDate dataInicio, StatusMatricula status) {
        this(id, aluno, plano, dataInicio, status, null, 0);
    }

    public Matricula(int id, Aluno aluno, Plano plano, LocalDate dataInicio, StatusMatricula status,
                     Recepcionista recepcionista, int diasTrancados) {
        this.id = id;
        this.aluno = aluno;
        this.plano = plano;
        this.dataInicio = dataInicio;
        this.status = status;
        this.recepcionista = recepcionista;
        this.diasTrancados = diasTrancados;
        calcularDataFim();
    }

    // fim = inicio + meses do plano + dias trancados
    public void calcularDataFim() {
        this.dataFim = dataInicio.plusMonths(plano.getTipo().getMeses()).plusDays(diasTrancados);
    }

    public boolean estaAtiva(){
        return status == StatusMatricula.ATIVA;
    }

    // pendente -> ativa, a recepção confirma a assinatura
    public void confirmarAssinatura(Recepcionista recepcionista) {
        if (status != StatusMatricula.PENDENTE) {
            throw new TransicaoInvalidaException("Só matrícula PENDENTE pode ter a assinatura confirmada. Status atual: " + status);
        }
        this.recepcionista = recepcionista;
        recepcionista.registrarMatricula();
        this.status = StatusMatricula.ATIVA;
    }

    // ativa -> trancada, até 30 dias e só 1 vez
    public void trancar(int dias) {
        if (status != StatusMatricula.ATIVA) {
            throw new TransicaoInvalidaException("Só matrícula ATIVA pode ser trancada. Status atual: " + status);
        }
        if (diasTrancados > 0) {
            throw new TransicaoInvalidaException("Essa matrícula já foi trancada uma vez.");
        }
        if (dias < 1 || dias > 30) {
            throw new TransicaoInvalidaException("O trancamento deve ser de 1 a 30 dias.");
        }
        this.diasTrancados = dias;
        calcularDataFim();
        this.status = StatusMatricula.TRANCADA;
    }

    // trancada -> ativa
    public void reativar() {
        if (status != StatusMatricula.TRANCADA) {
            throw new TransicaoInvalidaException("Só matrícula TRANCADA pode ser reativada. Status atual: " + status);
        }
        this.status = StatusMatricula.ATIVA;
    }

    // qlqr status -> cancelada, dps n volta mais
    public void cancelar() {
        if (status == StatusMatricula.CANCELADA) {
            throw new TransicaoInvalidaException("Essa matrícula já está cancelada.");
        }
        this.status = StatusMatricula.CANCELADA;
    }

    // ativa -> vencida, só se já passou da data fim
    public void vencer(LocalDate hoje) {
        if (status != StatusMatricula.ATIVA) {
            throw new TransicaoInvalidaException("Só matrícula ATIVA pode vencer. Status atual: " + status);
        }
        if (!hoje.isAfter(dataFim)) {
            throw new TransicaoInvalidaException("A matrícula ainda não chegou na data de fim (" + dataFim + ").");
        }
        this.status = StatusMatricula.VENCIDA;
    }

    // vencida -> ativa, qnd paga de novo
    public void renovar(LocalDate novaDataInicio) {
        if (status != StatusMatricula.VENCIDA) {
            throw new TransicaoInvalidaException("Só matrícula VENCIDA pode ser renovada. Status atual: " + status);
        }
        this.dataInicio = novaDataInicio;
        this.diasTrancados = 0;
        calcularDataFim();
        this.status = StatusMatricula.ATIVA;
    }

    public boolean podeReceberPagamento() {
        return status != StatusMatricula.CANCELADA;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Plano getPlano() {
        return plano;
    }

    public void setPlano(Plano plano) {
        this.plano = plano;
        calcularDataFim();
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
        calcularDataFim();
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public StatusMatricula getStatus() {
        return status;
    }

    public void setStatus(StatusMatricula status) {
        this.status = status;
    }

    public Recepcionista getRecepcionista() {
        return recepcionista;
    }

    public int getDiasTrancados() {
        return diasTrancados;
    }
}
