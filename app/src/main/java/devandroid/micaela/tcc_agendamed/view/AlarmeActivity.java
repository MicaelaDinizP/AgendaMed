package devandroid.micaela.tcc_agendamed.view;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import devandroid.micaela.tcc_agendamed.R;
import devandroid.micaela.tcc_agendamed.controller.MedicamentoController;
import devandroid.micaela.tcc_agendamed.model.DiaDaSemana;
import devandroid.micaela.tcc_agendamed.model.Medicamento;

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
        TableRow tableRow = new TableRow(this);
        GridLayout gridLayout = new GridLayout(this);

        TableRow.LayoutParams params = new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.FILL_HORIZONTAL;
        params.topMargin = 15;
        params.leftMargin = 20;
        gridLayout.setLayoutParams(params);

        gridLayout.setColumnCount(2);
        gridLayout.setRowCount(2);
        gridLayout.setAlignmentMode(GridLayout.ALIGN_MARGINS);
        gridLayout.setUseDefaultMargins(true);
        gridLayout.setPadding(8, 8, 8, 8);

        gridLayout.setBackgroundColor(getResources().getColor(R.color.backgroundComfyWhite));

        TextView textViewNomeMedicamento = new TextView(this);
        textViewNomeMedicamento.setText(med.getNomeMedicamento());
        textViewNomeMedicamento.setTextSize(18);
        textViewNomeMedicamento.setTextColor(Color.BLACK);
        textViewNomeMedicamento.setGravity(Gravity.CENTER);
        textViewNomeMedicamento.setTypeface(null, Typeface.ITALIC);
        GridLayout.LayoutParams params1 = new GridLayout.LayoutParams();
        params1.rowSpec = GridLayout.spec(0);
        params1.columnSpec = GridLayout.spec(0, 2);
        textViewNomeMedicamento.setPadding(0,0,0,10);
        textViewNomeMedicamento.setLayoutParams(params1);
        gridLayout.addView(textViewNomeMedicamento);

        String horariosOrdenados = ordenarHorariosParaVisualizacao(med);
        TextView textViewHorariosAlarmes = new TextView(this);
        textViewHorariosAlarmes.setText(horariosOrdenados);
        textViewHorariosAlarmes.setTextSize(18);
        textViewHorariosAlarmes.setTextColor(Color.BLACK);
        textViewHorariosAlarmes.setGravity(Gravity.CENTER);
        textViewHorariosAlarmes.setPadding(90,0,90,0);
        textViewHorariosAlarmes.setTypeface(null, Typeface.ITALIC);
        gridLayout.addView(textViewHorariosAlarmes);

        String diaDaSemanaOrdenado = ordenarDiasDaSemanaParaVisualizacao(med);
        TextView textViewDiasDaSemana = new TextView(this);
        textViewDiasDaSemana.setText(diaDaSemanaOrdenado);
        textViewDiasDaSemana.setTextSize(18);
        textViewDiasDaSemana.setTextColor(Color.BLACK);
        textViewDiasDaSemana.setGravity(Gravity.CENTER);
        textViewDiasDaSemana.setTypeface(null, Typeface.ITALIC);
        gridLayout.addView(textViewDiasDaSemana);

        tableRow.addView(gridLayout);

        this.tabelaAlarmes.addView(tableRow);
    }

    private String ordenarDiasDaSemanaParaVisualizacao(Medicamento med) {
        StringBuilder diasOrdenados = new StringBuilder();
        Set<DiaDaSemana> diasAdicionados = new HashSet<>();

        for (int i = 0; i < med.getDiasDaSemana().size(); i++) {
            DiaDaSemana dia = med.getDiasDaSemana().get(i);

            if (!diasAdicionados.contains(dia)) {
                if (diasOrdenados.length() > 0) {
                    diasOrdenados.append(" | ").append(dia.name());
                } else {
                    diasOrdenados.append(dia.name());
                }

                diasAdicionados.add(dia);

                if (i % 2 != 0) {
                    diasOrdenados.append("\n");
                }
            }
        }
        return diasOrdenados.toString();
    }

    private String ordenarHorariosParaVisualizacao(Medicamento med) {
        StringBuilder horariosOrdenados = new StringBuilder();
        boolean isFirst = true;
        for (int i = 0; i < med.getListaHorarios().size(); i++) {
            String hora = med.getListaHorarios().get(i);

            if (!isFirst) {
                horariosOrdenados.append(" | ");
            }
            horariosOrdenados.append(hora);
            if (i != 0 && i % 2 == 1) {
                horariosOrdenados.append("\n");
            }
            isFirst = false;
        }

        if(med.getListaHorarios().size() == 1){
            horariosOrdenados.append("           ");
        }
        return horariosOrdenados.toString();
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
