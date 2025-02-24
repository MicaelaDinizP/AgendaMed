package devandroid.micaela.tcc_agendamed.controller;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import devandroid.micaela.tcc_agendamed.dao.UsuarioDAO;
import devandroid.micaela.tcc_agendamed.exception.ColecaoUsuariosException;
import devandroid.micaela.tcc_agendamed.dao.UsuarioDAOImpl;
import devandroid.micaela.tcc_agendamed.model.Usuario;

public class UsuarioController {
    private final UsuarioDAO colecaoUsuarios;
    private Context context;

    public UsuarioController(@NonNull Context context) {
        this.colecaoUsuarios = new UsuarioDAOImpl(context);
        this.context = context;
    }

    public long inserir(@NonNull String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            Log.e(this.colecaoUsuarios.getClass().getName(), "Nome de usuário inválido.");
            return -1;
        }
        try{
            return this.colecaoUsuarios.inserir(nome);
        }catch(ColecaoUsuariosException e){
            this.exibirErro("Erro ao inserir usuário.", e);
            return -1;
        }
    }

    public List<Usuario> obterTodos() {
        try{
           return this.colecaoUsuarios.obterTodos();
        }catch(ColecaoUsuariosException e){
            this.exibirErro("Erro ao obter todos usuários.", e);
            return Collections.emptyList();
        }
    }

    public boolean editar( @NonNull Usuario usuario) {
        try{
            return this.colecaoUsuarios.editar(usuario);
        }catch(ColecaoUsuariosException e){
            this.exibirErro("Erro ao editar o usuário.", e);
            return false;
        }
    }

    public boolean remover(long id) {
        try{
            return this.colecaoUsuarios.remover(id);
        }catch(ColecaoUsuariosException e){
            exibirErro("Erro ao remover o usuário.", e);
            return false;
        }
    }

    private void exibirErro( @NonNull String mensagem, @NonNull Exception e){
        Toast.makeText(this.context, mensagem , Toast.LENGTH_SHORT).show();
        Log.e(this.colecaoUsuarios.getClass().getName(), mensagem, e);
    }

}