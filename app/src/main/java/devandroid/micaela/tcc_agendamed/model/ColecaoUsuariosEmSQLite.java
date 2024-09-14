package devandroid.micaela.tcc_agendamed.model;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import devandroid.micaela.tcc_agendamed.exception.ColecaoMedicamentosException;
import devandroid.micaela.tcc_agendamed.exception.ColecaoUsuariosException;

public class ColecaoUsuariosEmSQLite implements ColecaoUsuarios{
    private GerenciadorSQLite gerenciadorBancoDeDados;
    private SQLiteDatabase bancoDeDados;
    public ColecaoUsuariosEmSQLite(Context context){
        this.gerenciadorBancoDeDados = new GerenciadorSQLite(context);
    }

    public void open() {
        this.bancoDeDados = gerenciadorBancoDeDados.getWritableDatabase();
    }

    public void close() {
        gerenciadorBancoDeDados.close();
    }
    @Override
    public long inserir(String nome) {
        ContentValues values = new ContentValues();
        try {
            values.put(GerenciadorSQLite.COLUMN_NOME, nome);
            return this.bancoDeDados.insert(GerenciadorSQLite.TABLE_USUARIO, null, values);
        }catch (SQLException e){
            throw new ColecaoUsuariosException("Não foi possível inserir o registro: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Usuario> obterTodos() {
        List<Usuario> usuarios = new ArrayList<>();
        try{
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
        }catch(SQLException e){
            throw new ColecaoUsuariosException("Não foi possível obter todos os registros: " + e.getMessage(), e);
        }
    }
    @Override
    public boolean editar(Usuario usuario) {
        ContentValues values = new ContentValues();
        try{
            int linhasAfetadas = 0;
            values.put(gerenciadorBancoDeDados.COLUMN_NOME, usuario.getNome());
            linhasAfetadas = this.bancoDeDados.update(gerenciadorBancoDeDados.TABLE_USUARIO, values, gerenciadorBancoDeDados.COLUMN_ID + " = ?", new String[]{String.valueOf(usuario.getId())});
            if(linhasAfetadas > 0){
                return true;
            }
            return false;
        }catch(SQLException e){
            throw new ColecaoUsuariosException("Não foi possível editar o registro: " + e.getMessage(), e);
        }
    }
    @Override
    public int remover(long id) {
        try{
            return this.bancoDeDados.delete(gerenciadorBancoDeDados.TABLE_USUARIO, gerenciadorBancoDeDados.COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        }catch (SQLException e){
            throw new ColecaoUsuariosException("Não foi possível excluir o registro: " + e.getMessage(), e);
        }
    }
}
