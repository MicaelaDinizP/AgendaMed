package devandroid.micaela.tcc_agendamed.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import devandroid.micaela.tcc_agendamed.R;
import devandroid.micaela.tcc_agendamed.controller.UsuarioController;
import devandroid.micaela.tcc_agendamed.model.MenuFragment;
import devandroid.micaela.tcc_agendamed.model.Usuario;

public class UsuarioActivity extends AppCompatActivity {
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
//        MenuFragment menuFragment = (MenuFragment) getSupportFragmentManager().findFragmentByTag("MENU_FRAGMENT");
//        if (menuFragment != null) {
//            menuFragment.definirAbaEmDestaque();
//        } else {
//            Toast.makeText(this, "FragmentoNãoEncontrado", Toast.LENGTH_SHORT).show();
//        }

        this.tabelaUsuarios = findViewById(R.id.tableUsuarios);

        //carregando o botão Criar Usuario
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
    private void limparTabela() {
        int childCount = this.tabelaUsuarios.getChildCount();
        if (childCount > 1) {
            this.tabelaUsuarios.removeViews(1, childCount - 1);
        }
    }

    //FUNCTIONS DE TABELA
    public void obterUsuariosCadastrados(){
        this.usuarioController.abrirConexao();
        this.listaUsuarios = this.usuarioController.obterTodos();
        this.usuarioController.fecharConexao();
    }
    public void desenharTabela(){
        for (Usuario usuario : this.listaUsuarios) {
            desenharLinha(usuario.getNome(), usuario.getId());
        }
    }
    public void desenharLinha(String nome, long id){
        TableRow row = new TableRow(this);
        row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

        TextView nomeUsuario = new TextView(this);
        nomeUsuario.setText(nome);
        nomeUsuario.setPadding(8, 8, 8, 8);
        nomeUsuario.setGravity(Gravity.CENTER);
        nomeUsuario.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));

        Button botaoEditar = new Button(this);
        botaoEditar.setText("Editar");
        botaoEditar.setPadding(8, 8, 8, 8);
        botaoEditar.setGravity(Gravity.CENTER);
        botaoEditar.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        botaoEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Usuario usuario = new Usuario(id, nome);
                Intent intent = new Intent(UsuarioActivity.this, EditarUsuarioActivity.class);
                intent.putExtra("usuario", (Parcelable) usuario);
                startActivity(intent);
            }
        });

        row.addView(nomeUsuario);
        row.addView(botaoEditar);

        this.tabelaUsuarios.addView(row);
    }
}