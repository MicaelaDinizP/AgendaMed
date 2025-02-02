package devandroid.micaela.tcc_agendamed.test;

import android.os.Parcel;
import devandroid.micaela.tcc_agendamed.model.Usuario;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;

public class UsuarioTest {

    @Test
    public void testCriarParcel() {
        Usuario usuario = new Usuario(1L, "Meg");

        Parcel parcel = Parcel.obtain();
        usuario.writeToParcel(parcel, 0);

        parcel.setDataPosition(0);

        Usuario usuarioCriado = Usuario.CREATOR.createFromParcel(parcel);

        assertEquals(usuario.getId(), usuarioCriado.getId());
        assertEquals(usuario.getNome(), usuarioCriado.getNome());

        parcel.recycle();
    }
    @Test
    public void testCriarArrayDeParcel() {
        ArrayList<Usuario> usuarios = new ArrayList<>();
        usuarios.add(new Usuario(1, "Meg"));
        usuarios.add(new Usuario(2, "Toy"));
        assertEquals(2, usuarios.size());
    }
    @Test
    public void testConsistenciaDoObjetoAposSerializacao() {

        Usuario usuarioOriginal = new Usuario(1L, "Meg");

        usuarioOriginal.setId(3L);
        usuarioOriginal.setNome("Meg");

        Parcel parcel = Parcel.obtain();
        usuarioOriginal.writeToParcel(parcel, 0);

        parcel.setDataPosition(0);

        Usuario usuarioCriado = Usuario.CREATOR.createFromParcel(parcel);

        assertEquals(3L, usuarioCriado.getId());
        assertEquals("Meg", usuarioCriado.getNome());

        parcel.recycle();
    }
}
