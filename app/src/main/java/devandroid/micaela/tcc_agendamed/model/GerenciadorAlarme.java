package devandroid.micaela.tcc_agendamed.model;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import java.util.Calendar;
import java.util.List;

public class  GerenciadorAlarme {
    private static final String CHANNEL_ID = "alarme_channel";
    public static void agendarMultiplosAlarmes(Context context, Medicamento medicamento ) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        List<DiaDaSemana> dias = medicamento.getDiasDaSemana();
        List<String> horarios = medicamento.getListaHorarios();

        for (DiaDaSemana dia : dias) {
            for (String hora : horarios) {


            }
        }
    }

    private static int[] formatarHorario(String horario) {
        int[] horariosInt = new int[2];

        String[] horarioSplit = horario.split(":");
        horariosInt[0] = Integer.parseInt(horarioSplit[0]);
        horariosInt[1] = Integer.parseInt(horarioSplit[1]);

        return horariosInt;
    }
    public static int calcularRequestCode(long medicamentoId, DiaDaSemana dia, int hora, int minuto) {
        return (int)medicamentoId * 10000 + dia.getValor() * 1000 + hora * 100 + minuto;
    }
}