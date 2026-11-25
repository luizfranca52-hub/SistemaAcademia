package exception;

// Exceção base de todas as regras de negócio do sistema.
// Herda de RuntimeException pra não obrigar "throws" em todo método.
public class RegraNegocioException extends RuntimeException {

    public RegraNegocioException(String mensagem) {
        super(mensagem);
    }
}
