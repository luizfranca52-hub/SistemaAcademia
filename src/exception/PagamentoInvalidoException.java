package exception;

// qnd o pagamento quebra alguma regra
public class PagamentoInvalidoException extends RegraNegocioException {

    public PagamentoInvalidoException(String mensagem) {
        super(mensagem);
    }
}
