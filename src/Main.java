import model.*;

import java.time.LocalDate;

public class Main {
    void main() {
        Aluno aluno = new Aluno(21, "Kike", "073548424-43", LocalDate.of(2005, 3, 21),"567890678" );
        Plano plano = new Plano(21, "Normal", 200, TipoPlano.ANUAL);
        Matricula matricula = new Matricula(21, aluno, plano, LocalDate.of(2026, 11, 17), StatusMatricula.ATIVA);
        Pagamento pagamento = new Pagamento(aluno, matricula, plano.getValor(), LocalDate.of(2026, 11, 17), FormaPagamento.CARTAO_CREDITO);

        System.out.println(pagamento.getForma());


        System.out.println(matricula.getDataFim());
        System.out.println(matricula.getPlano().getTipo());
        System.out.println(matricula.getStatus());

    }
}