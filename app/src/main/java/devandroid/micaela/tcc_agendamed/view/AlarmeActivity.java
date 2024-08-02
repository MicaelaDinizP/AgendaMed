package devandroid.micaela.tcc_agendamed.view;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import devandroid.micaela.tcc_agendamed.R;
import devandroid.micaela.tcc_agendamed.model.MenuFragment;

public class AlarmeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarme);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(new MenuFragment(), "MENU_FRAGMENT")
                    .commitNow();
        }
    }
}
