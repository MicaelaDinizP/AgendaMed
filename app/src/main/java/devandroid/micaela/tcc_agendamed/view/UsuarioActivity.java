package devandroid.micaela.tcc_agendamed.view;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;

import android.widget.TextView;

import devandroid.micaela.tcc_agendamed.R;
import devandroid.micaela.tcc_agendamed.model.Usuario;

public class UsuarioActivity extends AppCompatActivity{
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
    public TextView textViewNomeUsuarioLogado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);

        //..  01 obtém informações do ultimo usuário logado --acessa var estática e nao o BD --- AINDA
        //..  02 carrega ele na area de usuario logado
        textViewNomeUsuarioLogado = findViewById(R.id.textViewNomeUsuarioLogado);
        textViewNomeUsuarioLogado.setText(MainActivity.USUARIO_LOGADO.getNome());
        //..  03 carrega os botoes da navegação


        //    LISTA DE TAREFAS
        //..  01 obtém informações do ultimo usuário logado
        //..  02 carrega ele na area de usuario logado
        //..  03 carrega os botoes da navegação
        //..  04 carrega a tabela de usuários

    }

}