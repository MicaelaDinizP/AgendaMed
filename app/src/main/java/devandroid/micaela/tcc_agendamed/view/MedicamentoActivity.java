package devandroid.micaela.tcc_agendamed.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import devandroid.micaela.tcc_agendamed.R;
import devandroid.micaela.tcc_agendamed.model.MenuFragment;

public class MedicamentoActivity extends AppCompatActivity {

    private Button criarMedicamento;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicamento);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(new MenuFragment(), "MENU_FRAGMENT")
                    .commitNow();
        }

        this.criarMedicamento = findViewById(R.id.btnCriarMedicamento);
        this.criarMedicamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MedicamentoActivity.this, CadastroMedicamentoActivity.class);
                startActivity(intent);
            }
        });
    }
}
