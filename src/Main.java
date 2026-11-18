import model.*;

import java.time.LocalDate;

public class Main {
    void main() {
        Aluno aluno = new Aluno(21, "Kike", "073548424-43", LocalDate.of(2005, 3, 21),"567890678" );
        Plano plano = new Plano(21, "Normal", 200, TipoPlano.ANUAL);
        Matricula matricula = new Matricula(21, aluno, plano, LocalDate.of(2026, 11, 17), StatusMatricula.ATIVA);
        Pagamento pagamento = new Pagamento(aluno, matricula, plano.getValor(), LocalDate.of(2026, 11, 17), FormaPagamento.CARTAO_CREDITO);

       Instrutor instrutor = new Instrutor("234", "rafa", "1234", 700, 23, "Rafael", "23456", LocalDate.of(2012, 12, 10), "23456" );
       Equipamento alther = new Equipamento("Alther", StatusEquipamento.DISPONIVEL);
       Exercicio e1 = new Exercicio("supino", GrupoMuscular.PEITO, 12, 10, alther);
        Exercicio e2 = new Exercicio("crucifixo", GrupoMuscular.PEITO, 25, 10);

        Treino treino = new Treino(12, aluno, instrutor, "peito");
        treino.adicionarExercicio(e1);
        treino.adicionarExercicio(e2);
        treino.papelTreino();

    }
}