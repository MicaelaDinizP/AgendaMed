package devandroid.micaela.tcc_agendamed.controller;

import android.content.Context;

import java.util.List;

import devandroid.micaela.tcc_agendamed.model.ColecaoUsuariosEmSQLite;
import devandroid.micaela.tcc_agendamed.model.Usuario;

public class UsuarioController{
    private ColecaoUsuariosEmSQLite colecaoUsuarios;

    public UsuarioController(Context context) {
        this.colecaoUsuarios = new ColecaoUsuariosEmSQLite(context);
    }

    public void abrirConexao() {
        this.colecaoUsuarios.open();
    }

    public void fecharConexao() {
        this.colecaoUsuarios.close();
    }

    public long inserir(String nome) {
        return this.colecaoUsuarios.inserir(nome);
    }

    public List<Usuario> obterTodos() {
        return this.colecaoUsuarios.obterTodos();
    }
    public int editar(Usuario usuario) {
        return this.colecaoUsuarios.editar(usuario);
    }

    public int remover(long id) {
        return this.colecaoUsuarios.remover(id);
    }

}