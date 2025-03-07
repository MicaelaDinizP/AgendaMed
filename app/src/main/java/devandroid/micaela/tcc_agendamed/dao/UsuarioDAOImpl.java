package devandroid.micaela.tcc_agendamed.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import devandroid.micaela.tcc_agendamed.exception.ColecaoUsuariosException;
import devandroid.micaela.tcc_agendamed.model.Usuario;

public class UsuarioDAOImpl implements UsuarioDAO {
    private final GerenciadorSQLite gerenciadorBancoDeDados;
    private SQLiteDatabase bancoDeDados;
    public UsuarioDAOImpl(Context context){
        this.gerenciadorBancoDeDados = new GerenciadorSQLite(context);
    }

    private void open() {
        this.bancoDeDados = gerenciadorBancoDeDados.getWritableDatabase();
    }

    private void close() {
        gerenciadorBancoDeDados.close();
    }
    @Override
    public long inserir(String nome) {
        this.open();
        ContentValues values = new ContentValues();
        try {
            values.put(GerenciadorSQLite.COLUMN_NOME, nome);
            return this.bancoDeDados.insert(GerenciadorSQLite.TABLE_USUARIO, null, values);
        } catch (SQLException e) {
            throw new ColecaoUsuariosException("Não foi possível inserir o registro: " + e.getMessage(), e);
        }finally {
            this.close();
        }
    }

    @Override
    public List<Usuario> obterTodos() {
        this.open();
        List<Usuario> usuarios = new ArrayList<>();
        try {
            Cursor cursor = this.bancoDeDados.query(GerenciadorSQLite.TABLE_USUARIO,
                    new String[]{GerenciadorSQLite.COLUMN_ID, GerenciadorSQLite.COLUMN_NOME},
                    null, null, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(GerenciadorSQLite.COLUMN_ID));
                    @SuppressLint("Range") String nome = cursor.getString(cursor.getColumnIndex(GerenciadorSQLite.COLUMN_NOME));
                    usuarios.add(new Usuario(id, nome));
                } while (cursor.moveToNext());
                cursor.close();
            }
            return usuarios;
        } catch(SQLException e) {
            throw new ColecaoUsuariosException("Não foi possível obter todos os registros: " + e.getMessage(), e);
        }finally {
            this.close();
        }
    }
    @Override
    public boolean editar(Usuario usuario) {
        this.open();
        ContentValues values = new ContentValues();
        try {
            int linhasAfetadas;
            values.put(GerenciadorSQLite.COLUMN_NOME, usuario.getNome());
            linhasAfetadas = this.bancoDeDados.update(GerenciadorSQLite.TABLE_USUARIO, values, GerenciadorSQLite.COLUMN_ID + " = ?", new String[]{String.valueOf(usuario.getId())});
            return linhasAfetadas > 0;
        } catch(SQLException e) {
            throw new ColecaoUsuariosException("Não foi possível editar o registro: " + e.getMessage(), e);
        }finally {
            this.close();
        }
    }
    @Override
    public boolean remover(long id) {
        this.open();
        try {
            int linhasAfetadas;
            linhasAfetadas = this.bancoDeDados.delete(GerenciadorSQLite.TABLE_USUARIO, GerenciadorSQLite.COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
            return linhasAfetadas > 0;
        } catch (SQLException e) {
            throw new ColecaoUsuariosException("Não foi possível excluir o registro: " + e.getMessage(), e);
        }finally {
            this.close();
        }
    }
}
