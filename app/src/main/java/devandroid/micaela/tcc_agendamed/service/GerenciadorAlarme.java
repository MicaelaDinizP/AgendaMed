package devandroid.micaela.tcc_agendamed.service;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import java.util.Calendar;
import java.util.List;

import devandroid.micaela.tcc_agendamed.model.DiaDaSemana;
import devandroid.micaela.tcc_agendamed.model.Medicamento;
import devandroid.micaela.tcc_agendamed.receiver.AlarmeReceiver;

public class GerenciadorAlarme {
    private static final String CHANNEL_ID = "alarme_channel";
    public static void criarNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Alarme Channel";
            String description = "Canal para alarmes";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            channel.setSound(soundUri, new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build());
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }
    public static void agendarMultiplosAlarmes(Context context, Medicamento medicamento) {
        List<DiaDaSemana> dias = medicamento.getDiasDaSemana();
        List<String> horarios = medicamento.getListaHorarios();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            if (alarmManager != null && !alarmManager.canScheduleExactAlarms()) {
                Intent intent = new Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM);
                context.startActivity(intent);
                return;
            }
        }
        for (DiaDaSemana dia : dias) {
            for (String h : horarios) {
                int[] horario = formatarHorario(h);
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, horario[0]);
                calendar.set(Calendar.MINUTE, horario[1]);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                calendar.set(Calendar.DAY_OF_WEEK, dia.getValor());

                if (calendar.before(Calendar.getInstance())) {
                    calendar.add(Calendar.WEEK_OF_YEAR, 1);
                }

                Intent intent = new Intent(context, AlarmeReceiver.class);
                intent.putExtra("medicamento", medicamento);
                int requestCode = calcularRequestCode(medicamento.getId(), dia, horario[0], horario[1]);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        context,
                        requestCode,
                        intent,
                        PendingIntent.FLAG_IMMUTABLE
                );

                AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                if (alarmManager != null) {
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                }
            }
        }
    }
    public static void cancelarMultiplosAlarmes(Context context, Medicamento medicamento ) {
        List<DiaDaSemana> dias = medicamento.getDiasDaSemana();
        List<String> horarios = medicamento.getListaHorarios();

        for (DiaDaSemana dia : dias) {
            for (String h : horarios) {
                int[] horario = formatarHorario(h);
                int requestCode = calcularRequestCode(medicamento.getId(), dia, horario[0], horario[1]);

                Intent intent = new Intent(context, AlarmeReceiver.class);
                intent.putExtra("medicamento", medicamento);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        context,
                        requestCode,
                        intent,
                        PendingIntent.FLAG_IMMUTABLE
                );

                AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                if (alarmManager != null) {
                    alarmManager.cancel(pendingIntent);
                }
            }
        }
    }
    public static void editarAlarmes( Context context, Medicamento medicamento ){
            cancelarMultiplosAlarmes(context, medicamento);
            agendarMultiplosAlarmes(context, medicamento);
        }
    public static int calcularRequestCode(long medicamentoId, DiaDaSemana dia, int hora, int minuto) {
        return (int) medicamentoId * 10000 + dia.getValor() * 1000 + hora * 100 + minuto;
    }

    private static int[] formatarHorario(String horario) {
        String[] horarioSplit = horario.split(":");
        return new int[]{
                Integer.parseInt(horarioSplit[0]),
                Integer.parseInt(horarioSplit[1])
        };
    }
}