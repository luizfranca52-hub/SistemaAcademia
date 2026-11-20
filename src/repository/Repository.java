package repository;

import java.util.*;

public interface Repository<T, K> {
    void salvar(T objeto);
    Optional<T> buscarComChave(K chave);
    void remover(K chave);
    List<T> listarTodos();
}
