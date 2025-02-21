package devandroid.micaela.tcc_agendamed.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.core.app.NotificationManagerCompat;

import devandroid.micaela.tcc_agendamed.model.Medicamento;
import devandroid.micaela.tcc_agendamed.service.AtualizacaoEstoqueService;

public class ActionReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "BOTÃO CLICADO!!!", Toast.LENGTH_SHORT).show();
        int notificationId = intent.getIntExtra("notification_id", -1);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.cancel(notificationId);

        Medicamento med = intent.getParcelableExtra("medicamento");

        Intent serviceIntent = new Intent(context, AtualizacaoEstoqueService.class);
        serviceIntent.putExtra("medicamento", med);
        context.startService(serviceIntent);

    }
}
