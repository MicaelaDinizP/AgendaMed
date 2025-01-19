package devandroid.micaela.tcc_agendamed.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                this.bancoDeDados.insert(GerenciadorSQLite.TABLE_MEDICAMENTO_DIA_DA_SEMANA, null, dadosDiaDaSemana);
            }
            this.bancoDeDados.setTransactionSuccessful();
        }catch (SQLException e){
            throw new ColecaoMedicamentosException("Não foi possível inserir o registro: " + e.getMessage(), e);
        }finally {
            this.bancoDeDados.endTransaction();
        }
        return idMedicamento;
    }
    public List<Medicamento> obterTodos(Usuario usuario) {
        Map<Integer, Medicamento> medicamentoMap = new HashMap<>();
        Cursor cursor = null;
        try {
            String query = "SELECT " +
                    "m." + GerenciadorSQLite.COLUMN_ID + " AS medicamentoID, " +
                    "m." + GerenciadorSQLite.COLUMN_NOME + " AS nomeMedicamento, " +
                    "m." + GerenciadorSQLite.COLUMN_QUANTIDADE_DOSES + " AS quantidadeDoses, " +
                    "m." + GerenciadorSQLite.COLUMN_DOSES_POR_DIA + " AS dosesPorDia, " +
                    "m." + GerenciadorSQLite.COLUMN_QUANTIDADE_ESTOQUE_CRITICO + " AS quantidadeEstoqueCritico, " +
                    "m." + GerenciadorSQLite.COLUMN_QUANTIDADE_DOSES_RESTANTES + " AS quantidadeDosesRestantes, " +
                    "m." + GerenciadorSQLite.COLUMN_USO_PAUSADO + " AS usoPausado, " +
                    "m." + GerenciadorSQLite.COLUMN_CRIAR_ALARMES + " AS criarAlarmes, " +
                    "m." + GerenciadorSQLite.COLUMN_ALARME_ATIVO + " AS alarmeAtivo, " +
                    "hd." + GerenciadorSQLite.COLUMN_HORARIO + " AS horarioDose, " +
                    "dds." + GerenciadorSQLite.COLUMN_DIA_DA_SEMANA + " AS diaDaSemana " +
                    "FROM " + GerenciadorSQLite.TABLE_MEDICAMENTO + " m " +
                    "LEFT JOIN " + GerenciadorSQLite.TABLE_HORARIOS_DOSES + " hd ON m." + GerenciadorSQLite.COLUMN_ID + " = hd." + GerenciadorSQLite.COLUMN_MEDICAMENTO_ID + " " +
                    "LEFT JOIN " + GerenciadorSQLite.TABLE_MEDICAMENTO_DIA_DA_SEMANA + " mds ON m." + GerenciadorSQLite.COLUMN_ID + " = mds." + GerenciadorSQLite.COLUMN_MEDICAMENTO_ID + " " +
                    "LEFT JOIN " + GerenciadorSQLite.TABLE_DIA_DA_SEMANA + " dds ON mds." + GerenciadorSQLite.COLUMN_DIA_DA_SEMANA_ID + " = dds." + GerenciadorSQLite.COLUMN_ID +
                    " WHERE " + GerenciadorSQLite.COLUMN_USUARIO_ID + " = ?";

            cursor = bancoDeDados.rawQuery(query, new String[]{String.valueOf(usuario.getId())});

            if (cursor.moveToFirst()) {
                do {
                    int medicamentoId = cursor.getInt(cursor.getColumnIndexOrThrow("medicamentoID"));

                    Medicamento medicamento = medicamentoMap.get(medicamentoId);
                    if (medicamento == null) {
                        String nomeMedicamento = cursor.getString(cursor.getColumnIndexOrThrow("nomeMedicamento"));
                        int quantidadeDoses = cursor.getInt(cursor.getColumnIndexOrThrow("quantidadeDoses"));
                        int dosesPorDia = cursor.getInt(cursor.getColumnIndexOrThrow("dosesPorDia"));
                        int quantidadeEstoqueCritico = cursor.getInt(cursor.getColumnIndexOrThrow("quantidadeEstoqueCritico"));
                        int quantidadeDosesRestantes = cursor.getInt(cursor.getColumnIndexOrThrow("quantidadeDosesRestantes"));
                        boolean usoPausado = cursor.getInt(cursor.getColumnIndexOrThrow("usoPausado")) != 0;
                        boolean criarAlarmes = cursor.getInt(cursor.getColumnIndexOrThrow("criarAlarmes")) != 0;
                        boolean alarmeAtivo = cursor.getInt(cursor.getColumnIndexOrThrow("alarmeAtivo")) != 0;

                        medicamento = new Medicamento(nomeMedicamento, usuario, quantidadeDoses, new ArrayList<>(),
                                dosesPorDia, quantidadeEstoqueCritico, quantidadeDosesRestantes, usoPausado, criarAlarmes, alarmeAtivo,
                                new ArrayList<>());

                        medicamento.setId(medicamentoId);
                        medicamentoMap.put(medicamentoId, medicamento);
                    }

                    String horarioDose = cursor.getString(cursor.getColumnIndexOrThrow("horarioDose"));
                    if (horarioDose != null && !medicamento.getListaHorarios().contains(horarioDose)) {
                        medicamento.getListaHorarios().add(horarioDose);
                    }

                    String diaDaSemana = cursor.getString(cursor.getColumnIndexOrThrow("diaDaSemana"));
                    if (diaDaSemana != null && !medicamento.getDiasDaSemana().contains(diaDaSemana)) {
                        medicamento.getDiasDaSemana().add(DiaDaSemana.getDiaDaSemana(diaDaSemana));
                    }
                } while (cursor.moveToNext());
            }
        } catch (SQLException e) {
            throw new ColecaoMedicamentosException("Não foi possível obter os registros: " + e.getMessage(), e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return new ArrayList<>(medicamentoMap.values());
    }

    public Medicamento obterPorId(long id, Usuario usuario) {
        Medicamento medicamento = null;
        Cursor cursor = null;

        try {
            String query = "SELECT " +
                    "m." + GerenciadorSQLite.COLUMN_ID + " AS medicamentoID, " +
                    "m." + GerenciadorSQLite.COLUMN_NOME + " AS nomeMedicamento, " +
                    "m." + GerenciadorSQLite.COLUMN_QUANTIDADE_DOSES + " AS quantidadeDoses, " +
                    "m." + GerenciadorSQLite.COLUMN_DOSES_POR_DIA + " AS dosesPorDia, " +
                    "m." + GerenciadorSQLite.COLUMN_QUANTIDADE_ESTOQUE_CRITICO + " AS quantidadeEstoqueCritico, " +
                    "m." + GerenciadorSQLite.COLUMN_QUANTIDADE_DOSES_RESTANTES + " AS quantidadeDosesRestantes, " +
                    "m." + GerenciadorSQLite.COLUMN_USO_PAUSADO + " AS usoPausado, " +
                    "m." + GerenciadorSQLite.COLUMN_CRIAR_ALARMES + " AS criarAlarmes, " +
                    "m." + GerenciadorSQLite.COLUMN_ALARME_ATIVO + " AS alarmeAtivo, " +
                    "hd." + GerenciadorSQLite.COLUMN_HORARIO + " AS horarioDose, " +
                    "dds." + GerenciadorSQLite.COLUMN_DIA_DA_SEMANA + " AS diaDaSemana " +
                    "FROM " + GerenciadorSQLite.TABLE_MEDICAMENTO + " m " +
                    "LEFT JOIN " + GerenciadorSQLite.TABLE_HORARIOS_DOSES + " hd ON m." + GerenciadorSQLite.COLUMN_ID + " = hd." + GerenciadorSQLite.COLUMN_MEDICAMENTO_ID + " " +
                    "LEFT JOIN " + GerenciadorSQLite.TABLE_MEDICAMENTO_DIA_DA_SEMANA + " mds ON m." + GerenciadorSQLite.COLUMN_ID + " = mds." + GerenciadorSQLite.COLUMN_MEDICAMENTO_ID + " " +
                    "LEFT JOIN " + GerenciadorSQLite.TABLE_DIA_DA_SEMANA + " dds ON mds." + GerenciadorSQLite.COLUMN_DIA_DA_SEMANA_ID + " = dds." + GerenciadorSQLite.COLUMN_ID +
                    " WHERE m." + GerenciadorSQLite.COLUMN_ID + " = ? AND " + GerenciadorSQLite.COLUMN_USUARIO_ID + " = ?";

            cursor = bancoDeDados.rawQuery(query, new String[]{String.valueOf(id), String.valueOf(usuario.getId())});

            if (cursor.moveToFirst()) {
                String nomeMedicamento = cursor.getString(cursor.getColumnIndexOrThrow("nomeMedicamento"));
                int quantidadeDoses = cursor.getInt(cursor.getColumnIndexOrThrow("quantidadeDoses"));
                int dosesPorDia = cursor.getInt(cursor.getColumnIndexOrThrow("dosesPorDia"));
                int quantidadeEstoqueCritico = cursor.getInt(cursor.getColumnIndexOrThrow("quantidadeEstoqueCritico"));
                int quantidadeDosesRestantes = cursor.getInt(cursor.getColumnIndexOrThrow("quantidadeDosesRestantes"));
                boolean usoPausado = cursor.getInt(cursor.getColumnIndexOrThrow("usoPausado")) != 0;
                boolean criarAlarmes = cursor.getInt(cursor.getColumnIndexOrThrow("criarAlarmes")) != 0;
                boolean alarmeAtivo = cursor.getInt(cursor.getColumnIndexOrThrow("alarmeAtivo")) != 0;

                medicamento = new Medicamento(nomeMedicamento, usuario, quantidadeDoses, new ArrayList<>(), dosesPorDia, quantidadeEstoqueCritico,
                        quantidadeDosesRestantes,
                        usoPausado,
                        criarAlarmes,
                        alarmeAtivo,
                        new ArrayList<>()
                );
                medicamento.setId(id);

                do {
                    String horarioDose = cursor.getString(cursor.getColumnIndexOrThrow("horarioDose"));
                    if (horarioDose != null && !medicamento.getListaHorarios().contains(horarioDose)) {
                        medicamento.getListaHorarios().add(horarioDose);
                    }

                    String diaDaSemana = cursor.getString(cursor.getColumnIndexOrThrow("diaDaSemana"));
                    if (diaDaSemana != null) {
                        DiaDaSemana dia = DiaDaSemana.getDiaDaSemana(diaDaSemana);
                        if (!medicamento.getDiasDaSemana().contains(dia)) {
                            medicamento.getDiasDaSemana().add(dia);
                        }
                    }
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            throw new ColecaoMedicamentosException("Não foi possível buscar registro: " + e.getMessage(), e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return medicamento;
    }
    public Medicamento obterPorNome(String nome, Usuario usuario) {
        Medicamento medicamento = null;
        try {
            String query = "SELECT " +
                    "m." + GerenciadorSQLite.COLUMN_ID + " AS medicamentoID, " +
                    "m." + GerenciadorSQLite.COLUMN_NOME + " AS nomeMedicamento, " +
                    "m." + GerenciadorSQLite.COLUMN_QUANTIDADE_DOSES + " AS quantidadeDoses, " +
                    "m." + GerenciadorSQLite.COLUMN_DOSES_POR_DIA + " AS dosesPorDia, " +
                    "m." + GerenciadorSQLite.COLUMN_QUANTIDADE_ESTOQUE_CRITICO + " AS quantidadeEstoqueCritico, " +
                    "m." + GerenciadorSQLite.COLUMN_QUANTIDADE_DOSES_RESTANTES + " AS quantidadeDosesRestantes, " +
                    "m." + GerenciadorSQLite.COLUMN_USO_PAUSADO + " AS usoPausado, " +
                    "m." + GerenciadorSQLite.COLUMN_CRIAR_ALARMES + " AS criarAlarmes, " +
                    "m." + GerenciadorSQLite.COLUMN_ALARME_ATIVO + " AS alarmeAtivo, " +
                    "hd." + GerenciadorSQLite.COLUMN_HORARIO + " AS horarioDose, " +
                    "dds." + GerenciadorSQLite.COLUMN_DIA_DA_SEMANA + " AS diaDaSemana " +
                    "FROM " + GerenciadorSQLite.TABLE_MEDICAMENTO + " m " +
                    "LEFT JOIN " + GerenciadorSQLite.TABLE_HORARIOS_DOSES + " hd ON m." + GerenciadorSQLite.COLUMN_ID + " = hd." + GerenciadorSQLite.COLUMN_MEDICAMENTO_ID + " " +
                    "LEFT JOIN " + GerenciadorSQLite.TABLE_MEDICAMENTO_DIA_DA_SEMANA + " mds ON m." + GerenciadorSQLite.COLUMN_ID + " = mds." + GerenciadorSQLite.COLUMN_MEDICAMENTO_ID + " " +
                    "LEFT JOIN " + GerenciadorSQLite.TABLE_DIA_DA_SEMANA + " dds ON mds." + GerenciadorSQLite.COLUMN_DIA_DA_SEMANA_ID + " = dds." + GerenciadorSQLite.COLUMN_ID +
                    " WHERE m." + GerenciadorSQLite.COLUMN_NOME + " = ? AND " + GerenciadorSQLite.COLUMN_USUARIO_ID + " = ?";

            Cursor cursor = bancoDeDados.rawQuery(query, new String[]{nome, String.valueOf(usuario.getId())});

            if (cursor.moveToFirst()) {
                long id = cursor.getLong(cursor.getColumnIndexOrThrow("medicamentoID"));
                String nomeMedicamento = cursor.getString(cursor.getColumnIndexOrThrow("nomeMedicamento"));
                int quantidadeDoses = cursor.getInt(cursor.getColumnIndexOrThrow("quantidadeDoses"));
                int dosesPorDia = cursor.getInt(cursor.getColumnIndexOrThrow("dosesPorDia"));
                int quantidadeEstoqueCritico = cursor.getInt(cursor.getColumnIndexOrThrow("quantidadeEstoqueCritico"));
                int quantidadeDosesRestantes = cursor.getInt(cursor.getColumnIndexOrThrow("quantidadeDosesRestantes"));
                boolean usoPausado = cursor.getInt(cursor.getColumnIndexOrThrow("usoPausado")) != 0;
                boolean criarAlarmes = cursor.getInt(cursor.getColumnIndexOrThrow("criarAlarmes")) != 0;
                boolean alarmeAtivo = cursor.getInt(cursor.getColumnIndexOrThrow("alarmeAtivo")) != 0;

                medicamento = new Medicamento(nomeMedicamento, usuario, quantidadeDoses, new ArrayList<>(), dosesPorDia, quantidadeEstoqueCritico,
                        quantidadeDosesRestantes,
                        usoPausado,
                        criarAlarmes,
                        alarmeAtivo,
                        new ArrayList<>()
                );
                medicamento.setId(id);

                do {
                    String horarioDose = cursor.getString(cursor.getColumnIndexOrThrow("horarioDose"));
                    if (horarioDose != null && !medicamento.getListaHorarios().contains(horarioDose)) {
                        medicamento.getListaHorarios().add(horarioDose);
                    }

                    String diaDaSemana = cursor.getString(cursor.getColumnIndexOrThrow("diaDaSemana"));
                    if (diaDaSemana != null) {
                        DiaDaSemana dia = DiaDaSemana.getDiaDaSemana(diaDaSemana);
                        if (!medicamento.getDiasDaSemana().contains(dia)) {
                            medicamento.getDiasDaSemana().add(dia);
                        }
                    }
                } while (cursor.moveToNext());
            }
            cursor.close();
        } catch (SQLException e) {
            throw new ColecaoMedicamentosException("Não foi possível buscar o registro: " + e.getMessage(), e);
        }
        return medicamento;
    }
    public long editar(Medicamento medicamento) {
        long novoId = -1;
        bancoDeDados.beginTransaction();
        try {
            int rowsAffected = bancoDeDados.delete(
                    GerenciadorSQLite.TABLE_MEDICAMENTO,
                    GerenciadorSQLite.COLUMN_ID + " = ?",
                    new String[]{String.valueOf(medicamento.getId())}
            );

            if (rowsAffected > 0) {
                novoId = this.inserir(medicamento);
                bancoDeDados.setTransactionSuccessful();
            } else {
                throw new IllegalStateException("Erro ao editar medicamento: nenhum registro foi encontrado para remoção.");
            }
        } catch (IllegalStateException e) {
            throw new ColecaoMedicamentosException("" + e.getMessage(), e);
        } catch (Exception e) {
            throw new ColecaoMedicamentosException("Não foi possível editar o registro: " + e.getMessage(), e);
        } finally {
            bancoDeDados.endTransaction();
        }
        return novoId;
    }

    public boolean remover(long id) {
        boolean statusRemocao = false;
        bancoDeDados.beginTransaction();
        try {
            int rowsAffected = bancoDeDados.delete(
                    GerenciadorSQLite.TABLE_MEDICAMENTO,
                    GerenciadorSQLite.COLUMN_ID + " = ?",
                    new String[]{String.valueOf(id)}
            );

            if (rowsAffected > 0) {
                bancoDeDados.setTransactionSuccessful();
                statusRemocao = true;
            } else {
                throw new IllegalStateException("Erro ao remover medicamento: nenhum registro foi encontrado com o ID fornecido.");
            }
        } catch (IllegalStateException e) {
            throw new ColecaoMedicamentosException("" + e.getMessage(), e);
        } catch (Exception e) {
            throw new ColecaoMedicamentosException("Não foi possível remover o registro: " + e.getMessage(), e);
        } finally {
            bancoDeDados.endTransaction();
            return statusRemocao;
        }
    }
}