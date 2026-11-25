package exception;

// Lançada quando o equipamento está em manutenção ou inativo
public class EquipamentoIndisponivelException extends RegraNegocioException {

    public EquipamentoIndisponivelException(String mensagem) {
        super(mensagem);
    }
}
