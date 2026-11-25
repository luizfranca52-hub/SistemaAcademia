package exception;

// Lançada quando uma ação exige matrícula ATIVA e ela não está
public class MatriculaInativaException extends RegraNegocioException {

    public MatriculaInativaException(String mensagem) {
        super(mensagem);
    }
}
