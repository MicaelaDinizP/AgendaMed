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

    private void atualizarRowTitulos() {
        TableRow rowTitulos = findViewById(R.id.rowTitulos);
        rowTitulos.removeAllViews(); // Limpa os filhos existentes, se necessário

        // Adicionando o primeiro TextView (título dos medicamentos)
        TextView textViewTitulo = new TextView(this);
        textViewTitulo.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        textViewTitulo.setPadding(20, 20, 20, 20);
        textViewTitulo.setText("Medicamentos");
        rowTitulos.addView(textViewTitulo); // Adiciona o TextView à TableRow

        // Adicionando o segundo TextView (para editar)
        TextView textViewEditar = new TextView(this);
        textViewEditar.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        textViewEditar.setPadding(20, 20, 20, 20);
        textViewEditar.setText("Editar");
        rowTitulos.addView(textViewEditar); // Adiciona o TextView à TableRow
    }

    private void mostrarRegistroAusente() {
        TableRow rowTitulos = findViewById(R.id.rowTitulos);
        rowTitulos.removeAllViews(); // Limpa as Views existentes se necessário

        TextView textViewRegistroAusente = new TextView(this);
        textViewRegistroAusente.setId(View.generateViewId());
        textViewRegistroAusente.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        textViewRegistroAusente.setPadding(20, 20, 20, 20);
        textViewRegistroAusente.setText("Não há medicamentos cadastrados.");
        textViewRegistroAusente.setTypeface(null, Typeface.ITALIC);
        textViewRegistroAusente.setGravity(Gravity.CENTER);
        textViewRegistroAusente.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);

        rowTitulos.addView(textViewRegistroAusente); // Adiciona o TextView à TableRow
    }
    private void recriarMesagemTabelaVazia() {
        TableLayout tableMedicamentos = findViewById(R.id.tableMedicamentos);
        TableRow rowTitulos = findViewById(R.id.rowTitulos);

        // Se rowTitulos for nulo, cria uma nova TableRow
        if (rowTitulos == null) {
            rowTitulos = new TableRow(this);
            rowTitulos.setId(View.generateViewId()); // Gera um ID único
            // Adiciona a nova TableRow ao TableLayout
            tableMedicamentos.addView(rowTitulos);
        } else {
            // Se já existe, remove os views existentes
            rowTitulos.removeAllViews();
        }

        // Criar novo TextView
        TextView textViewRegistroAusente = new TextView(this);
        textViewRegistroAusente.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT)); // Layout width and height
        textViewRegistroAusente.setPadding(20, 20, 20, 20); // Padding
        textViewRegistroAusente.setText("Não há medicamentos cadastrados."); // Texto
        textViewRegistroAusente.setTypeface(null, Typeface.ITALIC); // Estilo de texto (itálico)
        textViewRegistroAusente.setGravity(Gravity.CENTER); // Gravidade (centro)

        // Adicionar o novo TextView na TableRow
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
            // Criação do LinearLayout para agrupar a flag e o nome do medicamento
            LinearLayout container = new LinearLayout(this);
            container.setOrientation(LinearLayout.VERTICAL);
            container.setLayoutParams(new TableRow.LayoutParams(
                    0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
            container.setPadding(8, 8, 8, 8);
            container.setGravity(Gravity.CENTER);

            // Criação do TextView para a flag
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
            } else if (medicamento.getQuantidadeDosesRestantes() < medicamento.getQuantidadeEstoqueCritico()) {
                flag.setText("Esgotando");
                flag.setBackgroundColor(Color.YELLOW);
                flag.setTextColor(Color.BLACK);
            }

            // Adiciona a flag ao container
            container.addView(flag);

            // Criação do TextView para o nome do medicamento
            TextView nomeMedicamento = new TextView(this);
            nomeMedicamento.setId(View.generateViewId());
            nomeMedicamento.setText(medicamento.getNomeMedicamento());
            nomeMedicamento.setTextSize(18); // Aumenta o tamanho da fonte para 18sp
            nomeMedicamento.setGravity(Gravity.CENTER);
            nomeMedicamento.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

            // Ajuste de cor de fundo com base na situação
            if (medicamento.isUsoPausado()) {
                nomeMedicamento.setBackgroundColor(Color.BLUE);
                nomeMedicamento.setTextColor(Color.WHITE);
            } else if (medicamento.getQuantidadeDosesRestantes() == 0) {
                nomeMedicamento.setBackgroundColor(Color.RED);
                nomeMedicamento.setTextColor(Color.WHITE);
            } else if (medicamento.getQuantidadeDosesRestantes() < medicamento.getQuantidadeEstoqueCritico()) {
                nomeMedicamento.setBackgroundColor(Color.YELLOW);
                nomeMedicamento.setTextColor(Color.BLACK);
            }

            // Adiciona o nome do medicamento ao container
            container.addView(nomeMedicamento);

            // Adiciona o container à linha da tabela
            row.addView(container);

        } else {
            // Criação do TextView para o nome do medicamento sem flag
            TextView nomeMedicamento = new TextView(this);
            nomeMedicamento.setId(View.generateViewId());
            nomeMedicamento.setText(medicamento.getNomeMedicamento());
            nomeMedicamento.setTextSize(18); // Aumenta o tamanho da fonte para 18sp
            nomeMedicamento.setPadding(8, 8, 8, 8);
            nomeMedicamento.setGravity(Gravity.CENTER);
            nomeMedicamento.setLayoutParams(new TableRow.LayoutParams(
                    0, TableRow.LayoutParams.WRAP_CONTENT, 1f));

            // Ajuste de cor de fundo com base na situação (sem flag)
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

            // Adiciona o nome do medicamento diretamente à linha da tabela
            row.addView(nomeMedicamento);
        }

        // Criação do botão de edição
        Button botaoEditar = new Button(this);
        botaoEditar.setText("Editar");
        botaoEditar.setPadding(8, 8, 8, 8);
        botaoEditar.setGravity(Gravity.CENTER);
        botaoEditar.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        botaoEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MedicamentoActivity.this, EdicaoMedicamentoActivity.class);
                intent.putExtra("medicamento", medicamento);
                startActivity(intent);
            }
        });

        // Adiciona o botão de edição à linha da tabela
        row.addView(botaoEditar);

        // Adiciona a linha à tabela
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