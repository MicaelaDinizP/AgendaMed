package devandroid.micaela.tcc_agendamed.model;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import devandroid.micaela.tcc_agendamed.view.MainActivity;
import devandroid.micaela.tcc_agendamed.model.Usuario;
import devandroid.micaela.tcc_agendamed.R;
import devandroid.micaela.tcc_agendamed.view.MedicamentoActivity;
import devandroid.micaela.tcc_agendamed.view.UsuarioActivity;

public class MenuFragment extends Fragment {
    private Toolbar toolbarTop;
    private Toolbar toolbarBottom;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        this.toolbarTop = getActivity().findViewById(R.id.toolbar_top);
        this.toolbarBottom = getActivity().findViewById(R.id.toolbar_bottom);

        ((AppCompatActivity) getActivity()).setSupportActionBar(this.toolbarTop);
        setupBottomToolbar();
    }

    public boolean definirAbaEmDestaque(){
        //inativar o botao da aba atual
        //colocar a aba atual em destaque

        if(getActivity().getClass().equals(UsuarioActivity.class) ){
            Toast.makeText(getActivity(), "A aba em destaque será a 'Usuarios'", Toast.LENGTH_SHORT).show();
            return true;
        }
//        if(getActivity().getClass().equals(AlarmeActivity.class) ){
//            Toast.makeText(getActivity(), "A aba em destaque será a 'Alarmes'", Toast.LENGTH_SHORT).show();
//            return true;
//        }
        if(getActivity().getClass().equals(MedicamentoActivity.class) ){
            Toast.makeText(getActivity(), "A aba em destaque será a 'Medicamentos'", Toast.LENGTH_SHORT).show();
            return true;
        }
//        if(getActivity().getClass().equals(ManualActivity.class) ){
//            Toast.makeText(getActivity(), "A aba em destaque será a 'Manual'", Toast.LENGTH_SHORT).show();
//            return true;
//        }
        return false;
    }
    private void atualizarUsuarioLogado(Usuario novoUsuarioLogado) {
            //pego o usuario atual
            //removo o background color dele
            //adiciono o novoUsuario na variavel estatica
            //troco a cor do usuario novo pra verde
            //deixo o botão anterior ativo
            //deixo o botão atual inativo
            //MainActivity.USUARIO_LOGADO = novoUsuarioLogado;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_top, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem activeUserName = menu.findItem(R.id.menu_active_user_name);
        activeUserName.setTitle(MainActivity.USUARIO_LOGADO.getNome());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_active_user) {
            Toast.makeText(getActivity(), "Menu ACTIVE USER clicked", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (id == R.id.menu_settings) {
            Toast.makeText(getActivity(), "Menu SETTINGS clicked", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (id == R.id.menu_active_user_name) {
            Toast.makeText(getActivity(), "Menu ACTIVE USER NAME clicked", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void setupBottomToolbar() {
        this.toolbarBottom.inflateMenu(R.menu.menu_bottom);

        this.toolbarBottom.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.menu_users) {
                    Toast.makeText(getActivity(), "Bottom Toolbar USERS clicked", Toast.LENGTH_SHORT).show();
                    return true;
                }
                if (id == R.id.menu_alarms) {
                    Toast.makeText(getActivity(), "Bottom Toolbar ALARMS clicked", Toast.LENGTH_SHORT).show();
                    return true;
                }
                if (id == R.id.menu_medicines) {
                    Intent intent = new Intent(getContext(), MedicamentoActivity.class);
                    startActivity(intent);
                    return true;
                }
                if (id == R.id.menu_manual) {
                    Toast.makeText(getActivity(), "Bottom Toolbar MANUAL clicked", Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });
    }
}