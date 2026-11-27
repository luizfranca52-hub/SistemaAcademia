package controller;

import model.Equipamento;
import model.StatusEquipamento;
import repository.EquipamentoRepository;

import java.util.List;

public class EquipamentoController {
    private EquipamentoRepository equipamentoRepo;

    public EquipamentoController(EquipamentoRepository equipamentoRepo){
        this.equipamentoRepo = equipamentoRepo;
    }

    public String alterarStatusEquipamento(int idEquipamento, StatusEquipamento novoStatus) {
        Equipamento equipamento = equipamentoRepo.buscarPorId(idEquipamento).orElse(null);
        if (equipamento == null) {
            return "Erro: Equipamento não encontrado.";
        }
        equipamento.setStatus(novoStatus);
        equipamentoRepo.salvar(equipamento); // (ou .atualizar(equipamento), conforme seu RepositoryGeral)
        return "Status do equipamento atualizado para: " + novoStatus;
    }


}
//cadastrarEquipamento(), listarEquipamentos(), alterarStatusEquipamento() (ex: colocar em manutenção ou liberar).
//Classe equipamento