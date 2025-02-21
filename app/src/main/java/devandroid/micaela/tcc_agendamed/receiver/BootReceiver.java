package devandroid.micaela.tcc_agendamed.receiver;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import devandroid.micaela.tcc_agendamed.service.BootService;

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            Log.d("BootReceiver", "Iniciando serviço");

            Intent serviceIntent = new Intent(context, BootService.class);
            context.startService(serviceIntent);
        }
    }
}
