package devandroid.micaela.tcc_agendamed.controller;

import android.content.Context;
import android.database.SQLException;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import devandroid.micaela.tcc_agendamed.exception.ColecaoMedicamentosException;
import devandroid.micaela.tcc_agendamed.model.ColecaoMedicamentos;
import devandroid.micaela.tcc_agendamed.model.ColecaoMedicamentosEmSQLite;
import devandroid.micaela.tcc_agendamed.model.ColecaoUsuariosEmSQLite;
import devandroid.micaela.tcc_agendamed.model.Medicamento;
import devandroid.micaela.tcc_agendamed.model.Usuario;
import devandroid.micaela.tcc_agendamed.view.MedicamentoActivity;

public class MedicamentoController {
    private ColecaoMedicamentosEmSQLite colecaoMedicamentos;
    private Context context;
    public MedicamentoController(Context context) {
        this.context = context;
        this.colecaoMedicamentos = new ColecaoMedicamentosEmSQLite(context);
    }
    public void abrirConexao() {
        this.colecaoMedicamentos.open();
    }

    public void fecharConexao() {
        this.colecaoMedicamentos.close();
    }
    public List<Medicamento> obterTodos(Usuario usuario){
        List<Medicamento> listaMed = new ArrayList<>();
        try{
            listaMed = this.colecaoMedicamentos.obterTodos(usuario);
            }catch (ColecaoMedicamentosException e){
                Log.e(this.colecaoMedicamentos.getClass().getName(), "Erro ao obter medicamentos.", e);
            Toast.makeText(this.context, "Erro ao obter medicamentos." , Toast.LENGTH_SHORT).show();
            }
        return listaMed;
    }
    public Medicamento obterPorNome(String nomeMedicamento, Usuario usuario){
        Medicamento med = null;
        try{
            med = this.colecaoMedicamentos.obterPorNome(nomeMedicamento, usuario);
        }catch (ColecaoMedicamentosException e){
            Log.e(this.colecaoMedicamentos.getClass().getName(), "Erro ao obter o medicamento.", e);
            Toast.makeText(this.context, "Erro ao obter o medicamento." , Toast.LENGTH_SHORT).show();
        }
        return med;
    }

    public Medicamento obterPorId(long idMedicamento, Usuario usuario){
        Medicamento med = null;
        try{
            med = this.colecaoMedicamentos.obterPorId(idMedicamento, usuario);
        }catch (ColecaoMedicamentosException e){
            Log.e(this.colecaoMedicamentos.getClass().getName(), "Erro ao obter o medicamento.", e);
        }
        return med;
    }

    public long inserir(Medicamento medicamento) {
        long idRetornado = -1;
        try{
            idRetornado = this.colecaoMedicamentos.inserir(medicamento);
        }catch (ColecaoMedicamentosException e) {
            Log.e(this.colecaoMedicamentos.getClass().getName(), "Erro ao inserir o medicamento.", e);
        }
        return idRetornado;
    }

    public long editar(Medicamento medicamento){
        long idRetornado = -1;
        try{
            idRetornado = this.colecaoMedicamentos.editar(medicamento);
        }catch (ColecaoMedicamentosException e){
            Log.e(this.colecaoMedicamentos.getClass().getName(), "Erro ao editar o medicamento.", e);
        }
        return idRetornado;
    }

    public boolean remover(long id){
        boolean foiRemovido = false;
        try{
            foiRemovido = this.colecaoMedicamentos.remover(id);
        }catch (ColecaoMedicamentosException e){
            Log.e(this.colecaoMedicamentos.getClass().getName(), "Erro ao remover o medicamento.", e);
        }
        return foiRemovido;
    }
}