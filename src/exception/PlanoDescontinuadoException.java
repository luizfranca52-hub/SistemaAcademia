package exception;

// qnd tenta matricular ou renovar num plano q n é mais vendido
public class PlanoDescontinuadoException extends RegraNegocioException {

    public PlanoDescontinuadoException(String mensagem) {
        super(mensagem);
    }
}
