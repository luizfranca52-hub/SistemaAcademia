package exception;

// Lançada quando um id buscado não existe
public class EntidadeNaoEncontradaException extends RegraNegocioException {

    public EntidadeNaoEncontradaException(String mensagem) {
        super(mensagem);
    }
}
