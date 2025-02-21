package devandroid.micaela.tcc_agendamed.receiver;

import android.Manifest;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import devandroid.micaela.tcc_agendamed.model.Medicamento;
import devandroid.micaela.tcc_agendamed.receiver.ActionReceiver;

public class AlarmeReceiver extends BroadcastReceiver {

    private static final String CHANNEL_ID = "alarme_channel";

    @Override
    public void onReceive(Context context, Intent intent) {

        Medicamento medicamento = intent.getParcelableExtra("medicamento");

        int NOTIFICATION_ID = (int) System.currentTimeMillis();
        Intent actionIntent = new Intent(context, ActionReceiver.class);
        actionIntent.putExtra("notification_id", NOTIFICATION_ID);
        actionIntent.putExtra("medicamento", medicamento);
        PendingIntent actionPendingIntent = PendingIntent.getBroadcast(
                context,
                0,
                actionIntent,
                PendingIntent.FLAG_UPDATE_CURRENT|PendingIntent.FLAG_IMMUTABLE
        );

        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
        bigTextStyle.bigText("USUÁRIO: "+medicamento.getUsuario().getNome()+" \n ESTOQUE ATUAL:"+medicamento.getQuantidadeDosesRestantes()
                +" - "+obterStatusMedicamento(medicamento));
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_alert)
                .setContentTitle(medicamento.getNomeMedicamento())
                .setSubText(medicamento.getUsuario().getNome())
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setStyle(bigTextStyle)
                .addAction(new NotificationCompat.Action(
                        android.R.drawable.ic_menu_add,
                        "ADMINISTRADO !",
                        actionPendingIntent
                ))
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
    }
    public String obterStatusMedicamento(Medicamento medicamento){
        if (medicamento.getQuantidadeDosesRestantes() == 0) {
            return "Esgotado.";
        } else if (medicamento.getQuantidadeDosesRestantes() < medicamento.getQuantidadeEstoqueCritico()) {
            return "Esgotando...";
        }
        return "";
    }
}