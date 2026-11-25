package exception;

// qnd o equipamento ta em manutenção ou inativo
public class EquipamentoIndisponivelException extends RegraNegocioException {

    public EquipamentoIndisponivelException(String mensagem) {
        super(mensagem);
    }
}
