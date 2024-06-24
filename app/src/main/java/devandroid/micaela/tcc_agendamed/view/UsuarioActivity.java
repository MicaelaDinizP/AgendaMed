package devandroid.micaela.tcc_agendamed.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import devandroid.micaela.tcc_agendamed.R;
import devandroid.micaela.tcc_agendamed.model.Usuario;

public class UsuarioActivity extends AppCompatActivity {

    private Toolbar toolbarTop;
    private Toolbar toolbarBottom;
    private ArrayList<Usuario> listaUsuarios;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);

        //criação da toolbar
        toolbarTop = findViewById(R.id.toolbar_top);
        toolbarBottom = findViewById(R.id.toolbar_bottom);

        setSupportActionBar(toolbarTop);
        setupBottomToolbar();

        // carregando o resto da tela
        this.listaUsuarios = new ArrayList<>();
        obterUsuariosCadastrados();
        desenharTabela();


    }
    //FUNCTIONS DE TABELA
    public void obterUsuariosCadastrados(){

        this.listaUsuarios.add(new Usuario("Micaela Diniz da Silva Pae"));
        this.listaUsuarios.add(new Usuario("Nicole Diniz da Silva Paes"));
        this.listaUsuarios.add(new Usuario("MegMeg Diniz da Silva Paes"));

    }
    public void desenharTabela(){

    }

    //FUNCTIONS DE MENU
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_top, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem activeUserName = menu.findItem(R.id.menu_active_user_name);
        activeUserName.setTitle("Micaela Diniz da Silva Pae");

        //activeUserName.setTitle(MainActivity.USUARIO_LOGADO.getNome()); >pega o que é digitado
        //Aqui define o nome do usuário ativo. Caso seja alterado usar o recreate()
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_active_user) {
            Toast.makeText(this, "Menu ACTIVE USER clicked", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (id == R.id.menu_settings) {
            Toast.makeText(this, "Menu SETTINGS clicked", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (id == R.id.menu_active_user_name) {
            Toast.makeText(this, "Menu ACTIVE USER NAME clicked", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupBottomToolbar() {
        toolbarBottom.inflateMenu(R.menu.menu_bottom);
        toolbarBottom.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.menu_users) {
                    Toast.makeText(UsuarioActivity.this, "Bottom Toolbar USERS clicked", Toast.LENGTH_SHORT).show();
                    return true;
                }
                if (id == R.id.menu_alarms) {
                    Toast.makeText(UsuarioActivity.this, "Bottom Toolbar ALARMS clicked", Toast.LENGTH_SHORT).show();
                    return true;
                }
                if (id == R.id.menu_medicines) {
                    Toast.makeText(UsuarioActivity.this, "Bottom Toolbar MEDICINES clicked", Toast.LENGTH_SHORT).show();
                    return true;
                }
                if (id == R.id.menu_manual) {
                    Toast.makeText(UsuarioActivity.this, "Bottom Toolbar MANUAL clicked", Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });
    }
}