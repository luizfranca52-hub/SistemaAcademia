package exception;

// Lançada quando a aula coletiva não tem mais vagas
public class AulaLotadaException extends RegraNegocioException {

    public AulaLotadaException(String mensagem) {
        super(mensagem);
    }
}
