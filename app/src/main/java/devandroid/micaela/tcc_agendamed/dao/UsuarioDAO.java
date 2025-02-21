package devandroid.micaela.tcc_agendamed.dao;

import java.util.List;

import devandroid.micaela.tcc_agendamed.exception.ColecaoUsuariosException;
import devandroid.micaela.tcc_agendamed.model.Usuario;

public interface UsuarioDAO {
    long inserir(String nome) throws ColecaoUsuariosException;
    List<Usuario> obterTodos() throws ColecaoUsuariosException;
    boolean editar(Usuario usuario) throws ColecaoUsuariosException;
    boolean remover(long id) throws ColecaoUsuariosException;

}