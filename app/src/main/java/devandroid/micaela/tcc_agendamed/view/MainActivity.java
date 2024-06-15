package devandroid.micaela.tcc_agendamed.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import devandroid.micaela.tcc_agendamed.R;

import devandroid.micaela.tcc_agendamed.model.Usuario;
public class MainActivity extends AppCompatActivity {
    public static Usuario USUARIO_LOGADO;
    public EditText editNomeUsuarioInicial;
    public Button btnContinuar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);
        editNomeUsuarioInicial = findViewById(R.id.editNomeUsuarioInicial);

        btnContinuar = findViewById(R.id.btnContinuar);

        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                USUARIO_LOGADO = new Usuario(editNomeUsuarioInicial.getText().toString());
                Intent intent = new Intent(MainActivity.this, UsuarioActivity.class);
                startActivity(intent);
                //Toast.makeText(MainActivity.this, "O usuário: '"+usuarioInicial.getNome()+"' foi cadastrado!", Toast.LENGTH_LONG).show();
            }
        });

    }
}