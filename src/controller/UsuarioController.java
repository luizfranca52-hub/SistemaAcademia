package controller;

import exception.RegraNegocioException;
import model.*;
import repository.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.time.LocalDate;
import java.util.List;

public class UsuarioController {
    private UsuarioRepository usuarioRepo;
    private AlunoRepository alunoRepo;
    private InstrutorRepository instrutorRepo;
    private AdministrativoRepository adminRepo;
    private RecepcionistaRepository recepcionistaRepo;
    private Map<String, Integer> tentativasLogin = new HashMap<>();
    private Set<String> usuariosBloqueados = new HashSet<>();

    private static final int MAX_TENTATIVAS = 3;

    public UsuarioController(UsuarioRepository usuarioRepo, AlunoRepository alunoRepo, InstrutorRepository instrutorRepo, AdministrativoRepository adminRepo, RecepcionistaRepository recepcionistaRepo) {
        this.usuarioRepo = usuarioRepo;
        this.alunoRepo = alunoRepo;
        this.instrutorRepo = instrutorRepo;
        this.adminRepo = adminRepo;
        this.recepcionistaRepo = recepcionistaRepo;
    }

    public Usuario login(String login, String senhaDigitada) {
        if (usuariosBloqueados.contains(login)) {
            return null;
        }
        Usuario usuario = usuarioRepo.buscarPorLogin(login).orElse(null);
        if (usuario == null) {
            return null;
        }
        if (usuario.autenticar(senhaDigitada)) {
            tentativasLogin.remove(login);
            return usuario;
        }
        int tentativas = tentativasLogin.getOrDefault(login, 0) + 1;
        tentativasLogin.put(login, tentativas);

        if (tentativas >= MAX_TENTATIVAS) {// Block na terceira tentativa
            usuariosBloqueados.add(login);
        }
        return null;
    }

    //--------------------------------------------------------------------------------------metodos extra
    public boolean estaBloqueado(String login) {
        return usuariosBloqueados.contains(login);
    }

    public int tentativasRestantes(String login) {
        return MAX_TENTATIVAS - tentativasLogin.getOrDefault(login, 0);
    }

    //---------------------------------------------------------------------------------------------------------
    public String cadastrarAluno(int id, String nome, String cpf, LocalDate dataNascimento, String telefone, String responsavel, String login, String senha) {
        try {
            Aluno aluno = new Aluno(id, nome, cpf, dataNascimento, telefone, responsavel);
            alunoRepo.salvar(aluno);
            Usuario usuario = new Usuario(login, senha, Perfil.ALUNO, aluno);
            usuarioRepo.salvar(usuario);
            return "Aluno cadastrado com sucesso!";
        } catch (RegraNegocioException e) {
            return "Erro no cadastro: " + e.getMessage();
        }
    }


    public String cadastrarInstrutor(int id, String nome, String cpf, LocalDate dataNascimento, String telefone, double salarioBase, LocalDate dataAdmissao, String cref, int cargaHorariaSemanal, String login, String senha) {
        Instrutor instrutor = new Instrutor(id, nome, cpf, dataNascimento, telefone, salarioBase, dataAdmissao, cref, cargaHorariaSemanal);
        instrutorRepo.salvar(instrutor);
        Usuario usuario = new Usuario(login, senha, Perfil.INSTRUTOR, instrutor);
        usuarioRepo.salvar(usuario);
        return "Instrutor cadastrado com sucesso!";
    }



    public String cadastrarAdmin(int id, String nome, String cpf, LocalDate dataNascimento, String telefone, double salarioBase, LocalDate dataAdmissao, String cargo, String login, String senha) {
        Administrativo admin = new Administrativo(id, nome, cpf, dataNascimento, telefone, salarioBase, dataAdmissao, cargo);
        adminRepo.salvar(admin);
        Usuario usuario = new Usuario(login, senha, Perfil.ADMINISTRATIVO, admin);
        usuarioRepo.salvar(usuario);
        return "Administrativo cadastrado com sucesso!";
    }

    public String cadastrarRecepcionista(int id, String nome, String cpf, LocalDate dataNascimento, String telefone, double salarioBase, LocalDate dataAdmissao, Turno turno, int metaMatriculas, String login, String senha) {
        Recepcionista recep = new Recepcionista(id, nome, cpf, dataNascimento, telefone, salarioBase, dataAdmissao, turno, metaMatriculas, 0);
        recepcionistaRepo.salvar(recep);
        Usuario usuario = new Usuario(login, senha, Perfil.RECEPCIONISTA, recep);
        usuarioRepo.salvar(usuario);
        return "Recepcionista cadastrado com sucesso!";
    }




    public List<Usuario> listarUsuarios() {
        return usuarioRepo.listarTodos();
    }


    public Pessoa buscarPorCpf(String cpf) {
        for (Usuario u : usuarioRepo.listarTodos()) {
            if (u.getPessoa().getCpf().equals(cpf)) {
                return u.getPessoa();
            }
        }
        return null;
    }

    public String removerUsuario(String login) {
        Usuario usuario = usuarioRepo.buscarPorLogin(login).orElse(null);
        if (usuario == null) {
            return "Erro: Usuário não encontrado.";
        }
        usuarioRepo.remover(login);
        return "Usuário removido com sucesso!";
    }
}
//métodos: login(), cadastrarAluno(), cadastrarInstrutor(), cadastrarAdmin(), listarUsuarios(), buscarPorCpf(), removerUsuario().
//Gerenciar basicamente as classes relativas a humanos