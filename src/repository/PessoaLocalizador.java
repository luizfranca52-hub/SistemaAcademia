package repository;

import model.Administrativo;
import model.Aluno;
import model.Instrutor;
import model.Perfil;
import model.Pessoa;
import model.Recepcionista;

// Os ids se repetem entre os tipos de pessoa (pode ter aluno 1 e instrutor 1),
// então pra achar uma pessoa precisa do tipo dela + o id.
// Usado por quem guarda uma Pessoa qualquer no arquivo (Usuario e AvaliacaoFisica).
public class PessoaLocalizador {
    private final AlunoRepository alunoRepository;
    private final InstrutorRepository instrutorRepository;
    private final AdministrativoRepository administrativoRepository;
    private final RecepcionistaRepository recepcionistaRepository;

    public PessoaLocalizador(AlunoRepository alunoRepository, InstrutorRepository instrutorRepository,
                             AdministrativoRepository administrativoRepository, RecepcionistaRepository recepcionistaRepository) {
        this.alunoRepository = alunoRepository;
        this.instrutorRepository = instrutorRepository;
        this.administrativoRepository = administrativoRepository;
        this.recepcionistaRepository = recepcionistaRepository;
    }

    // procura no repositório certo de acordo com o tipo
    public Pessoa buscar(Perfil tipo, Integer id) {
        Pessoa pessoa = null;

        if (tipo == Perfil.ALUNO) {
            pessoa = alunoRepository.buscarPorId(id).orElse(null);
        } else if (tipo == Perfil.INSTRUTOR) {
            pessoa = instrutorRepository.buscarPorId(id).orElse(null);
        } else if (tipo == Perfil.ADMINISTRATIVO) {
            pessoa = administrativoRepository.buscarPorId(id).orElse(null);
        } else if (tipo == Perfil.RECEPCIONISTA) {
            pessoa = recepcionistaRepository.buscarPorId(id).orElse(null);
        }

        if (pessoa == null) {
            throw new IllegalArgumentException("Pessoa não encontrada: " + tipo + " " + id);
        }
        return pessoa;
    }

    // descobre o tipo da pessoa pela classe dela
    public Perfil tipoDe(Pessoa pessoa) {
        if (pessoa instanceof Aluno) {
            return Perfil.ALUNO;
        }
        if (pessoa instanceof Instrutor) {
            return Perfil.INSTRUTOR;
        }
        if (pessoa instanceof Administrativo) {
            return Perfil.ADMINISTRATIVO;
        }
        if (pessoa instanceof Recepcionista) {
            return Perfil.RECEPCIONISTA;
        }
        throw new IllegalArgumentException("Tipo de pessoa desconhecido: " + pessoa.getClass().getSimpleName());
    }
}
