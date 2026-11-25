package exception;

// Lançada quando o perfil logado não tem permissão para a ação
public class AcessoNegadoException extends RegraNegocioException {

    public AcessoNegadoException(String mensagem) {
        super(mensagem);
    }
}
