package devandroid.micaela.tcc_agendamed.view;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import devandroid.micaela.tcc_agendamed.R;
import devandroid.micaela.tcc_agendamed.controller.MedicamentoController;
import devandroid.micaela.tcc_agendamed.model.Medicamento;

public class ReposicaoMedicamentoActivity extends AppCompatActivity {
    private TableLayout tabelaMedicamentos;
    private ImageButton btnFechar;
    private Button btnSalvarReposicao;
    private List<Medicamento> listaMedicamentos = null;

    private MedicamentoController medicamentoController;
    private Map<Medicamento, CheckBox> reposicaoCheckBoxes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repor_medicamento);

        this.btnFechar = findViewById(R.id.btnFechar);
        this.tabelaMedicamentos = findViewById(R.id.tableMedicamentos);
        this.btnSalvarReposicao = findViewById(R.id.btnSalvarReposicao);
        this.medicamentoController = new MedicamentoController(this);
        this.reposicaoCheckBoxes = new HashMap<>();

        this.btnFechar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnSalvarReposicao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarReposicao();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarMedicamentos();
    }

    private void carregarMedicamentos() {
        this.listaMedicamentos = new ArrayList<>();
        this.listaMedicamentos = this.medicamentoController.obterTodos(MainActivity.USUARIO_LOGADO);

        if (listaMedicamentos.isEmpty()) {
            recriarMensagemTabelaVazia();
        } else {
            desenharTabela();
        }
    }

    private void recriarMensagemTabelaVazia() {
        tabelaMedicamentos.removeAllViews();
        TableRow row = new TableRow(this);

        TextView textView = new TextView(this);
        textView.setText("Não há medicamentos para repor.");
        textView.setTypeface(null, Typeface.ITALIC);
        textView.setGravity(Gravity.CENTER);
        row.addView(textView);

        tabelaMedicamentos.addView(row);
    }

    private void desenharTabela() {
        tabelaMedicamentos.removeAllViews();
        reposicaoCheckBoxes.clear();

        for (Medicamento med : listaMedicamentos) {
            desenharLinha(med);
        }
    }

    private void desenharLinha(Medicamento medicamento) {
        TableRow row = new TableRow(this);
        row.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

        TextView nomeMedicamento = new TextView(this);
        nomeMedicamento.setText(medicamento.getNomeMedicamento());
        nomeMedicamento.setTextSize(18);
        nomeMedicamento.setPadding(8, 8, 8, 8);
        nomeMedicamento.setGravity(Gravity.CENTER);

        TextView quantidadeRestante = new TextView(this);
        quantidadeRestante.setText("+"+String.valueOf(medicamento.getQuantidadeDosesPorEmbalagem()));
        quantidadeRestante.setGravity(Gravity.CENTER);

        CheckBox chkReposicao = new CheckBox(this);
        reposicaoCheckBoxes.put(medicamento, chkReposicao);

        row.addView(nomeMedicamento);
        row.addView(quantidadeRestante);
        row.addView(chkReposicao);

        tabelaMedicamentos.addView(row);
    }

    private void salvarReposicao() {
        for (Medicamento medicamento : listaMedicamentos) {
            CheckBox chk = reposicaoCheckBoxes.get(medicamento);
            if (chk != null && chk.isChecked()) {
                medicamento.setQuantidadeDosesRestantes(
                        medicamento.getQuantidadeDosesRestantes() + medicamento.getQuantidadeDosesPorEmbalagem()
                );
                medicamentoController.editar(medicamento);
            }
        }

        Toast.makeText(this, "Reposição salva com sucesso!", Toast.LENGTH_SHORT).show();
        finish();
    }
}