package devandroid.micaela.tcc_agendamed.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class GerenciadorSQLite extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "teste_agendamed.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "usuario";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "nome";

    private static final String TABELA_USUARIO_CREATE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT UNIQUE NOT NULL);";

    public GerenciadorSQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        context.deleteDatabase(DATABASE_NAME); //apagar base toda
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABELA_USUARIO_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase banco, int versaoAnterior, int versaoNova) {
        banco.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(banco);
    }
}