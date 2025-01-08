package devandroid.micaela.tcc_agendamed.model;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationManagerCompat;

import devandroid.micaela.tcc_agendamed.controller.MedicamentoController;
import devandroid.micaela.tcc_agendamed.view.CadastroMedicamentoActivity;
import devandroid.micaela.tcc_agendamed.view.MedicamentoActivity;
import devandroid.micaela.tcc_agendamed.view.SplashScreenProgramarAlarmesActivity;

public class ActionReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "BOTÃO CLICADO!!!", Toast.LENGTH_SHORT).show();
        int notificationId = intent.getIntExtra("notification_id", -1);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.cancel(notificationId);

        // Crie o Intent para a nova Activity
        Intent intent2 = new Intent(context, SplashScreenProgramarAlarmesActivity.class);

        // Obtenha o objeto "medicamento" e passe para a nova Activity
        Medicamento med = intent.getParcelableExtra("medicamento");
        intent2.putExtra("medicamento", med);

        // Defina a flag para garantir que a Activity será iniciada corretamente
        intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Inicie a nova Activity
        context.startActivity(intent2);
    }

}
