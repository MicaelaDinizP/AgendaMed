package devandroid.micaela.tcc_agendamed.view;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import devandroid.micaela.tcc_agendamed.R;
import devandroid.micaela.tcc_agendamed.controller.MedicamentoController;
import devandroid.micaela.tcc_agendamed.model.DiaDaSemana;
import devandroid.micaela.tcc_agendamed.model.Medicamento;

public class EdicaoMedicamentoActivity extends AppCompatActivity {
    private Medicamento medicamentoParaEditar;
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
    private ArrayList<CheckBox>cbDiasDaSemana;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edicao_medicamento);

        Intent intent = getIntent();
        this.medicamentoParaEditar = intent.getParcelableExtra("medicamento");

        this.cbDiasDaSemana = new ArrayList<CheckBox>();
        this.listaHorarios = new ArrayList<>();
        this.medicamentoController = new MedicamentoController(EdicaoMedicamentoActivity.this);

        this.editTextNomeMedicamento = findViewById(R.id.editTextNomeMedicamento);
        this.editTextDosesPorEmbalagem = findViewById(R.id.editTextDosesPorEmbalagem);
        this.editTextDosesPorDia = findViewById(R.id.editTextDosesPorDia);
        this.editTextEstoqueCritico = findViewById(R.id.editTextEstoqueCritico);
        this.editTextDosesRestantes = findViewById(R.id.editTextDosesRestantes);
        this.checkBoxPausarUso = findViewById(R.id.checkBoxPausarUso);
        this.checkBoxCriarAlarme = findViewById(R.id.checkBoxCriarAlarme);
        this.tabelaHorarios = findViewById(R.id.tableHorarios);

        cbDiasDaSemana.add(findViewById(R.id.checkBoxDiaSegunda));
        cbDiasDaSemana.add(findViewById(R.id.checkBoxDiaTerca));
        cbDiasDaSemana.add(findViewById(R.id.checkBoxDiaQuarta));
        cbDiasDaSemana.add(findViewById(R.id.checkBoxDiaQuinta));
        cbDiasDaSemana.add(findViewById(R.id.checkBoxDiaSexta));
        cbDiasDaSemana.add(findViewById(R.id.checkBoxDiaSabado));
        cbDiasDaSemana.add(findViewById(R.id.checkBoxDiaDomingo));

        this.montarMedicamentoParaEdicao();
//
//        this.editTextDosesPorDia.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {}
//            @Override
//            public void afterTextChanged(Editable s) {
//                atualizarTabelaHorarioDasDoses(Integer.parseInt(s.toString()));
//            }
//        });
//
//        this.btnFechar = findViewById(R.id.btnFechar);
//        this.btnFechar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//
//        this.btnApagar = findViewById(R.id.btnApagar);
//        btnApagar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(EdicaoMedicamentoActivity.this, "Botão clicado : Apagar " , Toast.LENGTH_SHORT).show();
//                finish();
//            }
//        });
//        this.btnSalvar = findViewById(R.id.btnSalvar);
//        btnSalvar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                obterListaDeHorarios();
//                obterDiasDaSemana(cbDiasDaSemana);
//                if (verificarCampos()) {
//                    Toast.makeText(EdicaoMedicamentoActivity.this, "Elegível para salvar as mudanças." , Toast.LENGTH_SHORT).show();
//                }else{
//                    Toast.makeText(EdicaoMedicamentoActivity.this, "ERRO: Campos obrigatórios não preenchidos." , Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
    }

    private void montarMedicamentoParaEdicao() {
        this.editTextNomeMedicamento.setText(this.medicamentoParaEditar.getNomeMedicamento());
        this.editTextDosesPorEmbalagem.setText(String.valueOf(this.medicamentoParaEditar.getQuantidadeDosesPorEmbalagem()));
        this.editTextDosesPorDia.setText(String.valueOf(this.medicamentoParaEditar.getDosesPorDia()));
        this.editTextEstoqueCritico.setText(String.valueOf(this.medicamentoParaEditar.getQuantidadeEstoqueCritico()));
        this.editTextDosesRestantes.setText(String.valueOf(this.medicamentoParaEditar.getQuantidadeDosesRestantes()));
        this.checkBoxPausarUso.setChecked(this.medicamentoParaEditar.isUsoPausado());
        this.checkBoxCriarAlarme.setChecked(this.medicamentoParaEditar.isCriarAlarmes());
        this.listaHorarios = this.medicamentoParaEditar.getListaHorarios();
        this.montarTabelaDeHorarios();
        this.listaDeDiasDaSemana = this.medicamentoParaEditar.getDiasDaSemana();
        this.montarSelecaoDiasDaSemana();
    }
    private void montarTabelaDeHorarios() {
        int qtdRegistros = this.listaHorarios.size();

        for (int i = 0; i < qtdRegistros; i++) {
            TableRow linha = new TableRow(this);
            TextView numRegistro = new TextView(this);
            numRegistro.setText("Dose " + (i + 1));
            numRegistro.setPadding(16, 16, 16, 16);
            numRegistro.setTextSize(17);
            numRegistro.setTextColor(Color.parseColor("#000000"));
            numRegistro.setTypeface(Typeface.DEFAULT_BOLD);

            EditText horarioDesejado = new EditText(this);
            horarioDesejado.setTextSize(17);
            horarioDesejado.setText(this.listaHorarios.get(i));
            horarioDesejado.setTextColor(Color.parseColor("#000000"));

            linha.addView(numRegistro);
            linha.addView(horarioDesejado);
            this.tabelaHorarios.addView(linha);
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
    private void montarSelecaoDiasDaSemana() {
        DiaDaSemana[] dias = DiaDaSemana.values();
        for (int i = 0; i < dias.length; i++) {
            if (this.listaDeDiasDaSemana.contains(dias[i])) {
                this.cbDiasDaSemana.get(i).setChecked(true);
            } else {
                this.cbDiasDaSemana.get(i).setChecked(false);
            }
        }
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
    private boolean verificarCampos(){
        return !editTextNomeMedicamento.getText().toString().trim().isEmpty() &&
                !editTextDosesPorEmbalagem.getText().toString().trim().isEmpty() &&
                !editTextDosesPorDia.getText().toString().trim().isEmpty() &&
                !editTextEstoqueCritico.getText().toString().trim().isEmpty() &&
                !editTextDosesRestantes.getText().toString().trim().isEmpty() &&
                !listaDeDiasDaSemana.isEmpty() &&
                !listaHorarios.isEmpty() &&
                Integer.parseInt(editTextDosesPorDia.getText().toString().trim()) > 0;
    }

}
