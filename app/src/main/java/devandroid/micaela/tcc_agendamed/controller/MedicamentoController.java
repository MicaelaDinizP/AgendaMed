package devandroid.micaela.tcc_agendamed.controller;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import devandroid.micaela.tcc_agendamed.model.ColecaoMedicamentos;
import devandroid.micaela.tcc_agendamed.model.ColecaoMedicamentosEmSQLite;
import devandroid.micaela.tcc_agendamed.model.ColecaoUsuariosEmSQLite;
import devandroid.micaela.tcc_agendamed.model.Medicamento;
import devandroid.micaela.tcc_agendamed.model.Usuario;

public class MedicamentoController {
    private ColecaoMedicamentosEmSQLite colecaoMedicamentos;
    public MedicamentoController(Context context) {
        this.colecaoMedicamentos = new ColecaoMedicamentosEmSQLite(context);
    }
    public void abrirConexao() {
        this.colecaoMedicamentos.open();
    }

    public void fecharConexao() {
        this.colecaoMedicamentos.close();
    }
    public List<Medicamento> obterTodos(Usuario usuario){
        return this.colecaoMedicamentos.obterTodos(usuario);
    }

    public long inserir(Medicamento medicamento) {
        return this.colecaoMedicamentos.inserir(medicamento);
    }

    public boolean editar(Medicamento medicamento){
        return this.colecaoMedicamentos.editar(medicamento);
    }
}