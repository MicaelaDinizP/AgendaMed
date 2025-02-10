package devandroid.micaela.tcc_agendamed.view;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Layout;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

import devandroid.micaela.tcc_agendamed.R;
import devandroid.micaela.tcc_agendamed.controller.MedicamentoController;
import devandroid.micaela.tcc_agendamed.model.Medicamento;
import devandroid.micaela.tcc_agendamed.model.MenuFragment;
import devandroid.micaela.tcc_agendamed.model.Usuario;

public class MedicamentoActivity extends AppCompatActivity {
    private Button btnCriarMedicamento;
    private Button btnReporMedicamento;
    private TableLayout tabelaMedicamentos;
    private List<Medicamento> listaMedicamentos = null;

    private MedicamentoController medicamentoController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicamento);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(new MenuFragment(), "MENU_FRAGMENT")
                    .commitNow();
        }

        this.tabelaMedicamentos = findViewById(R.id.tableMedicamentos);
        this.medicamentoController = new MedicamentoController(this);
        this.btnReporMedicamento = findViewById(R.id.btnReporMedicamento);
        this.btnCriarMedicamento = findViewById(R.id.btnCriarMedicamento);
        this.btnCriarMedicamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MedicamentoActivity.this, CadastroMedicamentoActivity.class);
                startActivity(intent);
            }
        });
        this.btnReporMedicamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MedicamentoActivity.this, ReposicaoMedicamentoActivity.class);
                startActivity(intent);
                Toast.makeText(MedicamentoActivity.this, "Botão repor clicado." , Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();

        this.listaMedicamentos = new ArrayList<>();
        obterMedicamentosCadastrados();

        limparTabela();

        if (this.listaMedicamentos.isEmpty()) {
            recriarMesagemTabelaVazia();
        } else {
            desenharTabela();
        }
    }
    private void recriarMesagemTabelaVazia() {
        TableLayout tableMedicamentos = findViewById(R.id.tableMedicamentos);
        TableRow rowTitulos = findViewById(R.id.rowTitulos);

        if (rowTitulos == null) {
            rowTitulos = new TableRow(this);
            rowTitulos.setId(View.generateViewId());
            tableMedicamentos.addView(rowTitulos);
        } else {
            rowTitulos.removeAllViews();
        }
        TextView textViewRegistroAusente = new TextView(this);
        textViewRegistroAusente.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        textViewRegistroAusente.setPadding(20, 20, 20, 20);
        textViewRegistroAusente.setText("Não há medicamentos cadastrados.");
        textViewRegistroAusente.setTypeface(null, Typeface.ITALIC);
        textViewRegistroAusente.setGravity(Gravity.CENTER);

        rowTitulos.addView(textViewRegistroAusente);
    }

    public void obterMedicamentosCadastrados(){
        this.medicamentoController.abrirConexao();
        this.listaMedicamentos = this.medicamentoController.obterTodos(MainActivity.USUARIO_LOGADO);
        this.medicamentoController.fecharConexao();
    }
    public void desenharTabela(){
        for (Medicamento med : this.listaMedicamentos) {
            desenharLinha(med);
        }
    }
    public void desenharLinha(Medicamento medicamento) {
        TableRow row = new TableRow(this);
        row.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

        boolean flagNecessaria = medicamento.isUsoPausado() ||
                medicamento.getQuantidadeDosesRestantes() <= medicamento.getQuantidadeEstoqueCritico();

        if (flagNecessaria) {
            LinearLayout container = new LinearLayout(this);
            container.setOrientation(LinearLayout.VERTICAL);
            container.setLayoutParams(new TableRow.LayoutParams(
                    0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
            container.setPadding(8, 8, 8, 8);
            container.setGravity(Gravity.CENTER);

            TextView flag = new TextView(this);
            flag.setTypeface(null, Typeface.BOLD);
            flag.setGravity(Gravity.CENTER);

            if (medicamento.isUsoPausado()) {
                flag.setText("Pausado");
                flag.setBackgroundColor(Color.BLUE);
                flag.setTextColor(Color.WHITE);
            } else if (medicamento.getQuantidadeDosesRestantes() == 0) {
                flag.setText("Esgotado");
                flag.setBackgroundColor(Color.RED);
                flag.setTextColor(Color.WHITE);
            } else if (medicamento.getQuantidadeDosesRestantes() <= medicamento.getQuantidadeEstoqueCritico()) {
                flag.setText("Esgotando");
                flag.setBackgroundColor(Color.YELLOW);
                flag.setTextColor(Color.BLACK);
            }
            container.addView(flag);

            TextView nomeMedicamento = new TextView(this);
            nomeMedicamento.setId(View.generateViewId());
            nomeMedicamento.setText(medicamento.getNomeMedicamento());
            nomeMedicamento.setTextSize(18);
            nomeMedicamento.setGravity(Gravity.CENTER);
            nomeMedicamento.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

            if (medicamento.isUsoPausado()) {
                nomeMedicamento.setBackgroundColor(Color.BLUE);
                nomeMedicamento.setTextColor(Color.WHITE);
            } else if (medicamento.getQuantidadeDosesRestantes() == 0) {
                nomeMedicamento.setBackgroundColor(Color.RED);
                nomeMedicamento.setTextColor(Color.WHITE);
            } else if (medicamento.getQuantidadeDosesRestantes() <= medicamento.getQuantidadeEstoqueCritico()) {
                nomeMedicamento.setBackgroundColor(Color.YELLOW);
                nomeMedicamento.setTextColor(Color.BLACK);
            }

            container.addView(nomeMedicamento);

            row.addView(container);

        } else {
            TextView nomeMedicamento = new TextView(this);
            nomeMedicamento.setId(View.generateViewId());
            nomeMedicamento.setText(medicamento.getNomeMedicamento());
            nomeMedicamento.setTextSize(18);
            nomeMedicamento.setPadding(8, 8, 8, 8);
            nomeMedicamento.setGravity(Gravity.CENTER);
            nomeMedicamento.setLayoutParams(new TableRow.LayoutParams(
                    0, TableRow.LayoutParams.WRAP_CONTENT, 1f));

            if (medicamento.getQuantidadeDosesRestantes() == 0) {
                nomeMedicamento.setBackgroundColor(Color.RED);
                nomeMedicamento.setTextColor(Color.WHITE);
            } else if (medicamento.getQuantidadeDosesRestantes() < medicamento.getQuantidadeEstoqueCritico()) {
                nomeMedicamento.setBackgroundColor(Color.YELLOW);
                nomeMedicamento.setTextColor(Color.BLACK);
            } else {
                nomeMedicamento.setBackgroundColor(Color.WHITE);
                nomeMedicamento.setTextColor(Color.BLACK);
            }

            row.addView(nomeMedicamento);
        }

        Button botaoEditar = new Button(this);
        botaoEditar.setText("Editar");
        botaoEditar.setPadding(8, 8, 8, 8);
        botaoEditar.setGravity(Gravity.CENTER);
        botaoEditar.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.backgroundBasePurple));
        botaoEditar.setLayoutParams(new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
        botaoEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MedicamentoActivity.this, EdicaoMedicamentoActivity.class);
                intent.putExtra("medicamento", medicamento);
                startActivity(intent);
            }
        });

        row.addView(botaoEditar);

        this.tabelaMedicamentos.addView(row);
    }

    private int getPaddingEmPixels(int paddingEmDp) {
        float densidade = this.getResources().getDisplayMetrics().density;
        return Math.round(paddingEmDp * densidade);
    }
    private void limparTabela() {
        int childCount = this.tabelaMedicamentos.getChildCount();
        if (childCount > 0) {
            this.tabelaMedicamentos.removeViews(0, childCount);
        }
    }
    private void habilitarBotaoReporMedicamento() {
        this.btnReporMedicamento.setEnabled(true);
    }
    private void desabilitarBotaoReporMedicamento() {
        this.btnReporMedicamento.setEnabled(false);
    }

}