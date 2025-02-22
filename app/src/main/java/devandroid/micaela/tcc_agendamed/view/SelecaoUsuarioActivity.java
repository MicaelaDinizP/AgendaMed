package devandroid.micaela.tcc_agendamed.view;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.List;

import devandroid.micaela.tcc_agendamed.R;
import devandroid.micaela.tcc_agendamed.controller.UsuarioController;
import devandroid.micaela.tcc_agendamed.model.Usuario;

public class SelecaoUsuarioActivity extends AppCompatActivity {
    private UsuarioController usuarioController;
    private TableLayout tabelaLoginUsuario;
    private List<Usuario> listaUsuarios;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecao_usuario);

        this.usuarioController = new UsuarioController(this);
        this.tabelaLoginUsuario = findViewById(R.id.tableLoginUsuario);
        this.listaUsuarios = this.usuarioController.obterTodos();
        if(this.listaUsuarios.isEmpty()){
            Toast.makeText(SelecaoUsuarioActivity.this, "Erro ao carregar o aplicativo." , Toast.LENGTH_SHORT).show();
            finish();
        }
        this.desenharTabela();
    }
    private void desenharTabela() {
        for (Usuario usuario : this.listaUsuarios) {
            desenharLinha(usuario);
        }
    }
    private  void desenharLinha(Usuario usuario){
        TableRow row = new TableRow(this);
        row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

        Button botaoLogarUsuario = new Button(this);
        botaoLogarUsuario.setTextColor(ContextCompat.getColor(this, R.color.backgroundEditTextWhite));
        botaoLogarUsuario.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.buttonBaseDeepPurple));
        botaoLogarUsuario.setText(usuario.getNome());
        botaoLogarUsuario.setTypeface(Typeface.MONOSPACE);
        botaoLogarUsuario.setTextSize(18);
        botaoLogarUsuario.setPadding(8, 8, 8, 8);
        botaoLogarUsuario.setGravity(Gravity.CENTER);
        botaoLogarUsuario.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        float shadowRadius = 4.0f;
        float shadowDx = 2.0f;
        float shadowDy = 2.0f;
        int shadowColor = Color.parseColor("#000000");
        botaoLogarUsuario.setShadowLayer(shadowRadius, shadowDx, shadowDy, shadowColor);
        botaoLogarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.USUARIO_LOGADO = usuario;
                Intent intent = new Intent(SelecaoUsuarioActivity.this, UsuarioActivity.class);
                startActivity(intent);
            }
        });

        row.addView(botaoLogarUsuario);
        this.tabelaLoginUsuario.addView(row);
    }
}
