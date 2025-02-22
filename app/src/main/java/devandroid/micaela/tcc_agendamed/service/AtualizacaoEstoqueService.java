package devandroid.micaela.tcc_agendamed.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;


import devandroid.micaela.tcc_agendamed.controller.MedicamentoController;
import devandroid.micaela.tcc_agendamed.model.Medicamento;


public class AtualizacaoEstoqueService extends Service {
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        MedicamentoController medicamentoController = new MedicamentoController(this);
        Medicamento medicamento = intent.getParcelableExtra("medicamento");
        medicamento.setQuantidadeDosesRestantes(medicamento.getQuantidadeDosesRestantes()-1);
        medicamentoController.editar(medicamento);
        return START_STICKY;
    }

    public IBinder onBind(Intent intent) {
        return null;
    }
}
