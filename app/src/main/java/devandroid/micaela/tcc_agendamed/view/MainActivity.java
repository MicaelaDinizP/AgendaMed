package devandroid.micaela.tcc_agendamed.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import devandroid.micaela.tcc_agendamed.R;

import devandroid.micaela.tcc_agendamed.controller.UsuarioController;
import devandroid.micaela.tcc_agendamed.model.Usuario;
public class MainActivity extends AppCompatActivity {
    public static Usuario USUARIO_LOGADO;
    private UsuarioController usuarioController;
    public EditText editNomeUsuarioInicial;
    public Button btnContinuar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);
        editNomeUsuarioInicial = findViewById(R.id.editNomeUsuarioInicial);

        this.usuarioController = new UsuarioController(MainActivity.this);

        btnContinuar = findViewById(R.id.btnContinuar);

        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                USUARIO_LOGADO = new Usuario(editNomeUsuarioInicial.getText().toString());

                usuarioController.abrirConexao();
                long idUsuarioInicial = usuarioController.inserir(USUARIO_LOGADO.getNome());
                if(idUsuarioInicial == -1){
                    Toast.makeText(MainActivity.this, "ERRO: Falha ao cadastrar o usuário inicial.", Toast.LENGTH_LONG).show();

                }else{
                    USUARIO_LOGADO.setId(idUsuarioInicial);
                    Toast.makeText(MainActivity.this, "O usuário: '"+USUARIO_LOGADO.getNome()+"' foi cadastrado!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MainActivity.this, UsuarioActivity.class);
                    startActivity(intent);
                 }
                usuarioController.fecharConexao();
            }
        });

    }
}