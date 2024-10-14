package devandroid.micaela.tcc_agendamed.view;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Layout;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
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
    protected void onResume(){
        super.onResume();
        this.listaMedicamentos = new ArrayList<Medicamento>();
        obterMedicamentosCadastrados();

        if(!this.listaMedicamentos.isEmpty()){
            this.limparTabela();
            this.desenharTabela();
            this.habilitarBotaoReporMedicamento();
        }else{
            this.desabilitarBotaoReporMedicamento();
            //Criar o text view do erro aqui e remover  do xml
        }
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
    public void desenharLinha(Medicamento medicamento){
        TableRow row = new TableRow(this);
        row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

        TextView nomeMedicamento = new TextView(this);
        nomeMedicamento.setId(View.generateViewId());
        nomeMedicamento.setText(medicamento.getNomeMedicamento());
        nomeMedicamento.setPadding(8, 8, 8, 8);
        nomeMedicamento.setGravity(Gravity.CENTER);
        nomeMedicamento.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        nomeMedicamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MedicamentoActivity.this, "Visualizar o registro." , Toast.LENGTH_SHORT).show();

            }
        });

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

        row.addView(nomeMedicamento);
        row.addView(botaoEditar);
        this.tabelaMedicamentos.addView(row);
        //Editar cor e informações de disponibilidade aqui, futuramente. !!!!
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