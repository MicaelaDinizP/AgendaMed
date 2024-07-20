package devandroid.micaela.tcc_agendamed.model;

import java.util.List;

public interface ColecaoUsuarios {
    long inserir(String nome);
    public List<Usuario> obterTodos();
    public int editar(Usuario usuario);
    public int remover(long id);

}