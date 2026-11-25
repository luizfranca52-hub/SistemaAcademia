package exception;

// mãe de todas as exceções de regra de negócio
// é RuntimeException pra n precisar de throws em tudo
public class RegraNegocioException extends RuntimeException {

    public RegraNegocioException(String mensagem) {
        super(mensagem);
    }
}
