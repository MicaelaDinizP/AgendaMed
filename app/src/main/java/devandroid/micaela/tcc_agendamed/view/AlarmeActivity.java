package devandroid.micaela.tcc_agendamed.view;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import devandroid.micaela.tcc_agendamed.R;
import devandroid.micaela.tcc_agendamed.controller.MedicamentoController;
import devandroid.micaela.tcc_agendamed.model.Medicamento;
import devandroid.micaela.tcc_agendamed.model.MenuFragment;

public class AlarmeActivity extends AppCompatActivity {
    private TableLayout tabelaAlarmes;
    private Button btnDefinirAlarmes;
    private List<Medicamento> listaMedicamentos;
    private List<Medicamento> listaMedicamentosComAlarmeAtivo;

    private MedicamentoController medicamentoController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarme);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(new MenuFragment(), "MENU_FRAGMENT")
                    .commitNow();
        }
        this.tabelaAlarmes = findViewById(R.id.tableAlarmes);
        this.btnDefinirAlarmes = findViewById(R.id.btnDefinirAlarmes);
        this.medicamentoController = new MedicamentoController(this);
        this.btnDefinirAlarmes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AlarmeActivity.this, MedicamentoActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        this.listaMedicamentos = new ArrayList<>();
        this.listaMedicamentosComAlarmeAtivo = new ArrayList<>();
        obterMedicamentosCadastrados();
        this.limparTabela();
        if (this.listaMedicamentos.isEmpty()) {
            recriarMesagemTabelaVazia("Não há alarmes definidos. Não há medicamentos cadastrados para definição do alarme.");
        } else {
            if(!this.filtrarAlarmesDefinidos()){
                recriarMesagemTabelaVazia("Não há alarmes definidos.");
            }
            desenharTabela();
        }
    }

    private void desenharTabela() {
        for (Medicamento med : this.listaMedicamentosComAlarmeAtivo) {
            desenharLinha(med);
        }
    }

    private void desenharLinha(Medicamento med) {
        // Aqui monta o registro
    }

    private boolean filtrarAlarmesDefinidos() {
        for (Medicamento med : this.listaMedicamentos) {
            if(med.isAlarmeAtivo()){
                this.listaMedicamentosComAlarmeAtivo.add(med);
            }
        }
        return !this.listaMedicamentosComAlarmeAtivo.isEmpty();
    }

    private void recriarMesagemTabelaVazia(String mensagemExibida) {
        TableRow rowRegistroAlarme = findViewById(R.id.rowRegistroAlarme);
        if (rowRegistroAlarme == null) {
            rowRegistroAlarme = new TableRow(this);
            rowRegistroAlarme.setId(View.generateViewId()); // Gera um ID único
            // Adiciona a nova TableRow ao TableLayout
            tabelaAlarmes.addView(rowRegistroAlarme);
        } else {
            rowRegistroAlarme.removeAllViews();
        }

        TextView textViewRegistroAusente = new TextView(this);
        textViewRegistroAusente.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT)); // Layout width and height
        textViewRegistroAusente.setPadding(20, 20, 20, 20); // Padding
        textViewRegistroAusente.setText(mensagemExibida); // Texto
        textViewRegistroAusente.setTypeface(null, Typeface.ITALIC); // Estilo de texto (itálico)
        textViewRegistroAusente.setGravity(Gravity.CENTER); // Gravidade (centro)

        // Adicionar o novo TextView na TableRow
        rowRegistroAlarme.addView(textViewRegistroAusente);
    }

    public void obterMedicamentosCadastrados(){
        this.medicamentoController.abrirConexao();
        this.listaMedicamentos = this.medicamentoController.obterTodos(MainActivity.USUARIO_LOGADO);
        this.medicamentoController.fecharConexao();
    }
    private void limparTabela() {
        int childCount = this.tabelaAlarmes.getChildCount();
        if (childCount > 0) {
            this.tabelaAlarmes.removeViews(0, childCount);
        }
    }


}
