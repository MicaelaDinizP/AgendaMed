package devandroid.micaela.tcc_agendamed.test;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)  // Define que o teste será executado no Android
public class ExampleInstrumentedTest {

    @Test
    public void useAppContext() {
        // Obter o contexto da aplicação
        Context appContext = ApplicationProvider.getApplicationContext();

        // Verificar se o nome do pacote está correto
        assertEquals("com.exemplo.app", appContext.getPackageName());
    }
}