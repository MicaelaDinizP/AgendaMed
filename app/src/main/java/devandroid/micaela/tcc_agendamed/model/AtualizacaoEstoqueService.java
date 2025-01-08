package devandroid.micaela.tcc_agendamed.model;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;


import devandroid.micaela.tcc_agendamed.controller.MedicamentoController;


public class AtualizacaoEstoqueService extends Service {
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        MedicamentoController medicamentoController = new MedicamentoController(this);
        medicamentoController.abrirConexao();

        Medicamento medicamento = intent.getParcelableExtra("medicamento");
        medicamento.setQuantidadeDosesRestantes(medicamento.getQuantidadeDosesRestantes()-1);
        medicamentoController.editar(medicamento);

        medicamentoController.fecharConexao();
        return START_STICKY;
    }

    public IBinder onBind(Intent intent) {
        return null;
    }
}
