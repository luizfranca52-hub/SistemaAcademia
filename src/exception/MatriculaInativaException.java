package exception;

// qnd precisa de matrícula ativa e n ta
public class MatriculaInativaException extends RegraNegocioException {

    public MatriculaInativaException(String mensagem) {
        super(mensagem);
    }
}
