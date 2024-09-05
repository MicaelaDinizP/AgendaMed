package devandroid.micaela.tcc_agendamed.model;

import java.util.List;

import devandroid.micaela.tcc_agendamed.exception.ColecaoUsuariosException;

public interface ColecaoUsuarios {
    long inserir(String nome) throws ColecaoUsuariosException;
    public List<Usuario> obterTodos() throws ColecaoUsuariosException;
    public int editar(Usuario usuario) throws ColecaoUsuariosException;
    public int remover(long id) throws ColecaoUsuariosException;

}