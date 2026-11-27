package model;

import exception.AcessoNegadoException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

// conta de acesso ao sistema. É separada da Pessoa: a pessoa tem os dados dela,
// o usuário tem login, senha (em hash) e perfil
public class Usuario {
    private String login;
    private String senhaHash;
    private Perfil perfil;
    private Pessoa pessoa;
    private int tentativasErradas;

    // usuário novo: recebe a senha normal e guarda só o hash dela
    public Usuario(String login, String senha, Perfil perfil, Pessoa pessoa) {
        this.login = login;
        this.senhaHash = gerarHash(senha);
        this.perfil = perfil;
        this.pessoa = pessoa;
        this.tentativasErradas = 0;
    }

    // usado qnd carrega do arquivo, o hash já vem pronto
    public Usuario(String login, String senhaHash, Perfil perfil, Pessoa pessoa, int tentativasErradas) {
        this.login = login;
        this.senhaHash = senhaHash;
        this.perfil = perfil;
        this.pessoa = pessoa;
        this.tentativasErradas = tentativasErradas;
    }

    // confere a senha. Com 3 erros seguidos o usuário fica bloqueado
    public boolean autenticar(String senha) {
        if (estaBloqueado()) {
            throw new AcessoNegadoException("Usuário bloqueado por 3 tentativas erradas. Procure um administrativo.");
        }
        if (senhaHash.equals(gerarHash(senha))) {
            tentativasErradas = 0;
            return true;
        }
        tentativasErradas++;
        return false;
    }

    public boolean estaBloqueado() {
        return tentativasErradas >= 3;
    }

    public void desbloquear() {
        tentativasErradas = 0;
    }

    public boolean temPermissao(Permissao permissao) {
        return perfil.temPermissao(permissao);
    }

    // lança exceção se o perfil n puder fazer a ação
    public void verificarPermissao(Permissao permissao) {
        if (!temPermissao(permissao)) {
            throw new AcessoNegadoException("O perfil " + perfil + " não tem permissão para: " + permissao);
        }
    }

    public void alterarSenha(String novaSenha) {
        this.senhaHash = gerarHash(novaSenha);
    }

    // transforma a senha num hash SHA-256, assim a senha de verdade nunca fica salva
    public static String gerarHash(String senha) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] bytes = md.digest(senha.getBytes(StandardCharsets.UTF_8));
            String hash = "";
            for (byte b : bytes) {
                hash = hash + String.format("%02x", b);
            }
            return hash;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Não foi possível gerar o hash da senha", e);
        }
    }

    public String getLogin() {
        return login;
    }

    public String getSenhaHash() {
        return senhaHash;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public int getTentativasErradas() {
        return tentativasErradas;
    }
}
