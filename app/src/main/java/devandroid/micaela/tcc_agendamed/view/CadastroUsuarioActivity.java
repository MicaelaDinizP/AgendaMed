package devandroid.micaela.tcc_agendamed.view;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import devandroid.micaela.tcc_agendamed.R;

import devandroid.micaela.tcc_agendamed.model.Usuario;

public class CadastroUsuarioActivity extends AppCompatActivity {
    private ImageButton btnFechar;
    private EditText editTextNomeUsuario;
    private ImageButton btnApagar;
    private ImageButton btnSalvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

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
                Toast.makeText(CadastroUsuarioActivity.this, "Botão clicado : Apagar " , Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        this.btnSalvar = findViewById(R.id.btnSalvar);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CadastroUsuarioActivity.this, "Salvando usuário "+  editTextNomeUsuario.getText() , Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}