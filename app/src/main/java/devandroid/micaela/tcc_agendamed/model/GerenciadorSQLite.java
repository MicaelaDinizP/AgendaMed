package devandroid.micaela.tcc_agendamed.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.TableLayout;

public class GerenciadorSQLite extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "teste_agendamed.db";
    private static final int DATABASE_VERSION = 1;

    //Tabelas
    public static final String TABLE_USUARIO = "usuario";
    public static final String TABLE_MEDICAMENTO = "medicamento";
    public static final String TABLE_HORARIOS_DOSES = "horarios_doses";
    public static final String TABLE_DIA_DA_SEMANA = "dia_da_semana";
    public static final String TABLE_MEDICAMENTO_DIA_DA_SEMANA = "medicamento_dia_da_semana";
    public static final String TABLE_MANUAL = "manual";

    //Colunas
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NOME = "nome";
    public static final String COLUMN_QUANTIDADE_DOSES = "quantidade_doses";
    public static final String COLUMN_DIA_DA_SEMANA_ID = "dia_da_semana_id";
    public static final String COLUMN_DOSES_POR_DIA = "doses_por_dia";
    public static final String COLUMN_QUANTIDADE_ESTOQUE_CRITICO = "quantidade_estoque_critico";
    public static final String COLUMN_QUANTIDADE_DOSES_RESTANTES = "quantidade_doses_restantes";
    public static final String COLUMN_USO_PAUSADO = "uso_pausado";
    public static final String COLUMN_CRIAR_ALARMES = "criar_alarmes";
    public static final String COLUMN_MEDICAMENTO_ID = "medicamento_id";
    public static final String COLUMN_HORARIO = "horario";
    public static final String COLUMN_DIA_DA_SEMANA = "dia_da_semana";
    public static final String COLUMN_USUARIO_ID = "usuario_id";
    public static final String COLUMN_ALARME_ATIVO = "alarme_ativo";
    public static final String COLUMN_PERGUNTA = "pergunta";
    public static final String COLUMN_CONTEUDO = "conteudo";

    private static final String TABLE_DIA_DA_SEMANA_CREATE = "CREATE TABLE "+ TABLE_DIA_DA_SEMANA+ "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_DIA_DA_SEMANA + " TEXT NOT NULL UNIQUE);";
    private static final String TABLE_MEDICAMENTO_CREATE =
            "CREATE TABLE " + TABLE_MEDICAMENTO + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NOME + " TEXT NOT NULL, " +
                    COLUMN_USUARIO_ID + " INTEGER NOT NULL ," +
                    COLUMN_QUANTIDADE_DOSES + " INTEGER NOT NULL, " +
                    COLUMN_DOSES_POR_DIA + " INTEGER NOT NULL, " +
                    COLUMN_QUANTIDADE_ESTOQUE_CRITICO + " INTEGER NOT NULL, " +
                    COLUMN_QUANTIDADE_DOSES_RESTANTES + " INTEGER NOT NULL, " +
                    COLUMN_USO_PAUSADO + " INTEGER DEFAULT 0, " +
                    COLUMN_CRIAR_ALARMES + " INTEGER DEFAULT 0, " +
                    COLUMN_ALARME_ATIVO + " INTEGER DEFAULT 0," +
                    " FOREIGN KEY (" + COLUMN_USUARIO_ID + ") REFERENCES " + TABLE_USUARIO + "(" + COLUMN_ID + ") ON DELETE CASCADE);";

    private static final String TABLE_HORARIOS_DOSES_CREATE =
            "CREATE TABLE " + TABLE_HORARIOS_DOSES + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_MEDICAMENTO_ID + " INTEGER, " +
                    COLUMN_HORARIO + " TEXT NOT NULL, " + //HH:MM:SS
                    " FOREIGN KEY (" + COLUMN_MEDICAMENTO_ID + ") REFERENCES " + TABLE_MEDICAMENTO + "(" + COLUMN_ID + ") ON DELETE CASCADE);";

    private static final String TABLE_USUARIO_CREATE =
            "CREATE TABLE " + TABLE_USUARIO + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NOME + " TEXT UNIQUE NOT NULL);";
    private static final String TABLE_MANUAL_CREATE =
            "CREATE TABLE " + TABLE_MANUAL + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_PERGUNTA + " TEXT UNIQUE NOT NULL, " +
                    COLUMN_CONTEUDO + " TEXT NOT NULL);";

    private static final String TABLE_MEDICAMENTO_DIA_DA_SEMANA_CREATE =
            "CREATE TABLE " + TABLE_MEDICAMENTO_DIA_DA_SEMANA + "(" +
                    COLUMN_MEDICAMENTO_ID + " INTEGER NOT NULL, " +
                    COLUMN_DIA_DA_SEMANA_ID + " INTEGER NOT NULL, " +
                    "PRIMARY KEY (" + COLUMN_MEDICAMENTO_ID + "," + COLUMN_DIA_DA_SEMANA_ID + " ), " +
                    "FOREIGN KEY (" + COLUMN_MEDICAMENTO_ID +") REFERENCES " + TABLE_MEDICAMENTO + "(" + COLUMN_ID + ") ON DELETE CASCADE ON UPDATE CASCADE, " +
                    "FOREIGN KEY (" + COLUMN_DIA_DA_SEMANA_ID +") REFERENCES " + TABLE_DIA_DA_SEMANA + "(" + COLUMN_ID + ") ON DELETE CASCADE ON UPDATE CASCADE);";

    public GerenciadorSQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //context.deleteDatabase(DATABASE_NAME); //apagar base toda
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_MANUAL_CREATE);
        db.execSQL(TABLE_USUARIO_CREATE);
        db.execSQL(TABLE_DIA_DA_SEMANA_CREATE);
        db.execSQL(TABLE_MEDICAMENTO_CREATE);
        db.execSQL(TABLE_MEDICAMENTO_DIA_DA_SEMANA_CREATE);
        db.execSQL(TABLE_HORARIOS_DOSES_CREATE);
        this.inserirDadosIniciais(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase banco, int versaoAnterior, int versaoNova) {
        banco.execSQL("DROP TABLE IF EXISTS " + TABLE_MANUAL);
        banco.execSQL("DROP TABLE IF EXISTS " + TABLE_HORARIOS_DOSES);
        banco.execSQL("DROP TABLE IF EXISTS " + TABLE_DIA_DA_SEMANA);
        banco.execSQL("DROP TABLE IF EXISTS " + TABLE_MEDICAMENTO);
        banco.execSQL("DROP TABLE IF EXISTS " + TABLE_USUARIO);
        banco.execSQL("DROP TABLE IF EXISTS " + TABLE_MEDICAMENTO_DIA_DA_SEMANA);

        onCreate(banco);
    }

    private void inserirDadosIniciais(SQLiteDatabase db) {
        db.execSQL("INSERT INTO " + TABLE_MANUAL + " (" + COLUMN_PERGUNTA +","+ COLUMN_CONTEUDO + ") " +
                "VALUES ('Como funciona esse aplicativo?', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
                "Etiam aliquam feugiat ex sit amet mattis. In nec porta tortor. Nullam at diam eu quam tincidunt ullamcorper at ut leo. " +
                "Cras sagittis finibus ornare. Nulla fermentum nisl a orci porta volutpat. Sed iaculis nulla nec quam venenatis, " +
                "ac consectetur metus venenatis. Ut tempus elit ut purus porta, vel mattis purus auctor. Nulla facilisi. " +
                "Donec sit amet ullamcorper enim, et sodales justo. Suspendisse potenti.');");
    }
}