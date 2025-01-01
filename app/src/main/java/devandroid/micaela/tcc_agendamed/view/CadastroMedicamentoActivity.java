package devandroid.micaela.tcc_agendamed.view;

import android.Manifest;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.text.TextWatcher;
import android.text.Editable;
import devandroid.micaela.tcc_agendamed.model.AlarmeReceiver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import devandroid.micaela.tcc_agendamed.R;
import devandroid.micaela.tcc_agendamed.controller.MedicamentoController;
import devandroid.micaela.tcc_agendamed.model.DiaDaSemana;
import devandroid.micaela.tcc_agendamed.model.GerenciadorAlarme;
import devandroid.micaela.tcc_agendamed.model.Medicamento;

public class CadastroMedicamentoActivity extends AppCompatActivity {
    private static final String CHANNEL_ID = "alarme_channel";
    private MedicamentoController medicamentoController;
    private EditText editTextNomeMedicamento;
    private EditText editTextDosesPorEmbalagem;
    private EditText editTextDosesPorDia;
    private List<String> listaHorarios;
    private EditText editTextEstoqueCritico;
    private EditText editTextDosesRestantes;
    private List<DiaDaSemana> listaDeDiasDaSemana;
    private CheckBox checkBoxPausarUso;
    private CheckBox checkBoxCriarAlarme;
    private ImageButton btnFechar;
    private ImageButton btnApagar;
    private ImageButton btnSalvar;
    private TableLayout tabelaHorarios;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_medicamento);

        ArrayList<CheckBox> cbDiasDaSemana = new ArrayList<CheckBox>();
        this.listaHorarios = new ArrayList<>();
        this.medicamentoController = new MedicamentoController(CadastroMedicamentoActivity.this);
        this.editTextNomeMedicamento = findViewById(R.id.editTextNomeMedicamento);
        this.editTextDosesPorEmbalagem = findViewById(R.id.editTextDosesPorEmbalagem);
        this.editTextDosesPorDia = findViewById(R.id.editTextDosesPorDia);
        this.editTextEstoqueCritico = findViewById(R.id.editTextEstoqueCritico);
        this.editTextDosesRestantes = findViewById(R.id.editTextDosesRestantes);
        this.checkBoxPausarUso = findViewById(R.id.checkBoxPausarUso);
        this.checkBoxCriarAlarme = findViewById(R.id.checkBoxCriarAlarme);
        this.tabelaHorarios = findViewById(R.id.tableHorarios);
        //MAPEAR O CAMPO DOS HORARIOS
        cbDiasDaSemana.add(findViewById(R.id.checkBoxDiaSegunda));
        cbDiasDaSemana.add(findViewById(R.id.checkBoxDiaTerca));
        cbDiasDaSemana.add(findViewById(R.id.checkBoxDiaQuarta));
        cbDiasDaSemana.add(findViewById(R.id.checkBoxDiaQuinta));
        cbDiasDaSemana.add(findViewById(R.id.checkBoxDiaSexta));
        cbDiasDaSemana.add(findViewById(R.id.checkBoxDiaSabado));
        cbDiasDaSemana.add(findViewById(R.id.checkBoxDiaDomingo));
        this.editTextDosesPorDia.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                int dose = 0;
                if (!s.toString().isEmpty()) {
                    dose = Integer.parseInt(s.toString());
                }
                atualizarTabelaHorarioDasDoses(dose);
            }
        });

        this.btnFechar = findViewById(R.id.btnFechar);
        this.btnFechar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        this.btnApagar = findViewById(R.id.btnApagar);
        btnApagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });
        this.btnSalvar = findViewById(R.id.btnSalvar);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obterListaDeHorarios();
                obterDiasDaSemana(cbDiasDaSemana);
                if (verificarCampos()) {
                    String nomeMedicamento = editTextNomeMedicamento.getText().toString();
                    int qtdDosesPorEmbalagem = Integer.parseInt(editTextDosesPorEmbalagem.getText().toString());
                    int qtdDosesPorDia = Integer.parseInt(editTextDosesPorDia.getText().toString());
                    int qtdEstoqueCritico = Integer.parseInt(editTextEstoqueCritico.getText().toString());
                    int qtdDosesRestantes = Integer.parseInt(editTextDosesRestantes.getText().toString());

                    Medicamento medicamento = new Medicamento(nomeMedicamento, MainActivity.USUARIO_LOGADO, qtdDosesPorEmbalagem,
                            listaDeDiasDaSemana, qtdDosesPorDia, qtdEstoqueCritico, qtdDosesRestantes, checkBoxPausarUso.isChecked(),
                            checkBoxCriarAlarme.isChecked(), checkBoxCriarAlarme.isChecked(), listaHorarios);
                    medicamentoController.abrirConexao();
                    long idRetornado = medicamentoController.inserir(medicamento);
                    if(idRetornado != -1){
                        Toast.makeText(CadastroMedicamentoActivity.this, "Medicamento  cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                        programarAlarmes(medicamento);
                        finish();
                    }else{
                        Toast.makeText(CadastroMedicamentoActivity.this, "ERRO: Não foi possível cadastrar o medicamento." , Toast.LENGTH_SHORT).show();
                    }
                    medicamentoController.fecharConexao();
                }else{
                    Toast.makeText(CadastroMedicamentoActivity.this, "ERRO: Campos obrigatórios não preenchidos." , Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    /////////////////////// ALARMESSS //////////////////////////////
    private void programarAlarmes(Medicamento medicamento) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
            }
        }
        criarNotificationChannel();
        agendarMultiplosAlarmes(this, medicamento);
    }
    public void agendarMultiplosAlarmes(Context context, Medicamento medicamento ) {
        List<DiaDaSemana> dias = medicamento.getDiasDaSemana();
        List<String> horarios = medicamento.getListaHorarios();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            if (alarmManager != null && !alarmManager.canScheduleExactAlarms()) {
                Intent intent = new Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM);
                startActivity(intent);
                return; // Retorna se a permissão não foi concedida
            }
        }
        for (DiaDaSemana dia : dias) {
            for (String h : horarios) {
                int[] horario = formatarHorario(h);

                int horaFormatada = horario[0];
                int minutoFormatado = horario[1];

                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, horaFormatada);
                calendar.set(Calendar.MINUTE, minutoFormatado);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);

                calendar.set(Calendar.DAY_OF_WEEK, dia.getValor());

                if (calendar.before(Calendar.getInstance())) {
                    calendar.add(Calendar.WEEK_OF_YEAR, 1);
                }

                Intent intent = new Intent(this, AlarmeReceiver.class);
                intent.putExtra("medicamento",medicamento);
                int requestCode =  calcularRequestCode(medicamento.getId(),dia,horaFormatada,minutoFormatado);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(this, requestCode, intent, PendingIntent.FLAG_IMMUTABLE);

                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                if (alarmManager != null) {
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                }
            }
        }
    }
    public static int calcularRequestCode(long medicamentoId, DiaDaSemana dia, int hora, int minuto) {
        return (int)medicamentoId * 10000 + dia.getValor() * 1000 + hora * 100 + minuto;
    }
    private static int[] formatarHorario(String horario) {
        int[] horariosInt = new int[2];

        String[] horarioSplit = horario.split(":");
        horariosInt[0] = Integer.parseInt(horarioSplit[0]);
        horariosInt[1] = Integer.parseInt(horarioSplit[1]);

        return horariosInt;
    }
    private void criarNotificationChannel() {

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

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }
    ///////////////////////////////////////////////////////
    private void obterListaDeHorarios() {
        int qtdRegistros = this.tabelaHorarios.getChildCount();

        for (int i = 0; i < qtdRegistros; i++) {
            TableRow linhaAtual = (TableRow) tabelaHorarios.getChildAt(i);
            for (int j = 0; j < linhaAtual.getChildCount(); j++) {
                View view = linhaAtual.getChildAt(j);
                if (view instanceof EditText) {
                    EditText editText = (EditText) view;
                    String horario = editText.getText().toString();
                    this.listaHorarios.add(horario);
                }
            }
        }
    }
    private void atualizarTabelaHorarioDasDoses(int dosesPorDia) {
        this.tabelaHorarios.removeAllViews();
        for (int i = 0; i < dosesPorDia; i++) {
            TableRow linha = new TableRow(this);
            TextView numRegistro = new TextView(this);
            numRegistro.setText("Dose " + (i + 1));
            numRegistro.setPadding(16, 16, 16, 16);
            numRegistro.setTextSize(17);
            numRegistro.setTextColor(Color.parseColor("#000000"));
            numRegistro.setTypeface(Typeface.DEFAULT_BOLD);

            EditText horarioDesejado = new EditText(this);
            horarioDesejado.setTextSize(17);
            horarioDesejado.setTextColor(Color.parseColor("#000000"));

            linha.addView(numRegistro);
            linha.addView(horarioDesejado);
            this.tabelaHorarios.addView(linha);
        }
    }

    private boolean verificarCampos(){
        return !editTextNomeMedicamento.getText().toString().trim().isEmpty() &&
                !editTextDosesPorEmbalagem.getText().toString().trim().isEmpty() &&
                Integer.parseInt(editTextDosesPorEmbalagem.getText().toString().trim()) > 0 &&
                !editTextDosesPorDia.getText().toString().trim().isEmpty() &&
                !editTextEstoqueCritico.getText().toString().trim().isEmpty() &&
                !editTextDosesRestantes.getText().toString().trim().isEmpty() &&
                !listaDeDiasDaSemana.isEmpty() &&
                !listaHorarios.isEmpty() &&
                Integer.parseInt(editTextDosesPorDia.getText().toString().trim()) > 0;
    }

    private void obterDiasDaSemana(ArrayList<CheckBox> cbDiasDaSemana) {
        this.listaDeDiasDaSemana = new ArrayList<DiaDaSemana>();
        if (cbDiasDaSemana.get(0).isChecked()) {
            this.listaDeDiasDaSemana.add(DiaDaSemana.SEGUNDA);
        }
        if (cbDiasDaSemana.get(1).isChecked()) {
            this.listaDeDiasDaSemana.add(DiaDaSemana.TERCA);
        }
        if (cbDiasDaSemana.get(2).isChecked()) {
            this.listaDeDiasDaSemana.add(DiaDaSemana.QUARTA);
        }
        if (cbDiasDaSemana.get(3).isChecked()) {
            this.listaDeDiasDaSemana.add(DiaDaSemana.QUINTA);
        }
        if (cbDiasDaSemana.get(4).isChecked()) {
            this.listaDeDiasDaSemana.add(DiaDaSemana.SEXTA);
        }
        if (cbDiasDaSemana.get(5).isChecked()) {
            this.listaDeDiasDaSemana.add(DiaDaSemana.SABADO);
        }
        if (cbDiasDaSemana.get(6).isChecked()) {
            this.listaDeDiasDaSemana.add(DiaDaSemana.DOMINGO);
        }
    }
}