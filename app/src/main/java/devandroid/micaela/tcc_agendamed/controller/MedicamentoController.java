package devandroid.micaela.tcc_agendamed.controller;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import devandroid.micaela.tcc_agendamed.dao.MedicamentoDAO;
import devandroid.micaela.tcc_agendamed.exception.ColecaoMedicamentosException;
import devandroid.micaela.tcc_agendamed.dao.MedicamentoDAOImpl;
import devandroid.micaela.tcc_agendamed.model.Medicamento;
import devandroid.micaela.tcc_agendamed.model.Usuario;

public class MedicamentoController {

    private MedicamentoDAO colecaoMedicamentos;
    private Context context;
    public MedicamentoController( @NonNull Context context) {
        this.context = context;
        this.colecaoMedicamentos = new MedicamentoDAOImpl(context);
    }

    public List<Medicamento> obterTodos( @NonNull  Usuario usuario){
        try{
            return this.colecaoMedicamentos.obterTodos(usuario);
            }catch (ColecaoMedicamentosException e){
                this.exibirErro("Erro ao obter o medicamento.",e);
                return Collections.emptyList();
            }
    }

    public Medicamento obterPorNome(@NonNull String nomeMedicamento, @NonNull Usuario usuario){
        try{
            return this.colecaoMedicamentos.obterPorNome(nomeMedicamento, usuario);
        }catch (ColecaoMedicamentosException e){
            this.exibirErro("Erro ao obter o medicamento.",e);
            return null;
        }
    }

    public Medicamento obterPorId(long idMedicamento, @NonNull Usuario usuario){
        try{
            return this.colecaoMedicamentos.obterPorId(idMedicamento, usuario);
        }catch (ColecaoMedicamentosException e){
            this.exibirErro("Erro ao obter o medicamento.",e);
            return null;
        }
    }

    public long inserir(@NonNull Medicamento medicamento) {
        try{
            return this.colecaoMedicamentos.inserir(medicamento);
        }catch (ColecaoMedicamentosException e) {
            this.exibirErro("Erro ao inserir o medicamento.",e);
            return -1;
        }
    }

    public long editar(@NonNull Medicamento medicamento){
        try{
            return this.colecaoMedicamentos.editar(medicamento);
        }catch (ColecaoMedicamentosException e){
            this.exibirErro("Erro ao editar o medicamento.",e);
            return -1;
        }
    }

    public boolean remover(long id){
        try{
            return this.colecaoMedicamentos.remover(id);
        }catch (ColecaoMedicamentosException e){
            this.exibirErro("Erro ao remover o medicamento.",e);
            return false;
        }
    }

    private void exibirErro( @NonNull String mensagem, @NonNull Exception e){
        Toast.makeText(this.context, mensagem , Toast.LENGTH_SHORT).show();
        Log.e(this.colecaoMedicamentos.getClass().getName(), mensagem, e);
    }

}