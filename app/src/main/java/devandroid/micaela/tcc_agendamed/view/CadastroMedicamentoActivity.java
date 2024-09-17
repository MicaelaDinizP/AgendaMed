package devandroid.micaela.tcc_agendamed.view;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.text.TextWatcher;
import android.text.Editable;


import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import devandroid.micaela.tcc_agendamed.R;
import devandroid.micaela.tcc_agendamed.controller.MedicamentoController;
import devandroid.micaela.tcc_agendamed.model.DiaDaSemana;
import devandroid.micaela.tcc_agendamed.model.Medicamento;

public class CadastroMedicamentoActivity extends AppCompatActivity {
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
                atualizarTabelaHorarioDasDoses(Integer.parseInt(s.toString()));
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
                Toast.makeText(CadastroMedicamentoActivity.this, "Botão clicado : Apagar " , Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(CadastroMedicamentoActivity.this, "Botão clicado : Salvar ", Toast.LENGTH_SHORT).show();
                    medicamentoController.abrirConexao();
                    long idRetornado = medicamentoController.inserir(medicamento);
                    if(idRetornado != -1){
                        Toast.makeText(CadastroMedicamentoActivity.this, "Medicamento  cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
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