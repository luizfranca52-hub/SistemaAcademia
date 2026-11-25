package exception;

// Lançada quando os dados do pagamento quebram alguma regra
public class PagamentoInvalidoException extends RegraNegocioException {

    public PagamentoInvalidoException(String mensagem) {
        super(mensagem);
    }
}
