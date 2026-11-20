package repository;

import java.util.*;

public abstract class RepositoryGeral<T, K> implements Repository<T, K> {

    private final Map<K, T> dados = new LinkedHashMap<>();

    protected abstract K getChave(T obj);

    @Override
    public void salvar(T obj) {
        dados.put(getChave(obj), obj);
    }

    @Override
    public Optional<T> buscarComChave(K chave) {
        return Optional.ofNullable(dados.get(chave)); //se não tiver vira optional vazio
    }

    @Override
    public List<T> listarTodos() {
        return new ArrayList<>(dados.values());
    }

    @Override
    public void remover(K chave) {
        dados.remove(chave);
    }
}