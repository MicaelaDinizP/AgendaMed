package devandroid.micaela.tcc_agendamed.model;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

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
        values.put(GerenciadorSQLite.COLUMN_NOME, nome);
        return this.bancoDeDados.insert(GerenciadorSQLite.TABLE_USUARIO, null, values);
    }

    @Override
    public List<Usuario> obterTodos() {
        List<Usuario> usuarios = new ArrayList<>();
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
    }

    @Override
    public int editar(Usuario usuario) {
        ContentValues values = new ContentValues();
        values.put(gerenciadorBancoDeDados.COLUMN_NOME, usuario.getNome());

       return this.bancoDeDados.update(gerenciadorBancoDeDados.TABLE_USUARIO, values, gerenciadorBancoDeDados.COLUMN_ID + " = ?", new String[]{String.valueOf(usuario.getId())});
    }

    @Override
    public int remover(long id) {
        return this.bancoDeDados.delete(gerenciadorBancoDeDados.TABLE_USUARIO, gerenciadorBancoDeDados.COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
    }
}
