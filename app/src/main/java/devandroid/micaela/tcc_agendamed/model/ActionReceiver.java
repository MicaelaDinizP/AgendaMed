package devandroid.micaela.tcc_agendamed.model;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationManagerCompat;

import devandroid.micaela.tcc_agendamed.view.CadastroMedicamentoActivity;

public class ActionReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        //Toast.makeText(context, "BOTAO CLICADO!!!", Toast.LENGTH_SHORT).show(); VOU PRECISAR MAIS TARDE
        int notificationId = intent.getIntExtra("notification_id", -1);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.cancel(notificationId);
        //MAIS AÇÕES AQUI (!!!!!!!!!!)
    }
}
