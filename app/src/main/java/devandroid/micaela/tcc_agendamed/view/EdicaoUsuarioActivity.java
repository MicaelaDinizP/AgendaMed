package devandroid.micaela.tcc_agendamed.view;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import devandroid.micaela.tcc_agendamed.R;
import devandroid.micaela.tcc_agendamed.controller.UsuarioController;
import devandroid.micaela.tcc_agendamed.model.Usuario;

public class EdicaoUsuarioActivity extends AppCompatActivity {
    private UsuarioController usuarioController;
    private Usuario usuario;
    private ImageButton btnFechar;
    private EditText editTextNomeUsuario;
    private ImageButton btnApagar;
    private ImageButton btnSalvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edicao_usuario);

        Intent intent = getIntent();
        this.usuario = intent.getParcelableExtra("usuario");

        this.usuarioController = new UsuarioController(EdicaoUsuarioActivity.this);

        this.editTextNomeUsuario = findViewById(R.id.editTextNomeUsuario);

        this.btnFechar = findViewById(R.id.btnFechar);
        this.editTextNomeUsuario.setText(this.usuario.getNome());
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
                if(MainActivity.USUARIO_LOGADO.getId() == usuario.getId()){
                    Toast.makeText(EdicaoUsuarioActivity.this, "ERRO: Não é possível excluir o usuario ativo", Toast.LENGTH_SHORT).show();
                    return;
                }
                boolean foiRemovido = usuarioController.remover(usuario.getId());
                if(foiRemovido) {
                    Toast.makeText(EdicaoUsuarioActivity.this, "Usuario removido com sucesso!", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(EdicaoUsuarioActivity.this, "ERRO: Não foi possível remover o usuário", Toast.LENGTH_SHORT).show();
                }
            }
        });

        this.btnSalvar = findViewById(R.id.btnSalvar);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuario.setNome(editTextNomeUsuario.getText().toString());
                if (usuario.getNome() != null && !usuario.getNome().trim().isEmpty()) {
                    boolean foiEditado = usuarioController.editar(usuario);
                    if (foiEditado) {
                        Toast.makeText(EdicaoUsuarioActivity.this, "Usuário editado com sucesso!", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(EdicaoUsuarioActivity.this, "Não foi possível editar o usuario.", Toast.LENGTH_LONG).show();
                    }
                }else{
                    exibirMensagem("ERRO: O usuário foi digitado incorretamente.");
                }
            }
        });
    }
    public void exibirMensagem(String mensagem){
        Toast.makeText(EdicaoUsuarioActivity.this, "" + mensagem + "" , Toast.LENGTH_SHORT).show();
    }
}
