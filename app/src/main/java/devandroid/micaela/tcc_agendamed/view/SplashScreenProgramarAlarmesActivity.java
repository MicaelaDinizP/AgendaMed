package devandroid.micaela.tcc_agendamed.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import devandroid.micaela.tcc_agendamed.R;
import devandroid.micaela.tcc_agendamed.controller.MedicamentoController;
import devandroid.micaela.tcc_agendamed.model.Medicamento;

public class SplashScreenProgramarAlarmesActivity extends AppCompatActivity {
    private static final int SPLASH_DURATION = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen_programar_alarmes);


        MedicamentoController medicamentoController = new MedicamentoController(this);
        medicamentoController.abrirConexao();

        Intent intent = getIntent();
        Medicamento medicamento = intent.getParcelableExtra("medicamento");
        medicamento.setQuantidadeDosesRestantes(medicamento.getQuantidadeDosesRestantes()-1);
        medicamentoController.editar(medicamento);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SplashScreenProgramarAlarmesActivity.this, MedicamentoActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, SPLASH_DURATION);
        medicamentoController.fecharConexao();
    }
}