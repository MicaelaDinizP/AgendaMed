package devandroid.micaela.tcc_agendamed.view;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import devandroid.micaela.tcc_agendamed.R;
import devandroid.micaela.tcc_agendamed.model.DiaDaSemana;
import devandroid.micaela.tcc_agendamed.model.Medicamento;

public class CadastroMedicamentoActivity extends AppCompatActivity {

   // private MedicamentoController MedicamentoController;
    private TableLayout tableHorarios;
    private EditText editTextNomeMedicamento;
    private EditText editTextDosesPorEmbalagem;
    private EditText editTextDosesPorDia;
    private List<String> horarios;
    private EditText editTextEstoqueCritico;
    private EditText editTextDosesRestantes;
    private List<DiaDaSemana> listaDeDiasDaSemana;
    private CheckBox checkBoxPausarUso;
    private CheckBox checkBoxCriarAlarme;
    private ImageButton btnFechar;
    private ImageButton btnApagar;
    private ImageButton btnSalvar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_medicamento);

        //this.medicamentoController = new MedicamentoController(CadastroMedicamentoActivity.this);
        this.editTextNomeMedicamento = findViewById(R.id.editTextNomeMedicamento);
        this.editTextDosesPorEmbalagem = findViewById(R.id.editTextDosesPorEmbalagem);
        this.editTextDosesPorDia = findViewById(R.id.editTextDosesPorDia);
        this.editTextEstoqueCritico = findViewById(R.id.editTextEstoqueCritico);
        this.editTextDosesRestantes = findViewById(R.id.editTextDosesRestantes);

        //PEGAR A LISTA DE HORARIOS
        //FAKE PARA PROSSEGUIR O DESENVOLVIMENTO:
        List<String> listaEstaticaPraTeste = new ArrayList<String>();
        listaEstaticaPraTeste.add("12:00:00");
        listaEstaticaPraTeste.add("13:00:00");

        String nomeMedicamento = this.editTextNomeMedicamento.getText().toString();
        int qtdDosesPorEmbalagem = Integer.parseInt(this.editTextDosesPorEmbalagem.getText().toString());
        int qtdDosesPorDia = Integer.parseInt(this.editTextDosesPorDia.getText().toString());
        int qtdEstoqueCritico = Integer.parseInt(this.editTextEstoqueCritico.getText().toString());
        int qtdDosesRestantes = Integer.parseInt(this.editTextDosesRestantes.getText().toString());

        List<CheckBox> cbDiasDaSemana = new ArrayList<CheckBox>();

        cbDiasDaSemana.add(findViewById(R.id.checkBoxDiaSegunda));
        cbDiasDaSemana.add(findViewById(R.id.checkBoxDiaTerca));
        cbDiasDaSemana.add(findViewById(R.id.checkBoxDiaQuarta));
        cbDiasDaSemana.add(findViewById(R.id.checkBoxDiaQuinta));
        cbDiasDaSemana.add(findViewById(R.id.checkBoxDiaSexta));
        cbDiasDaSemana.add(findViewById(R.id.checkBoxDiaSabado));
        cbDiasDaSemana.add(findViewById(R.id.checkBoxDiaDomingo));

        this.obterDiasDaSemana(cbDiasDaSemana);

        this.checkBoxPausarUso = findViewById(R.id.checkBoxPausarUso);
        this.checkBoxCriarAlarme = findViewById(R.id.checkBoxCriarAlarme);

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
                Medicamento medicamento = new Medicamento(nomeMedicamento,MainActivity.USUARIO_LOGADO,qtdDosesPorEmbalagem,
                        listaDeDiasDaSemana, qtdDosesPorDia, qtdEstoqueCritico, qtdDosesRestantes, checkBoxPausarUso.isChecked(),
                        checkBoxCriarAlarme.isChecked(), checkBoxCriarAlarme.isChecked(), listaEstaticaPraTeste);
                Toast.makeText(CadastroMedicamentoActivity.this, "Botão clicado : Salvar " , Toast.LENGTH_SHORT).show();
                finish();
                //medicamentoController.abrirConexao();
                //String nomeUsuario = editTextNomeUsuario.getText().toString();
                //long idRetornado = medicamentoController.inserir();
                //if(idRetornado != -1){
                  //  Toast.makeText(CadastroUsuarioActivity.this, "Usuario "+nomeUsuario+" cadastrado com sucesso!" , Toast.LENGTH_SHORT).show();
                   // finish();
                //}else{
                  //  Toast.makeText(CadastroUsuarioActivity.this, "ERRO: Não foi possível cadastrar o usuario." , Toast.LENGTH_SHORT).show();
                //}
                //medicamentoController.fecharConexao();
            }
        });

    }
    private void obterDiasDaSemana(List<CheckBox> cbDiasDaSemana) {
        this.listaDeDiasDaSemana = new ArrayList<DiaDaSemana>();
        for (CheckBox dia : cbDiasDaSemana) {
                if(dia.isChecked()){
                    switch (getResources().getResourceEntryName(dia.getId())){
                        case "checkBoxDiaSegunda":
                            this.listaDeDiasDaSemana.add(DiaDaSemana.SEGUNDA);
                            break;
                        case "checkBoxDiaTerca":
                            this.listaDeDiasDaSemana.add(DiaDaSemana.TERCA);
                            break;
                        case "checkBoxDiaQuarta":
                            this.listaDeDiasDaSemana.add(DiaDaSemana.QUARTA);
                            break;
                        case "checkBoxDiaQuinta":
                            this.listaDeDiasDaSemana.add(DiaDaSemana.QUINTA);
                            break;
                        case "checkBoxDiaSexta":
                            this.listaDeDiasDaSemana.add(DiaDaSemana.SEXTA);
                            break;
                        case "checkBoxDiaSabado":
                            this.listaDeDiasDaSemana.add(DiaDaSemana.SABADO);
                            break;
                        case "checkBoxDiaDomingo":
                            this.listaDeDiasDaSemana.add(DiaDaSemana.DOMINGO);
                    }
                }
        }
    }

}
