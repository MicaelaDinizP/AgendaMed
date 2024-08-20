package devandroid.micaela.tcc_agendamed.controller;

import android.content.Context;

import devandroid.micaela.tcc_agendamed.model.ColecaoMedicamentos;
import devandroid.micaela.tcc_agendamed.model.ColecaoMedicamentosEmSQLite;
import devandroid.micaela.tcc_agendamed.model.ColecaoUsuariosEmSQLite;
import devandroid.micaela.tcc_agendamed.model.Medicamento;

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
}
