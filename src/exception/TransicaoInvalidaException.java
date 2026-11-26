package exception;

// qnd tenta mudar o status da matrícula de um jeito q n pode
public class TransicaoInvalidaException extends RegraNegocioException {

    public TransicaoInvalidaException(String mensagem) {
        super(mensagem);
    }
}
