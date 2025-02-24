package devandroid.micaela.tcc_agendamed.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import devandroid.micaela.tcc_agendamed.R;

import devandroid.micaela.tcc_agendamed.controller.UsuarioController;

public class CadastroUsuarioActivity extends AppCompatActivity {
    private UsuarioController usuarioController;
    private ImageButton btnFechar;
    private EditText editTextNomeUsuario;
    private ImageButton btnApagar;
    private ImageButton btnSalvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

        this.usuarioController = new UsuarioController(CadastroUsuarioActivity.this);

        this.editTextNomeUsuario = findViewById(R.id.editTextNomeUsuario);

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
                finish();
            }
        });

        this.btnSalvar = findViewById(R.id.btnSalvar);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomeUsuario = editTextNomeUsuario.getText().toString();
                if (nomeUsuario != null || !nomeUsuario.trim().isEmpty()) {
                    long idRetornado = usuarioController.inserir(nomeUsuario);
                    if(idRetornado > 0) {
                        exibirMensagem("Usuario "+nomeUsuario+" cadastrado com sucesso!");
                        finish();
                    }else{
                        exibirMensagem("ERRO: Não foi possível cadastrar o usuario.");
                    }
                }
            }
        });
    }

    public void exibirMensagem(String mensagem){
        Toast.makeText(CadastroUsuarioActivity.this,mensagem, Toast.LENGTH_SHORT).show();
    }
}