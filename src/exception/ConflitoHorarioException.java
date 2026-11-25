package exception;

// Lançada quando o instrutor ou aluno já tem compromisso no mesmo horário
public class ConflitoHorarioException extends RegraNegocioException {

    public ConflitoHorarioException(String mensagem) {
        super(mensagem);
    }
}
