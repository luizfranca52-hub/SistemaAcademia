package model;

// perfil de acesso do usuário, cada um pode fazer coisas diferentes
public enum Perfil {
    ALUNO,
    INSTRUTOR,
    RECEPCIONISTA,
    ADMINISTRATIVO;

    public boolean temPermissao(Permissao permissao) {
        // todo mundo pode ver os próprios dados
        if (permissao == Permissao.VER_PROPRIOS_DADOS) {
            return true;
        }
        // administrativo pode tudo
        if (this == ADMINISTRATIVO) {
            return true;
        }
        if (this == RECEPCIONISTA) {
            return permissao == Permissao.GERENCIAR_MATRICULAS
                    || permissao == Permissao.REGISTRAR_PAGAMENTOS
                    || permissao == Permissao.INSCREVER_EM_AULA;
        }
        if (this == INSTRUTOR) {
            return permissao == Permissao.GERENCIAR_TREINOS
                    || permissao == Permissao.REGISTRAR_AVALIACOES
                    || permissao == Permissao.GERENCIAR_AULAS;
        }
        // aluno só se inscreve em aula
        return permissao == Permissao.INSCREVER_EM_AULA;
    }
}
