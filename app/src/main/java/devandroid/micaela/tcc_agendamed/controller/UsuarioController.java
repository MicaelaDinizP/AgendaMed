package devandroid.micaela.tcc_agendamed.controller;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import devandroid.micaela.tcc_agendamed.exception.ColecaoUsuariosException;
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
        long idRetornado = -1;
        if (nome == null || nome.trim().isEmpty()) {
            Log.e(this.colecaoUsuarios.getClass().getName(), "Nome de usuário inválido.");
            return -1;
        }
        try{
            idRetornado = this.colecaoUsuarios.inserir(nome);
        }catch(ColecaoUsuariosException e){
            Log.e(this.colecaoUsuarios.getClass().getName(), "Erro ao inserir usuário.", e);
        }
        return idRetornado;
    }

    public List<Usuario> obterTodos() {
        List<Usuario> usuarios = new ArrayList<>();
        try{
            usuarios = this.colecaoUsuarios.obterTodos();
        }catch(ColecaoUsuariosException e){
            Log.e(this.colecaoUsuarios.getClass().getName(), "Erro ao obter todos usuários.", e);
        }
        return usuarios;
    }

    public boolean editar(Usuario usuario) {
        boolean foiEditado = false;
        try{
            foiEditado = this.colecaoUsuarios.editar(usuario);
        }catch(ColecaoUsuariosException e){
            Log.e(this.colecaoUsuarios.getClass().getName(), "Erro ao editar o usuário.", e);
        }
        return foiEditado;
    }

    public int remover(long id) {
        int foiRemovido = -1;
        try{
            foiRemovido = this.colecaoUsuarios.remover(id);
        }catch(ColecaoUsuariosException e){
            Log.e(this.colecaoUsuarios.getClass().getName(), "Erro ao remover o usuário.", e);
        }
        return foiRemovido;
    }

}