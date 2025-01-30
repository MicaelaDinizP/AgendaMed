package devandroid.micaela.tcc_agendamed.model;

import static android.content.Intent.getIntent;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import devandroid.micaela.tcc_agendamed.view.AlarmeActivity;
import devandroid.micaela.tcc_agendamed.view.MainActivity;
import devandroid.micaela.tcc_agendamed.model.Usuario;
import devandroid.micaela.tcc_agendamed.R;
import devandroid.micaela.tcc_agendamed.view.ManualActivity;
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

    private boolean definirAbaEmDestaque(){

        Menu menu = this.toolbarBottom.getMenu();
        MenuItem itemMenu = null;
        ColorStateList colorStateList = ColorStateList.valueOf(Color.parseColor("#800080"));
        if(getActivity().getClass().equals(UsuarioActivity.class) ){
            itemMenu = menu.findItem(R.id.menu_users);
        }
        if(getActivity().getClass().equals(AlarmeActivity.class) ){
            itemMenu = menu.findItem(R.id.menu_alarms);
        }
        if(getActivity().getClass().equals(MedicamentoActivity.class) ){
            itemMenu = menu.findItem(R.id.menu_medicines);
        }
        if(getActivity().getClass().equals(ManualActivity.class) ){
            itemMenu = menu.findItem(R.id.menu_manual);
        }

        itemMenu.setEnabled(false);
        itemMenu.setIconTintList(colorStateList);
        return true;
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

        this.definirAbaEmDestaque();
        this.toolbarBottom.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.menu_users) {
                    Intent intent = new Intent(getContext(), UsuarioActivity.class);
                    startActivity(intent);
                    return true;
                }
                if (id == R.id.menu_alarms) {
                    Intent intent = new Intent(getContext(), AlarmeActivity.class);
                    startActivity(intent);
                    return true;
                }
                if (id == R.id.menu_medicines) {
                    Intent intent = new Intent(getContext(), MedicamentoActivity.class);
                    startActivity(intent);
                    return true;
                }
                if (id == R.id.menu_manual) {
                    Intent intent = new Intent(getContext(), ManualActivity.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });
    }
}