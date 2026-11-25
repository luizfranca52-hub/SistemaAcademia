package exception;

// Lançada quando a matrícula tenta mudar para um status não permitido
public class TransicaoInvalidaException extends RegraNegocioException {

    public TransicaoInvalidaException(String mensagem) {
        super(mensagem);
    }
}
