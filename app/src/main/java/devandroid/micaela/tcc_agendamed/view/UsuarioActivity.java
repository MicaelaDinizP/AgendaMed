package devandroid.micaela.tcc_agendamed.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import devandroid.micaela.tcc_agendamed.R;
import devandroid.micaela.tcc_agendamed.controller.UsuarioController;
import devandroid.micaela.tcc_agendamed.model.Usuario;

public class UsuarioActivity extends AppCompatActivity {
    private MenuFragment menuFragment;
    private Button btnCriarUsuario;
    private List<Usuario> listaUsuarios;
    private TableLayout tabelaUsuarios;
    private UsuarioController usuarioController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);
        this.usuarioController = new UsuarioController(UsuarioActivity.this);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(new MenuFragment(), "MENU_FRAGMENT")
                    .commitNow();
        }
        menuFragment = (MenuFragment) getSupportFragmentManager().findFragmentByTag("MENU_FRAGMENT");

        this.tabelaUsuarios = findViewById(R.id.tableUsuarios);

        this.btnCriarUsuario = findViewById(R.id.btnCriarUsuario);
        this.btnCriarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UsuarioActivity.this, CadastroUsuarioActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onResume(){
        super.onResume();
        limparTabela();
        this.listaUsuarios = new ArrayList<>();
        obterUsuariosCadastrados();
        desenharTabela();
    }
    private void atualizarUsuarioLogado(Usuario novoUsuarioLogado) {
        MainActivity.USUARIO_LOGADO = novoUsuarioLogado;
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    private void limparTabela() {
        int childCount = this.tabelaUsuarios.getChildCount();
        if (childCount > 0) {
            this.tabelaUsuarios.removeViews(0, childCount);
        }
    }
    public void obterUsuariosCadastrados(){
        this.listaUsuarios = this.usuarioController.obterTodos();
    }
    public void desenharTabela(){
        for (Usuario usuario : this.listaUsuarios) {
            desenharLinha(usuario.getNome(), usuario.getId());
        }
    }
    @SuppressLint("SetTextI18n")
    public void desenharLinha(String nome, long id){
        Usuario usuario = new Usuario(id, nome);
        TableRow row = new TableRow(this);
        row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

        TextView nomeUsuario = new TextView(this);
        nomeUsuario.setId(View.generateViewId());
        nomeUsuario.setText(nome);
        nomeUsuario.setTextSize(20);
        nomeUsuario.setPadding(8, 8, 8, 8);
        nomeUsuario.setGravity(Gravity.CENTER);
        nomeUsuario.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        nomeUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atualizarUsuarioLogado(usuario);
            }
        });

        Button botaoEditar = new Button(this);
        botaoEditar.setText("Editar");
        botaoEditar.setPadding(8, 8, 8, 8);
        botaoEditar.setGravity(Gravity.CENTER);
        botaoEditar.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.backgroundBasePurple));
        botaoEditar.setLayoutParams(new TableRow.LayoutParams(15, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
        botaoEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UsuarioActivity.this, EdicaoUsuarioActivity.class);
                intent.putExtra("usuario", usuario);
                startActivity(intent);
            }
        });

        row.addView(nomeUsuario);
        row.addView(botaoEditar);
        this.tabelaUsuarios.addView(row);

        if(usuario.getId() == MainActivity.USUARIO_LOGADO.getId()){
            nomeUsuario.setEnabled(false);
            nomeUsuario.setTextColor(Color.parseColor("#000000"));
            nomeUsuario.setTypeface(nomeUsuario.getTypeface(), Typeface.BOLD);
            nomeUsuario.setBackgroundColor(Color.parseColor("#BDECB6"));
            moverLinhaParaTopo(row);
        }
    }
    private void moverLinhaParaTopo(TableRow row) {
        this.tabelaUsuarios.removeView(row);
        this.tabelaUsuarios.addView(row, 0);
    }
    public void exibirMensagem(String mensagem){
        Toast.makeText(UsuarioActivity.this,mensagem, Toast.LENGTH_SHORT).show();
    }
}