package devandroid.micaela.tcc_agendamed.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.CheckBox;

import java.util.List;

import devandroid.micaela.tcc_agendamed.exception.ColecaoMedicamentosException;

public class ColecaoMedicamentosEmSQLite implements ColecaoMedicamentos {
    private GerenciadorSQLite gerenciadorBancoDeDados;
    private SQLiteDatabase bancoDeDados;
    public ColecaoMedicamentosEmSQLite(Context context){
        this.gerenciadorBancoDeDados = new GerenciadorSQLite(context);
    }

    public void open() {
        this.bancoDeDados = gerenciadorBancoDeDados.getWritableDatabase();
    }

    public void close() {
        gerenciadorBancoDeDados.close();
    }

    @Override
    public long inserir(Medicamento medicamento) {
        long idMedicamento = -1;
        this.bancoDeDados.beginTransaction();
        try {
            ContentValues dadosMedicamento = new ContentValues();
            dadosMedicamento.put(GerenciadorSQLite.COLUMN_NOME, medicamento.getNomeMedicamento());
            dadosMedicamento.put(GerenciadorSQLite.COLUMN_USUARIO_ID, medicamento.getUsuario().getId());
            dadosMedicamento.put(GerenciadorSQLite.COLUMN_QUANTIDADE_DOSES, medicamento.getQuantidadeDosesPorEmbalagem());
            dadosMedicamento.put(GerenciadorSQLite.COLUMN_DOSES_POR_DIA, medicamento.getDosesPorDia());
            dadosMedicamento.put(GerenciadorSQLite.COLUMN_QUANTIDADE_ESTOQUE_CRITICO, medicamento.getQuantidadeEstoqueCritico());
            dadosMedicamento.put(GerenciadorSQLite.COLUMN_QUANTIDADE_DOSES_RESTANTES, medicamento.getQuantidadeDosesRestantes());
            dadosMedicamento.put(GerenciadorSQLite.COLUMN_USO_PAUSADO, medicamento.isUsoPausado());
            dadosMedicamento.put(GerenciadorSQLite.COLUMN_CRIAR_ALARMES, medicamento.isCriarAlarmes());
            dadosMedicamento.put(GerenciadorSQLite.COLUMN_ALARME_ATIVO, medicamento.isAlarmeAtivo());

            idMedicamento = this.bancoDeDados.insert(GerenciadorSQLite.TABLE_MEDICAMENTO, null, dadosMedicamento);

            //Horarios das doses
            for (String horario : medicamento.getListaHorarios()) {
                ContentValues dadosHorario = new ContentValues();
                dadosHorario.put(GerenciadorSQLite.COLUMN_MEDICAMENTO_ID, idMedicamento);
                dadosHorario.put(GerenciadorSQLite.COLUMN_HORARIO, horario);
                this.bancoDeDados.insert(GerenciadorSQLite.TABLE_HORARIOS_DOSES, null, dadosHorario);
            }

            //Dias da semana
            for(DiaDaSemana dia: medicamento.getDiasDaSemana()){
                ContentValues dadosDiaDaSemana = new ContentValues();
                dadosDiaDaSemana.put(GerenciadorSQLite.COLUMN_MEDICAMENTO_ID, idMedicamento);
                dadosDiaDaSemana.put(GerenciadorSQLite.COLUMN_DIA_DA_SEMANA_ID, dia.getValor());
                this.bancoDeDados.insert(GerenciadorSQLite.TABLE_HORARIOS_DOSES, null, dadosDiaDaSemana);
            }
            this.bancoDeDados.setTransactionSuccessful();
        }catch (SQLException e){
            throw new ColecaoMedicamentosException("Não foi possível inserir o registro: " + e.getMessage(), e);
        }finally {
            this.bancoDeDados.endTransaction();
        }

        return idMedicamento;
    }
    @Override
    public List<Medicamento> obterTodos() {
        return null;
    }
 
    @Override
    public Medicamento obter(long id) {
        return null;
    }

    @Override
    public int editar(Medicamento medicamento) {
        return 0;
    }

    @Override
    public int remover(long id) {
        return 0;
    }
}
